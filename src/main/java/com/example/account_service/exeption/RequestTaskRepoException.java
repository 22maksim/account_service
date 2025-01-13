package com.example.account_service.exeption;

import java.io.IOException;

public class RequestTaskRepoException extends IOException {
    public RequestTaskRepoException(String message) {
        super(message);
    }
}
