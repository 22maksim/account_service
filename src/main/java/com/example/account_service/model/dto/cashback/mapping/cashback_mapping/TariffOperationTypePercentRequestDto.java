package com.example.account_service.model.dto.cashback.mapping.cashback_mapping;

import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class TariffOperationTypePercentRequestDto {
    private Long tariffCashbackId;
    private Long operationTypePercentId;
}
