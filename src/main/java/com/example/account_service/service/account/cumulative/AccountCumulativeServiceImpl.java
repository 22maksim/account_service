package com.example.account_service.service.account.cumulative;

import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.model.Account;
import com.example.account_service.model.CumulativeTariff;
import com.example.account_service.model.SavingsAccount;
import com.example.account_service.model.enums.TypeNumber;
import com.example.account_service.model.properties.BoxProperties;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.savings.account.SavingsAccountRepository;
import com.example.account_service.repository.tariff.cumulative.CumulativeTariffRepository;
import com.fasterxml.jackson.databind.ObjectMapper;
import jakarta.persistence.OptimisticLockException;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageRequest;
import org.springframework.retry.annotation.Backoff;
import org.springframework.retry.annotation.Retryable;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;

import java.sql.SQLDataException;
import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AccountCumulativeServiceImpl implements AccountCumulativeService {
    private final AccountRepository accountRepository;
    private final SavingsAccountRepository savingsAccountRepository;
    private final BoxProperties boxProperties;
    private final CumulativeTariffRepository cumulativeTariffRepository;
    private final ObjectMapper objectMapper;

    @Async("workerAsync")
    @Transactional
    public void updateCumulateAccounts() {
        int page = 0;
        Page<Account> pageAccounts;
        do {
            PageRequest pageable = PageRequest.of(page, boxProperties.size());
            pageAccounts = accountRepository.findAllByType(TypeNumber.CUMULATIVE, pageable);

            if (!pageAccounts.isEmpty()) {
                log.info("Processing page {}", page);
                updateBalance(pageAccounts.getContent());
            } else {
                log.warn("No accounts found on page {}", page);
            }
            page++;
        } while (pageAccounts.hasNext());
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    @Retryable(retryFor = {SQLDataException.class, OptimisticLockException.class}, backoff = @Backoff(delay = 5000, multiplier = 2))
    public void updateBalance(List<Account> accounts) {
        if (accounts.isEmpty()) {
            log.info("accounts to increase the balance by interest, not found");
            return;
        }
        for (Account account : accounts) {
            CumulativeTariff cumTariff = cumulativeTariffRepository.findById(account.getTariffType().name())
                    .orElseThrow(() -> new DataAccountException("cumulative tariff not found"));

            int[] historyRates = objectMapper.convertValue(cumTariff.getHistory(), int[].class);
            int rate = historyRates[historyRates.length - 1];
            double sum = rate / 365.00;
            double counter = rate * sum;
            account.getBalance().setCurrentBalance((long) counter - rate);
            SavingsAccount savingsAccount = savingsAccountRepository.findById(account.getId())
                    .orElseThrow(() -> new DataAccountException("savings account not found"));
            savingsAccount.setUpdatePercent(Timestamp.valueOf(LocalDateTime.now()));
        }
        accountRepository.saveAll(accounts);
    }
}
