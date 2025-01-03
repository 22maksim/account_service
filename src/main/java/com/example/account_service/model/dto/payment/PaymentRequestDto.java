package com.example.account_service.model.dto.payment;


import com.example.account_service.model.enums.Currency;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.model.enums.TypePaymentRequest;
import lombok.*;

import java.io.Serializable;

@Builder
@Setter
@Getter
@NoArgsConstructor
@AllArgsConstructor
public class PaymentRequestDto implements Serializable {
    Long requestId;
    String accountId;
    PaymentStatus paymentStatus;
    TypePaymentRequest typePaymentRequest;
    long numberTransaction;
    Long amount;
    Currency currency;
}
