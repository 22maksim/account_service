package com.example.account_service.exeption;

public class DataBalanceException extends RuntimeException {
    public DataBalanceException(String message) {
        super(message);
    }
}
