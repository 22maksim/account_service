package com.example.account_service.service.balance.clearing;

import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.model.Balance;

public interface ClearingProcessService {
    Balance processing(BalanceTransactionRequestDto transactionRequest, Long balanceId);
}
