package com.example.account_service.service.savings.account;

import com.example.account_service.model.dto.savings.account.SavingsAccountRequestDto;
import com.example.account_service.model.dto.savings.account.SavingsAccountResponseDto;

public interface SavingsAccountService {
    SavingsAccountResponseDto createSavingsAccount(SavingsAccountRequestDto savingsAccountRequestDto);

    SavingsAccountResponseDto getSavingsAccount(String accountId);
}
