package com.example.account_service.async_scheduled.free_account_number;

import org.springframework.scheduling.annotation.Scheduled;

public interface AsyncFreeAccountNumberService {
    @Scheduled(cron = "${cron.operation.free.account.number}")
    void operationFreeAccountNumber();

    void operationFreeAccountNumber(String type, long currencyValue);
}
