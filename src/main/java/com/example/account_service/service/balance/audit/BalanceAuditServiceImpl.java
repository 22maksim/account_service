package com.example.account_service.service.balance.audit;

import com.example.account_service.exeption.DataAccountException;
import com.example.account_service.model.Account;
import com.example.account_service.model.BalanceAudit;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.repository.account.AccountRepository;
import com.example.account_service.repository.balance.audit.BalanceAuditRepository;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class BalanceAuditServiceImpl implements BalanceAuditService {
    private final BalanceAuditRepository balanceAuditRepository;
    private final AccountRepository accountRepository;

    @Override
    public void saveBalanceAudit(BalanceResponseDto balanceResponseDto) {
        Account account = accountRepository.findAccountById(balanceResponseDto.getAccount_id());
        if (account == null) {
            log.error("Account not found");
            throw new DataAccountException("BalanceAudit... Account not found");
        }
        balanceAuditRepository.save(BalanceAudit.builder()
                .id(balanceResponseDto.getId())
                .account(account)
                .type(account.getType())
                .authorizationAmount(balanceResponseDto.getAuthorizationBalance())
                .actual_amount(balanceResponseDto.getCurrentBalance())
                .build());
    }
}
