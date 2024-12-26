package com.example.account_service.service.free_account_numbers;

import com.example.account_service.dto.FreeAccountNumberDto;
import com.example.account_service.model.Account;
import com.example.account_service.model.FreeAccountNumber;
import com.example.account_service.model.enums.NumberCode;
import com.example.account_service.model.enums.TypeNumber;
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
import java.util.function.Predicate;

@Slf4j
@Service
@RequiredArgsConstructor
public class FreeAccountNumbersServiceImpl implements FreeAccountNumbersService {
    private final FreeAccountNumbersRepository freeAccountNumbersRepository;
    private final AccountNumbersSequenceRepository accountNumbersSequenceRepository;

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Account getFreeAccountNumber(String type, Function<String, Account> function) {
        if (!freeAccountNumbersRepository.existsFreeAccountNumberByType(TypeNumber.valueOf(type))) {
            addAccountNumbersSequenceAndFreeAccountNumbers(type);
        }
        FreeAccountNumber accountNumber = freeAccountNumbersRepository.getFreeAccountNumberAndDelete(type);
        if (accountNumber == null) {
            addAccountNumbersSequenceAndFreeAccountNumbers(type);
            accountNumber = freeAccountNumbersRepository.getFreeAccountNumberAndDelete(type);
        }
        String number = accountNumber.getAccount_number();
        return function.apply(number);
    }

    @Transactional(propagation = Propagation.REQUIRES_NEW)
    @Retryable(retryFor = OptimisticLockingFailureException.class, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void addAccountNumbersSequenceAndFreeAccountNumbers(String type) {
        int valueInSequence = accountNumbersSequenceRepository.getCounter(type);
        int newValueInSequence = accountNumbersSequenceRepository.updateCounter(type);
        List<FreeAccountNumber> freeAccountNumbers = getFreeAccountNumbers(type, valueInSequence, newValueInSequence);
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

    private List<FreeAccountNumber> getFreeAccountNumbers(String type, int valueInSequence, int newValueInSequence) {
        List<FreeAccountNumber> freeAccountNumbers = new ArrayList<>();
        for (int i = valueInSequence + 1; i <= newValueInSequence; i++) {
            FreeAccountNumber freeAccountNumber = new FreeAccountNumber();
            freeAccountNumber.setType(TypeNumber.valueOf(type));
            long number = NumberCode.getCode(type);
            number = number + i;
            freeAccountNumber.setAccount_number(String.valueOf(number));
            freeAccountNumbers.add(freeAccountNumber);
        }
        return freeAccountNumbers;
    }
}
