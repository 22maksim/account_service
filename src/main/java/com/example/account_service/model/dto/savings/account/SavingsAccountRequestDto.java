package com.example.account_service.model.dto.savings.account;

import com.example.account_service.model.enums.TariffType;
import jakarta.validation.constraints.NotEmpty;
import jakarta.validation.constraints.NotNull;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;


@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccountRequestDto {
    @NotEmpty
    String accountId;
    @NotNull
    TariffType tariffType;
}
