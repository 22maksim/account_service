package com.example.account_service.exeption;

public class DataPaymentRequestEx extends RuntimeException {
    public DataPaymentRequestEx(String message) {
        super(message);
    }
}
