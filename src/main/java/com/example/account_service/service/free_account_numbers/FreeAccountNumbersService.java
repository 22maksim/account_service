package com.example.account_service.service.free_account_numbers;

import com.example.account_service.model.Account;
import com.example.account_service.model.FreeAccountNumber;
import org.springframework.scheduling.annotation.Async;

import java.util.List;
import java.util.function.Function;

public interface FreeAccountNumbersService {

    Account getFreeAccountNumber(String type, long currencyValue, Function<String, Account> function);

    @Async("workerAsync")
    void addAccountNumbersSequenceAndFreeAccountNumbers(String type, long currencyValue);

    void addFreeAccountNumbers(List<FreeAccountNumber> accountNumbers);

    int getFreeAccountNumbersCount(String type);
}
