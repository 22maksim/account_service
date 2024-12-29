package com.example.account_service.service.account;

import com.example.account_service.dto.AccountRequestDto;
import com.example.account_service.dto.AccountResponseDto;

public interface AccountService {
    AccountResponseDto get(String id);

    AccountResponseDto open(AccountRequestDto accountRequestDto);

    AccountResponseDto block(String id);

    AccountResponseDto close(String id, AccountRequestDto accountRequestDto);

    boolean existsAccountById(String id);
}
