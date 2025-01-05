package com.example.account_service.model.dto.cumulative.tariff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

@Data
@NoArgsConstructor
@AllArgsConstructor
@Builder
public class CumulativeTariffRequestDto {
    String type;
    int rate;
}
