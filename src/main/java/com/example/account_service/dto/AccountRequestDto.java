package com.example.account_service.dto;

import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.TypeAccountNumber;
import com.example.account_service.model.enums.TypeOwner;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.util.Currency;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto {
    private String id;
    @Positive
    private long ownerId;
    @NotNull
    private TypeOwner typeOwner;
    @NotNull
    private TypeAccountNumber type;
    @NotNull
    private Currency currency;
    @NotNull
    private AccountStatus status;
}
