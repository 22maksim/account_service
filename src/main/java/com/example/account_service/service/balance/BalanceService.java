package com.example.account_service.service.balance;

import com.example.account_service.aop.balance.audit.AuditBalance;
import com.example.account_service.model.Balance;
import com.example.account_service.model.dto.balance.BalanceOpenRequestDto;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.model.enums.TypeBalanceOperation;

import java.util.List;

public interface BalanceService {
    BalanceResponseDto getBalanceById(Long id);

    @AuditBalance(typeOperation = TypeBalanceOperation.CREATE)
    BalanceResponseDto createBalance(BalanceOpenRequestDto balanceDto);

    @AuditBalance(typeOperation = TypeBalanceOperation.UPDATE)
    BalanceResponseDto updateBalance(Long id, BalanceTransactionRequestDto transactionRequest);

    void saveAllBalances(List<Balance> balances);
}
