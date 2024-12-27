package com.example.account_service.service.balance;

import com.example.account_service.dto.balance.BalanceOpenRequestDto;
import com.example.account_service.dto.balance.BalanceResponseDto;
import com.example.account_service.dto.balance.BalanceTransactionRequestDto;

public interface BalanceService {
    BalanceResponseDto getBalanceById(Long id);

    BalanceResponseDto createBalance(BalanceOpenRequestDto balanceDto);

    BalanceResponseDto updateBalance(Long id, BalanceTransactionRequestDto transactionRequest);
}
