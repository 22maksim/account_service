package com.example.account_service.mapper.cashback.mapping.type_operation;

import com.example.account_service.model.cashback.tariff.TariffOperationTypePercents;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentRequestDto;
import com.example.account_service.model.dto.cashback.mapping.cashback_mapping.TariffOperationTypePercentResponseDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffOperationTypePercentMapper {

    @Mapping(target = "tariffCashbackId", source = "tariffCashback.id")
    @Mapping(target = "operationTypePercentId", source = "operationTypePercent.id")
    TariffOperationTypePercentResponseDto toTariffOperationTypeResponseDto(
            TariffOperationTypePercents tariffOperationTypePercents);

    TariffOperationTypePercents toEntity(TariffOperationTypePercentRequestDto requestDto);
}
