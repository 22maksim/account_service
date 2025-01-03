package com.example.account_service.service.kafka.listeners;

import com.example.account_service.model.dto.MyMessageDto;
import com.example.account_service.model.dto.payment.PaymentRequestDto;
import com.example.account_service.service.dms.DmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class ClearingTransactionKafkaListener {
    private final DmsService dmsServiceImpl;

    @KafkaListener(topics = "${topic.kafka.authorizationTopic}", groupId = "clearing-group")
    public void onClearingTransaction(MyMessageDto<PaymentRequestDto> myMessageDto) {
        PaymentRequestDto requestDto = myMessageDto.getMyEntity();
        try {
            if (requestDto == null) {
                log.info("Received empty PaymentRequestDto");
            } else {
                dmsServiceImpl.clearingRequestForPayment(requestDto);
            }
        } catch (Exception e) {
            log.error("Clearing failed to start. Exception: {}", e.getMessage());
        }
    }
}
