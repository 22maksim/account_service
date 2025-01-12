package com.example.account_service.async.scheduled.cashback;

import com.example.account_service.service.balance.audit.BalanceAuditService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class CashbackAccrual {
    private final BalanceAuditService balanceAuditServiceImpl;

    @Scheduled(cron = "${cron.cashback}")
    public void cashbackAccrual() {
        balanceAuditServiceImpl.cashbackAccrual();
    }
}
