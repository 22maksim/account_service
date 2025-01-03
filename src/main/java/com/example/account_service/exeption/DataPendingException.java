package com.example.account_service.exeption;

public class DataPendingException extends RuntimeException {
    public DataPendingException(String message) {
        super(message);
    }
}
