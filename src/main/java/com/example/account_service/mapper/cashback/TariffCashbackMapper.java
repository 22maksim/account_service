package com.example.account_service.mapper.cashback;

import com.example.account_service.exeption.DataCashbackException;
import com.example.account_service.model.cashback.tariff.TariffCashback;
import com.example.account_service.model.cashback.tariff.TariffMerchantPercents;
import com.example.account_service.model.cashback.tariff.TariffOperationTypePercents;
import com.example.account_service.model.dto.cashback.ResponseTariffCashbackDto;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

import java.util.List;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface TariffCashbackMapper {

    @Mapping(target = "tariffOperationTypePercentsIds", source = "tariffOperationTypePercents", qualifiedByName = "toTariffOperationTypePercentsIds")
    @Mapping(target = "tariffMerchantPercentIds", source = "tariffMerchantPercents", qualifiedByName = "toTariffMerchantPercentsIds")
    ResponseTariffCashbackDto toResponseDto(TariffCashback tariffCashback);

    @Named("toTariffOperationTypePercentsIds")
    default List<Long> toTariffOperationTypePercentsIds(List<TariffOperationTypePercents> tariffOperationTypePercents) {
        if (tariffOperationTypePercents == null || tariffOperationTypePercents.isEmpty()) {
            throw new DataCashbackException("tariffOperationTypePercents is null or empty");
        }
        return tariffOperationTypePercents.stream()
                .map(TariffOperationTypePercents::getId)
                .toList();
    }

    @Named("toTariffMerchantPercentsIds")
    default List<Long> toTariffMerchantPercentsIds(List<TariffMerchantPercents> tariffMerchantPercents) {
        if (tariffMerchantPercents == null || tariffMerchantPercents.isEmpty()) {
            throw new DataCashbackException("tariffMerchantPercents is null or empty");
        }
        return tariffMerchantPercents.stream()
                .map(TariffMerchantPercents::getId)
                .toList();
    }
}
