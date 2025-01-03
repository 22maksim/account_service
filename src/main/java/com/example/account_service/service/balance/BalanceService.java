package com.example.account_service.service.balance;

import com.example.account_service.model.dto.balance.BalanceOpenRequestDto;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;

public interface BalanceService {
    BalanceResponseDto getBalanceById(Long id);

    BalanceResponseDto createBalance(BalanceOpenRequestDto balanceDto);

    BalanceResponseDto updateBalance(Long id, BalanceTransactionRequestDto transactionRequest);
}
