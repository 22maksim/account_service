package com.example.account_service.dto;

import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.model.enums.TypePaymentRequest;
import com.example.account_service.model.enums.TypeTransactionBalance;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;
import java.math.BigDecimal;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto implements Serializable {
    Long requestId;
    PaymentStatus paymentStatus;
    TypeTransactionBalance typeTransactionBalance;
    TypePaymentRequest typePaymentRequest;
    int verificationCode;
    long numberTransaction;
    BigDecimal amount;
    Currency currency;
    String message;
}
