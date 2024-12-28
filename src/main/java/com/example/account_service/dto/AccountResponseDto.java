package com.example.account_service.dto;

import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.TypeOwner;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;
import java.util.Currency;

@Builder
@Getter
@Setter
public class AccountResponseDto implements Serializable {
    private String id;
    @NotNull
    @Positive
    private long ownerId;
    @NotNull
    private TypeOwner typeOwner;
    @NotNull
    private String currency;
    @NotNull
    private String status;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private Timestamp closeAt;
}
