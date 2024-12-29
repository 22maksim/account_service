package com.example.account_service.dto.balance;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.PositiveOrZero;
import lombok.Value;

import java.io.Serializable;
import java.sql.Timestamp;

/**
 * DTO for {@link com.example.account_service.model.Balance}
 */
@Value
public class BalanceResponseDto implements Serializable {
    @NotNull
    @Positive
    Long id;
    @NotNull
    @Positive
    String account_id;
    @PositiveOrZero
    long authorizationBalance;
    @PositiveOrZero
    long currentBalance;
    Timestamp createdAt;
    Timestamp updatedAt;
}