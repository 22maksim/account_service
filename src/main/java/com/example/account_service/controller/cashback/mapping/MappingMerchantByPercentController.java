package com.example.account_service.controller.cashback.mapping;

import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentResponseDto;
import com.example.account_service.service.cashback.merchant.MappingMerchantByPercentService;
import jakarta.validation.Valid;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

@RestController
@RequestMapping("/api/v1/mapping-merchant-by-percent")
@RequiredArgsConstructor
public class MappingMerchantByPercentController {
    private final MappingMerchantByPercentService mappingMerchantByPercentServiceImpl;

    @PostMapping
    public MerchantPercentResponseDto addMappingMerchantByPercent(@RequestBody @NotNull @Valid MerchantPercentRequestDto merchantPercentRequestDto) {
        return mappingMerchantByPercentServiceImpl.addMappingMerchantByPercent(merchantPercentRequestDto);
    }

    @GetMapping("/{id}")
    public MerchantPercentResponseDto getMappingMerchantByPercent(@PathVariable @NotNull @Positive Long id) {
        return mappingMerchantByPercentServiceImpl.getMappingMerchantByPercent(id);
    }

    @DeleteMapping("/{id}")
    public void deleteMappingMerchantByPercent(@PathVariable @NotNull @Positive Long id) {
        mappingMerchantByPercentServiceImpl.deleteMappingMerchantByPercent(id);
    }
}
