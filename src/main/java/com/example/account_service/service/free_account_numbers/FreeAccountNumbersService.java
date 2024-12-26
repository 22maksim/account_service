package com.example.account_service.service.free_account_numbers;

import com.example.account_service.dto.FreeAccountNumberDto;
import com.example.account_service.model.Account;
import com.example.account_service.model.FreeAccountNumber;

import java.util.List;
import java.util.function.Function;
import java.util.function.Predicate;

public interface FreeAccountNumbersService {

    Account getFreeAccountNumber(String type, Function<String, Account> function);

    void addAccountNumbersSequenceAndFreeAccountNumbers(String type);

    void addFreeAccountNumbers(List<FreeAccountNumber> accountNumbers);
}
