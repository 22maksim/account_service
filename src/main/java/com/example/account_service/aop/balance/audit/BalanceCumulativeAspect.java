package com.example.account_service.aop.balance.audit;

import com.example.account_service.mapper.balance.BalanceMapper;
import com.example.account_service.model.Account;
import com.example.account_service.model.Balance;
import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.service.balance.audit.BalanceAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.List;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BalanceCumulativeAspect {
    private final BalanceMapper balanceMapper;
    private final BalanceAuditService balanceAuditServiceImpl;

    @Pointcut("@annotation(auditBalanceCumulative)")
    public void auditWithBalanceCumulative(AuditBalanceCumulative auditBalanceCumulative) {
    }

    @AfterReturning(argNames = "auditBalanceCumulative, resultList",
            pointcut = "auditWithBalanceCumulative(auditBalanceCumulative)", returning = "resultList")
    public void logAuditCumulativeBalances(
            AuditBalanceCumulative auditBalanceCumulative, List<Account> resultList
    ) {
        String typeOperation = auditBalanceCumulative.typeOperation().name();
        resultList.forEach(account -> {
            if (account == null) {
                log.info("Account is null");
                return;
            }
            Balance balance = account.getBalance();
            BalanceResponseDto responseDto = balanceMapper.balanceToBalanceResponseDto(balance);

            balanceAuditServiceImpl.saveBalanceAudit(responseDto);

            log.info("Saved cumulative audit from account balance. Type: {}. Id: {}", typeOperation, balance.getId());
        });
    }
}