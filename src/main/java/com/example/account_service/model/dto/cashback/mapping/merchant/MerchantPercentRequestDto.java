package com.example.account_service.model.dto.cashback.mapping.merchant;

import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.*;

@Getter
@Setter
@Builder
@NoArgsConstructor
@AllArgsConstructor
public class MerchantPercentRequestDto {
    @NotNull
    @Positive
    private Long merchantId;
    @NotNull
    @Positive
    private Long percentsId;
}
