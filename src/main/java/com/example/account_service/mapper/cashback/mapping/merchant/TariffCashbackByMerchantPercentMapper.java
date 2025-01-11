package com.example.account_service.mapper.cashback.mapping.merchant;

import com.example.account_service.model.cashback.tariff.TariffMerchantPercents;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.MappingTariffCashbackByMerchantPercentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffCashbackByMerchantPercentMapper {

    @Mapping(target = "tariffCashbackId", source = "tariffCashback.id")
    @Mapping(target = "merchantPercentId", source = "merchantPercent.id")
    MappingTariffCashbackByMerchantPercentResponseDto toResponseDto(TariffMerchantPercents tariffMerchantPercents);

    TariffMerchantPercents toEntity(MappingTariffCashbackByMerchantPercentRequestDto requestDto);
}
