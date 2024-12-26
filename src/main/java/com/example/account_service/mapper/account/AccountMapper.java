package com.example.account_service.mapper.account;

import com.example.account_service.dto.AccountRequestDto;
import com.example.account_service.dto.AccountResponseDto;
import com.example.account_service.model.Account;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "owner", ignore = true)
    Account toAccount(AccountRequestDto accountRequestDto);

    @Mapping(target = "ownerId", source = "owner.id")
    @Mapping(target = "typeOwner", source = "owner.type")
    AccountResponseDto toAccountResponseDto(Account account);
}
