package com.example.account_service.async.scheduled.payment.request.authorisation;

import com.example.account_service.service.dms.DmsService;
import lombok.RequiredArgsConstructor;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@Component
@RequiredArgsConstructor
public class AutoStartAuthorisationPending {
    private final DmsService dmsServiceImpl;

    @Scheduled(fixedRate = 300000)
    public void startAuthorisation() {
        dmsServiceImpl.authorisationRequestForPayment();
    }
}
