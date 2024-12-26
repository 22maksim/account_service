package com.example.account_service.service.account;

import com.example.account_service.dto.AccountRequestDto;
import com.example.account_service.dto.AccountResponseDto;

public interface AccountService {
    public AccountResponseDto get(Long id);

    public AccountResponseDto open(AccountRequestDto accountRequestDto);

    public AccountResponseDto block(Long id);

    public AccountResponseDto close(Long id, AccountRequestDto accountRequestDto);
}
