package com.example.account_service.service.balance.audit;

import com.example.account_service.model.dto.balance.BalanceResponseDto;

public interface BalanceAuditService {
    void saveBalanceAudit(BalanceResponseDto balanceResponseDto);
}
