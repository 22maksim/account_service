package com.example.account_service.exeption;

public class UncorrectedValueRequest extends RuntimeException {
    public UncorrectedValueRequest(String message) {
        super(message);
    }
}
