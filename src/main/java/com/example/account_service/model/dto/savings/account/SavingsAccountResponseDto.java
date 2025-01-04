package com.example.account_service.model.dto.savings.account;

import com.example.account_service.model.enums.TariffType;
import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class SavingsAccountResponseDto {
    String id;
    int rate;
    TariffType tariffType;
}
