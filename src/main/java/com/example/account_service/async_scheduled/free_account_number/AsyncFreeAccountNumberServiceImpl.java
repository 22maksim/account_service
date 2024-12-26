package com.example.account_service.async_scheduled.free_account_number;

import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.TypeNumber;
import com.example.account_service.model.properties.BoxProperties;
import com.example.account_service.service.free_account_numbers.FreeAccountNumbersService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class AsyncFreeAccountNumberServiceImpl implements AsyncFreeAccountNumberService {
    private final FreeAccountNumbersService freeAccountNumbersServiceImpl;
    private final BoxProperties boxProperties;

    @Override
    public void operationFreeAccountNumber() {
        for (TypeNumber typeNumber : TypeNumber.values()) {
            for (Currency currency : Currency.values()) {
                String type = typeNumber.name();
                long currencyValue = currency.getValue();
                int counterFreeAccountNumbers = freeAccountNumbersServiceImpl.getFreeAccountNumbersCount(type);
                for (int i = counterFreeAccountNumbers; i <= 500; i += boxProperties.size()) {
                    operationFreeAccountNumber(type, currencyValue);
                }
            }
        }
    }

    public void operationFreeAccountNumber(String type, long currencyValue) {
        freeAccountNumbersServiceImpl.addAccountNumbersSequenceAndFreeAccountNumbers(type, currencyValue);
    }
}
