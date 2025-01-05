package com.example.account_service.model.dto.cumulative.tariff;

import lombok.AllArgsConstructor;
import lombok.Builder;
import lombok.Data;
import lombok.NoArgsConstructor;

import java.sql.Timestamp;
import java.util.List;

@Data
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class CumulativeTariffResponseDto {
    String type;
    List<Integer> historyRates;
    Timestamp updateAt;
}
