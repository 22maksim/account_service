package com.example.account_service.model.enums;

public enum TypePaymentRequest {
    AUTHORIZATION, //заморозка средств для авторизации суммы
    CAPTURE, // списание средств со счета после успешной авторизации
    SETTLEMENT, // процесс клиринга и завершение транзакции
    REPLENISHMENT //зачисление средств на счет после авторизации
}
