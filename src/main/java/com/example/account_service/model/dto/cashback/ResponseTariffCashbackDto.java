package com.example.account_service.model.dto.cashback;

import lombok.*;

import java.time.Instant;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class ResponseTariffCashbackDto {
    private Long id;
    private String description;
    private Instant created;
    private List<Long> tariffMerchantPercentIds;
    private List<Long> tariffOperationTypePercentsIds;
}
