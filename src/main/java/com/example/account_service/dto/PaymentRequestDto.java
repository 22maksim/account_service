package com.example.account_service.dto;


import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.model.enums.TypePaymentRequest;
import com.example.account_service.model.enums.TypeTransactionBalance;
import lombok.*;

import java.io.Serializable;
import java.math.BigDecimal;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto implements Serializable {
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
