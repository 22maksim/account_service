package com.example.account_service.aop.balance.audit;

import com.example.account_service.model.enums.TypeBalanceOperation;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

@Retention(RetentionPolicy.RUNTIME)
@Target(ElementType.METHOD)
public @interface AuditBalanceCumulative {
    TypeBalanceOperation typeOperation();
}
