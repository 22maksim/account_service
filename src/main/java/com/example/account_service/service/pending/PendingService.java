package com.example.account_service.service.pending;

import com.example.account_service.model.dto.payment.PaymentRequestDto;

public interface PendingService {

    void createPending(PaymentRequestDto paymentRequestDto);
}
