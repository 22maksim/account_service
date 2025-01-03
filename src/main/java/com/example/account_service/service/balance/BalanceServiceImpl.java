package com.example.account_service.service.balance;

import com.example.account_service.model.dto.balance.BalanceOpenRequestDto;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.exeption.DataBalanceException;
import com.example.account_service.mapper.balance.BalanceMapper;
import com.example.account_service.model.Account;
import com.example.account_service.model.Balance;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.balance.BalanceRepository;
import com.example.account_service.repository.owners.OwnersRepository;
import com.example.account_service.service.balance.clearing.ClearingProcessServiceImpl;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.cache.annotation.CacheConfig;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@CacheConfig(cacheNames = "balances")
@RequiredArgsConstructor
@Service
public class BalanceServiceImpl implements BalanceService {
    private final OwnersRepository ownersRepository;
    private final AccountRepository accountRepository;
    private final BalanceRepository balanceRepository;
    private final BalanceMapper balanceMapper;
    private final ClearingProcessServiceImpl clearingProcessServiceImpl;

    @Override
    public BalanceResponseDto getBalanceById(Long id) {
        Balance balance = balanceRepository.findBalanceById(id);
        if (balance == null) {
            log.error("Balance not found. Id: {}", id);
            throw new DataBalanceException("Balance not found. Id: " + id);
        }
        return balanceMapper.balanceToBalanceResponseDto(balance);
    }

    @Override
    @Transactional
    public BalanceResponseDto createBalance(BalanceOpenRequestDto balanceDto) {
        Balance balance = balanceMapper.balanceRequestDtoToBalance(balanceDto);
        Account account = accountRepository.findAccountById(balanceDto.getAccountId());
        validator(account);
        balance.setAccount(account);
        return balanceMapper.balanceToBalanceResponseDto(balanceRepository.save(balance));
    }

    @Override
    @Transactional
    public BalanceResponseDto updateBalance(Long id, BalanceTransactionRequestDto transactionRequest) {
        Balance balance = clearingProcessServiceImpl.processing(transactionRequest, id);
        return balanceMapper.balanceToBalanceResponseDto(balance);
    }

    private void validator(Account account) {
        if (account == null) {
            log.error("Authorization failed");
            throw new DataBalanceException("Invalid account id");
        }
        boolean ownerExist = ownersRepository.existsOwnerByOwnerId(account.getOwner().getOwnerId());
        if (!ownerExist) {
            log.info("Owner {} don't exists. Authorization failed", account.getOwner().getOwnerId());
            throw new DataBalanceException("Owners don't exists. Authorization failed");
        }
    }
}
