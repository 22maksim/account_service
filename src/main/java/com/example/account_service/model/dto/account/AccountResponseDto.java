package com.example.account_service.model.dto.account;

import com.example.account_service.model.enums.TariffType;
import com.example.account_service.model.enums.TypeOwner;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.Builder;
import lombok.Getter;
import lombok.Setter;

import java.io.Serializable;
import java.sql.Timestamp;

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
    private TariffType tariffType;
    private Timestamp createdAt;
    private Timestamp updateAt;
    private Timestamp closeAt;
}
