package com.example.account_service.service.cashback.merchant;

import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentResponseDto;

public interface MappingMerchantByPercentService {
    MerchantPercentResponseDto addMappingMerchantByPercent(MerchantPercentRequestDto merchantPercentRequestDto);

    MerchantPercentResponseDto getMappingMerchantByPercent(Long id);

    void deleteMappingMerchantByPercent(Long id);
}
