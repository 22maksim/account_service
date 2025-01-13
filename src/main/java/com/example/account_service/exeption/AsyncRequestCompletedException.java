package com.example.account_service.exeption;

import java.io.IOException;

public class AsyncRequestCompletedException extends IOException {
    public AsyncRequestCompletedException(String message) {
        super(message);
    }
}
