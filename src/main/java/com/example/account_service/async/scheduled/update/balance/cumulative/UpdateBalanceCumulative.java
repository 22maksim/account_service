package com.example.account_service.async.scheduled.update.balance.cumulative;

import com.example.account_service.service.account.cumulative.AccountCumulativeService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class UpdateBalanceCumulative {
    private final AccountCumulativeService accountCumulativeServiceImpl;

    @Scheduled(cron = "0 0 10 * * *")
    public void updateBalanceCumulative() {
        accountCumulativeServiceImpl.updateCumulateAccounts();
    }
}
