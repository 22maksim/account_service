package com.example.account_service.dto;

import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.TypeOwner;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Currency;

public class AccountResponseDto implements Serializable {
    private String id;
    @Positive
    private long ownerId;
    @NotNull
    private TypeOwner typeOwner;
    @NotNull
    private Currency currency;
    @NotNull
    private AccountStatus status;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private Timestamp closeAt;
}
