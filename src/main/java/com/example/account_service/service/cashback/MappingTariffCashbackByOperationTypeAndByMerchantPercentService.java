package com.example.account_service.service.cashback;

import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentResponseDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentResponseDto;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;

public interface MappingTariffCashbackByOperationTypeAndByMerchantPercentService {
    TariffOperationTypePercentResponseDto addTariffOperationTypePercent(
            TariffOperationTypePercentRequestDto tariffOperationTypePercentRequestDto);

    void deleteTariffOperationTypePercent(Long id);

    TariffOperationTypePercentResponseDto getTariffOperationTypePercent(Long id);

    MappingTariffCashbackByMerchantPercentResponseDto addMappingTariffCashbackByMerchantPercent(@NotNull MappingTariffCashbackByMerchantPercentRequestDto requestDto);

    MappingTariffCashbackByMerchantPercentResponseDto getMappingTariffCashbackByMerchantPercent(@NotNull @Positive Long mappingCashMerchantId);

    void deleteMappingTariffCashbackByMerchantPercent(@NotNull @Positive Long mappingCashMerchantId);
}