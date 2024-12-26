package com.example.account_service.model.enums;

import lombok.Getter;

@Getter
public enum Currency {
    USD(1_0000_0000_0000L),
    EUR(2_0000_0000_0000L),
    RUB(3_0000_0000_0000L);
    private final long value;

    Currency(long value) {
        this.value = value;
    }
}
