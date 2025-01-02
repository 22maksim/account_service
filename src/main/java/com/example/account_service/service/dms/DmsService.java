package com.example.account_service.service.dms;

import com.example.account_service.dto.PaymentRequestDto;

public interface DmsService {
    void authorisationRequestForPayment(PaymentRequestDto paymentRequestDto);

    void cancelledRequestForPayment(PaymentRequestDto paymentRequestDto);

    void clearingRequestForPayment(PaymentRequestDto paymentRequestDto);
}
