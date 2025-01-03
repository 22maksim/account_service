package com.example.account_service.model.dto.account;

import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.model.enums.TypeNumber;
import com.example.account_service.model.enums.TypeOwner;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

import java.io.Serializable;

@Builder
@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AccountRequestDto implements Serializable {
    private String id;
    @Positive
    private long ownerId;
    @NotNull
    private TypeOwner typeOwner;
    @NotNull
    private TypeNumber type;
    @NotNull
    private String currency;
    @NotNull
    private AccountStatus status;
}
