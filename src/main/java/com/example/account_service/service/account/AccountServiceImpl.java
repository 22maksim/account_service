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
import io.netty.channel.ChannelHandler;
import jakarta.validation.constraints.NotNull;
import jakarta.validation.constraints.Positive;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.cache.annotation.CacheEvict;
import org.springframework.cache.annotation.CachePut;
import org.springframework.cache.annotation.Cacheable;
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
    public AccountResponseDto get(String id) {
        if (id.isEmpty()) {
            log.error("accountRequestDto is null or is empty");
            throw new UncorrectedValueRequest("accountRequestDto is null");
        }
        Account account = accountRepository.findAccountById(id);
        return accountMapper.toAccountResponseDto(account);
    }

    @Transactional
    @Override
    public AccountResponseDto open(AccountRequestDto accountRequestDto) {
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
    public AccountResponseDto block(String id) {
        Account account = accountRepository.findAccountById(id);
        account.setStatus(AccountStatus.BLOCKED);
        return accountMapper.toAccountResponseDto(accountRepository.save(account));
    }

    @Transactional
    @Override
    public AccountResponseDto close(String id, AccountRequestDto accountRequestDto) {
        Account account = accountRepository.findAccountById(id);
        account.setCloseAt(Timestamp.valueOf(LocalDateTime.now()));
        account.setStatus(AccountStatus.CLOSED);
        account = accountRepository.save(account);
        return accountMapper.toAccountResponseDto(accountRepository.save(account));
    }

    @Transactional(isolation = Isolation.REPEATABLE_READ)
    public Owner findOwner(AccountRequestDto accountRequestDto) {
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

    @Override
    public boolean existsAccountById(String id) {
        return accountRepository.existsAccountById(id);
    }
}
