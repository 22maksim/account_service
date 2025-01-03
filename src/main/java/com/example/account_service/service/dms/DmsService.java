package com.example.account_service.service.dms;

import com.example.account_service.model.dto.payment.PaymentRequestDto;

public interface DmsService {
    void authorisationRequestForPayment();

    void cancelledRequestForPayment(PaymentRequestDto paymentRequestDto);

    void clearingRequestForPayment(PaymentRequestDto paymentRequestDto);
}
