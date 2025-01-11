package com.example.account_service.model.dto.cashback;

import lombok.*;

import java.io.Serializable;
import java.util.List;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class RequestTariffCashbackDto implements Serializable {
    private String description;
    private List<Long> tariffMerchantPercentIds;
    private List<Long> tariffOperationTypePercentsIds;
}
