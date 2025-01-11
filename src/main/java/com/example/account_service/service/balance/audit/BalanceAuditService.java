package com.example.account_service.service.balance.audit;

import com.example.account_service.model.BalanceAudit;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;

public interface BalanceAuditService {
    void saveBalanceAudit(BalanceAudit audit);

    void updateBalanceAudit(BalanceResponseDto responseDto, BalanceTransactionRequestDto transactionRequestDto);

    void cashbackAccrual();
}
