package com.example.account_service.dto.balance;

import com.example.account_service.model.enums.TypeTransactionBalance;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class BalanceTransactionRequestDto {
    private long debitAmount;
    private long creditAmount;
    private long depositAmount;
    @Positive
    private long numberOperation;
    @NotNull
    private TypeTransactionBalance typeTransactionBalance;
}
