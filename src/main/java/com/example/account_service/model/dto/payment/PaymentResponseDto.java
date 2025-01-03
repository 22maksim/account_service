package com.example.account_service.model.dto.payment;

import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.model.enums.TypePaymentRequest;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

import java.io.Serializable;

@Getter
@Setter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentResponseDto implements Serializable {
    Long requestId;
    Long accountId;
    PaymentStatus paymentStatus;
    TypePaymentRequest typePaymentRequest;
    long numberTransaction;
    Long amount;
    Currency currency;
}
