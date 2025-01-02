package com.example.account_service.service.kafka.listeners;

import com.example.account_service.dto.MyMessageDto;
import com.example.account_service.dto.PaymentRequestDto;
import com.example.account_service.service.dms.DmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class AuthorizationTransactionKafkaListener {
    private final DmsService dmsServiceImpl;

    @KafkaListener(topics = "authorization-topic", groupId = "authorisation-group")
    public void onAuthorizationPaymentTransaction(MyMessageDto<PaymentRequestDto> myMessageDto, Acknowledgment ack) {
        PaymentRequestDto requestDto = myMessageDto.getMyEntity();
        try {
            if (requestDto == null) {
                log.info("requestDto is null");
                throw new IllegalArgumentException("requestDto is null");
            } else {
                System.out.println("requestDto = " + requestDto);
                dmsServiceImpl.authorisationRequestForPayment(requestDto);
                ack.acknowledge();
            }
        } catch (Exception e) {
            log.error("Authorization failed to start. Exception: {}", e.getMessage());
        }
    }
}
