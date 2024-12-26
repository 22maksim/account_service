package com.example.account_service.service.account;

import com.example.account_service.dto.AccountRequestDto;
import com.example.account_service.dto.AccountResponseDto;
import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.exeption.UncorrectedValueRequest;
import com.example.account_service.mapper.account.AccountMapper;
import com.example.account_service.model.Account;
import com.example.account_service.model.Owner;
import com.example.account_service.model.enums.AccountStatus;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.owners.OwnersRepository;
import com.example.account_service.service.free_account_numbers.FreeAccountNumbersService;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Isolation;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.validation.annotation.Validated;

import java.sql.Timestamp;
import java.time.LocalDateTime;
import java.util.function.Function;

@Slf4j
@Service
@Validated
@RequiredArgsConstructor
public class AccountServiceImpl implements AccountService {
    private final AccountRepository accountRepository;
    private final FreeAccountNumbersService freeAccountNumbersServiceImpl;
    private final AccountMapper accountMapper;
    private final OwnersRepository ownersRepository;

    @Override
    public AccountResponseDto get(@Positive Long id) {
        if (id == null || id <= 0) {
            log.error("accountRequestDto is null or is empty");
            throw new UncorrectedValueRequest("accountRequestDto is null");
        }
        Account account = accountRepository.findById(id).orElseThrow(() -> new DataAccountException("Account not found"));
        return accountMapper.toAccountResponseDto(account);
    }

    @Transactional
    @Override
    public AccountResponseDto open(@NotNull AccountRequestDto accountRequestDto) {
        if (accountRequestDto == null) {
            log.error("accountRequestDto is null");
            throw new UncorrectedValueRequest("accountRequestDto is null");
        }
        Account account = accountMapper.toAccount(accountRequestDto);
        Owner owner = findOwner(accountRequestDto);
        account.setOwner(owner);
        account = freeAccountNumbersServiceImpl.getFreeAccountNumber(
                accountRequestDto.getType().name(), account.getCurrency().getValue(), saveAccountFunction(account));
        return accountMapper.toAccountResponseDto(account);
    }

    Function<String, Account> saveAccountFunction(@NotNull Account account) {
        return freeAccountNumber -> {
            account.setId(freeAccountNumber);
            return accountRepository.save(account);
        };
    }

    @Transactional
    @Override
    public AccountResponseDto block(@Positive Long id) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new DataAccountException("Account not found"));
        account.setStatus(AccountStatus.BLOCKED);
        return accountMapper.toAccountResponseDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountResponseDto close(@Positive Long id, @NotNull AccountRequestDto accountRequestDto) {
        Account account = accountRepository.findById(id).orElseThrow(() -> new DataAccountException("Account not found"));
        account.setCloseAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setStatus(AccountStatus.CLOSED);
        account = accountRepository.save(account);
        return accountMapper.toAccountResponseDto(accountRepository.save(account));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Owner findOwner(@NotNull AccountRequestDto accountRequestDto) {
        if (ownersRepository.existsOwnerById(accountRequestDto.getOwnerId())) {
            return ownersRepository.findByOwnerId(accountRequestDto.getOwnerId());
        } else {
            return ownersRepository.save(
                    Owner.builder()
                            .ownerId(accountRequestDto.getOwnerId())
                            .type(accountRequestDto.getTypeOwner()).build()
            );
        }
    }
}
