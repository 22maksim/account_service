package com.example.account_service.mapper.cashback.mapping.merchant;

import com.example.account_service.model.cashback.tariff.MerchantPercent;
import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.merchant.MerchantPercentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface MerchantPercentMapper {

    MerchantPercent toEntity(MerchantPercentRequestDto requestDto);

    MerchantPercentResponseDto toResponseDto(MerchantPercent merchantPercent);
}
