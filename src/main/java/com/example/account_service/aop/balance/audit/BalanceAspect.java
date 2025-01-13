package com.example.account_service.aop.balance.audit;

import com.example.account_service.model.dto.balance.BalanceResponseDto;
import com.example.account_service.model.dto.balance.BalanceTransactionRequestDto;
import com.example.account_service.model.enums.TypeBalanceOperation;
import com.example.account_service.service.balance.audit.BalanceAuditService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.aspectj.lang.JoinPoint;
import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.AfterReturning;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;
import org.springframework.stereotype.Component;

import java.util.Arrays;

@Slf4j
@Aspect
@Component
@RequiredArgsConstructor
public class BalanceAspect {
    private final BalanceAuditService balanceAuditServiceImpl;

    @Pointcut("@annotation(auditBalance)")
    public void annotationWithAudit(AuditBalance auditBalance) {}


    @Around(value = "annotationWithAudit(auditBalance)", argNames = "proceedingJoinPoint,auditBalance")
    public Object logAuditBalance(ProceedingJoinPoint proceedingJoinPoint, AuditBalance auditBalance) throws Throwable {
        TypeBalanceOperation typeOperation = auditBalance.typeOperation();

        Object[] args = proceedingJoinPoint.getArgs();

        BalanceTransactionRequestDto transactionRequestDto = (BalanceTransactionRequestDto) args[1];

        Object result;
        try {
            result = proceedingJoinPoint.proceed();
        } catch (Throwable throwable) {
            log.error("Error during method execution: {}", throwable.getMessage());
            throw throwable;
        }

        if (result instanceof BalanceResponseDto responseDto) {
            balanceAuditServiceImpl.updateBalanceAudit(responseDto, transactionRequestDto);
            log.info("Audit balance operation: {} {}", typeOperation, responseDto.getId());
        } else {
            log.warn("The return value is not a BalanceResponseDto: {}", result);
        }

        return result;
    }
}
