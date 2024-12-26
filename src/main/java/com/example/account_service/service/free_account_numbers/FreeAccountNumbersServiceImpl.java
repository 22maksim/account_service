package com.example.account_service.service.free_account_numbers;

import com.example.account_service.model.Account;
import com.example.account_service.model.FreeAccountNumber;
import com.example.account_service.model.enums.NumberCode;
import com.example.account_service.model.enums.TypeNumber;
import com.example.account_service.model.properties.BoxProperties;
import com.example.account_service.repository.account_number_sequence.AccountNumbersSequenceRepository;
import com.example.account_service.repository.free_account_numbers.FreeAccountNumbersRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.dao.OptimisticLockingFailureException;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Propagation;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.function.Function;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeAccountNumbersServiceImpl implements FreeAccountNumbersService {
    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;
    private final BoxProperties boxProperties;

    @Transactional(isolation = Isolation.REPEATABLE_READ, propagation = Propagation.REQUIRES_NEW)
    public Account getFreeAccountNumber(String type, long currencyValue, Function<String, Account> function) {
        if (!freeAccountNumbersRepository.existsFreeAccountNumberByType(TypeNumber.valueOf(type))) {
            addAccountNumbersSequenceAndFreeAccountNumbers(type, currencyValue);
        }
        FreeAccountNumber accountNumber = freeAccountNumbersRepository.getFreeAccountNumberAndDelete(type);
        if (accountNumber == null) {
            addAccountNumbersSequenceAndFreeAccountNumbers(type, currencyValue);
            accountNumber = freeAccountNumbersRepository.getFreeAccountNumberAndDelete(type);
        }
        String number = accountNumber.getAccount_number();
        return function.apply(number);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW, isolation = Isolation.REPEATABLE_READ)
    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void addAccountNumbersSequenceAndFreeAccountNumbers(String type, long currencyValue) {
        int valueInSequence = accountNumbersSequenceRepository.getCounter(type);
        int newValueInSequence = accountNumbersSequenceRepository.updateCounter(type, boxProperties.size());
        List<FreeAccountNumber> freeAccountNumbers =
                getFreeAccountNumbers(type, valueInSequence, newValueInSequence, currencyValue);
        addFreeAccountNumbers(freeAccountNumbers);
    }

    @Transactional
    public void addFreeAccountNumbers(List<FreeAccountNumber> accountNumbers) {
        if (accountNumbers.isEmpty()) {
            log.info("Account numbers don't have been added");
            throw new IllegalArgumentException("Account numbers don't have been added");
        }
        freeAccountNumbersRepository.saveAll(accountNumbers);
    }

    @Override
    public int getFreeAccountNumbersCount(String type) {
        return freeAccountNumbersRepository.getQuantityFreeAccountNumberByType(type);
    }

    private List<FreeAccountNumber> getFreeAccountNumbers(
            String type, int valueInSequence, int newValueInSequence, long currencyValue) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        for (int i = valueInSequence + 1; i <= newValueInSequence; i++) {
            FreeAccountNumber freeAccountNumber = new FreeAccountNumber();
            freeAccountNumber.setType(TypeNumber.valueOf(type));
            long number = NumberCode.getCode(type);
            number = number + currencyValue + i;
            freeAccountNumber.setAccount_number(String.valueOf(number));
            freeAccountNumbers.add(freeAccountNumber);
        }
        return freeAccountNumbers;
    }
}
