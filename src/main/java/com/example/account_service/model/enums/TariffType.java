package com.example.account_service.model.enums;

import lombok.Getter;

@Getter
public enum TariffType {
    // Все счета пополняемые. Начисление ежедневно
    BY_SUBSCRIPTION, // когда угодно
    BASE, // деньги можно снять когда угодно
    PROMO, // когда угодно
    TIME_DEPOSIT //деньги нельзя достать когда угодно
}