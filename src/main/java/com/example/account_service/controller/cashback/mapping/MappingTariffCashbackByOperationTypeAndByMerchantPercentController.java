package com.example.account_service.controller.cashback.mapping;

import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentResponseDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentResponseDto;
import com.example.account_service.service.cashback.MappingTariffCashbackByOperationTypeAndByMerchantPercentService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("api/v1/mapping-tariff-cashback-by-operation-type-and-by-merchant-percent")
@RequiredArgsConstructor
public class MappingTariffCashbackByOperationTypeAndByMerchantPercentController {
    private final MappingTariffCashbackByOperationTypeAndByMerchantPercentService
            mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl;

    @PostMapping("/operation-type")
    public TariffOperationTypePercentResponseDto addTariffOperationTypePercent(
            @RequestBody @NotNull TariffOperationTypePercentRequestDto tariffOperationTypePercentRequestDto) {
        return mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
                .addTariffOperationTypePercent(tariffOperationTypePercentRequestDto);
    }

    @GetMapping("/{id}/operation-type")
    public  TariffOperationTypePercentResponseDto getTariffOperationTypePercent(
            @PathVariable("id") @NotNull @Positive Long tariffOperationTypePercentId) {
        return mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
                .getTariffOperationTypePercent(tariffOperationTypePercentId);
    }

    @DeleteMapping("/{id}/operation-type")
    public  void deleteTariffOperationTypePercent(
            @PathVariable("id") @NotNull @Positive Long tariffOperationTypePercentId) {
        mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
                .deleteTariffOperationTypePercent(tariffOperationTypePercentId);
    }

    @PostMapping("/merchant")
    public MappingTariffCashbackByMerchantPercentResponseDto addMappingTariffCashbackByMerchantPercent(
            @RequestBody @NotNull MappingTariffCashbackByMerchantPercentRequestDto requestDto) {
        return mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
                .addMappingTariffCashbackByMerchantPercent(requestDto);
    }

    @GetMapping("/{id}/merchant")
    public MappingTariffCashbackByMerchantPercentResponseDto getMappingTariffCashbackByMerchantPercent(
            @PathVariable("id") @NotNull @Positive Long mappingCashMerchantId) {
        return mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
                .getMappingTariffCashbackByMerchantPercent(mappingCashMerchantId);
    }

    @DeleteMapping("/{id}/merchant")
    public void deleteMappingTariffCashbackByMerchantPercent(
            @PathVariable("id") @NotNull @Positive Long mappingCashMerchantId) {
        mappingTariffCashbackByOperationTypeAndByMerchantPercentServiceImpl
                .deleteMappingTariffCashbackByMerchantPercent(mappingCashMerchantId);
    }
}
