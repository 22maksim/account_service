package com.example.account_service.aop.balance.audit;

import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.enums.TypeBalanceOperation;
import com.example.account_service.service.balance.audit.BalanceAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BalanceAspect {
    private final BalanceAuditService balanceAuditServiceImpl;

    @Pointcut("@annotation(auditBalance)")
    public void annotationWithAudit(AuditBalance auditBalance) {}

    @AfterReturning(argNames = "joinPoint, auditBalance, responseDto", pointcut = "annotationWithAudit(auditBalance)",
            returning = "responseDto")
    public void logAuditBalance(JoinPoint joinPoint, AuditBalance auditBalance, BalanceResponseDto responseDto) {
        TypeBalanceOperation typeOperation = auditBalance.typeOperation();

        balanceAuditServiceImpl.saveBalanceAudit(responseDto);

        log.info("Audit balance operation: {} {}", typeOperation, responseDto.getId());
    }
}
