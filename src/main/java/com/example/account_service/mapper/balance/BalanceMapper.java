package com.example.account_service.mapper.balance;

import com.example.account_service.dto.balance.BalanceOpenRequestDto;
import com.example.account_service.dto.balance.BalanceResponseDto;
import com.example.account_service.model.Balance;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface BalanceMapper {

    @Mapping(target = "account_id", source = "account.id")
    BalanceResponseDto balanceToBalanceResponseDto(Balance balance);

    @Mapping(target = "account", ignore = true)
    @Mapping(target = "id", ignore = true)
    Balance balanceRequestDtoToBalance(BalanceOpenRequestDto balanceOpenRequestDto);

    @Mapping(target = "id", ignore = true)
    BalanceResponseDto balanceRequestToBalanceResponseDto(BalanceOpenRequestDto balanceOpenRequestDto);
}
