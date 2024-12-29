package com.example.account_service.dto.balance;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import jakarta.validation.constraints.Size;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceOpenRequestDto {
    @NotNull
    @Positive
    @Size(min = 10, max = 20)
    private String accountId;
    @NotNull
    @Positive
    private long authorizationBalance;
    @NotNull
    private long currentBalance;
}
