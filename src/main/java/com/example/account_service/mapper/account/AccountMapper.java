package com.example.account_service.mapper.account;

import com.example.account_service.dto.AccountRequestDto;
import com.example.account_service.dto.AccountResponseDto;
import com.example.account_service.model.Account;
import com.example.account_service.model.enums.Currency;
import org.mapstruct.Mapper;
import org.mapstruct.Mapping;
import org.mapstruct.Named;
import org.mapstruct.ReportingPolicy;

@Mapper(componentModel = "spring", unmappedTargetPolicy = ReportingPolicy.IGNORE)
public interface AccountMapper {

    @Mapping(target = "ownerId", source = "owner.ownerId")
    @Mapping(target = "typeOwner", source = "owner.type")
    AccountResponseDto toAccountResponseDto(Account account);

    @Mapping(target = "owner", ignore = true)
    Account toAccount(AccountRequestDto accountRequestDto);

}
