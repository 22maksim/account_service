package com.example.account_service.service.kafka.listeners;

import com.example.account_service.model.dto.MyMessageDto;
import com.example.account_service.model.dto.payment.PaymentRequestDto;
import com.example.account_service.service.dms.DmsService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.stereotype.Component;

@Slf4j
@Component
@RequiredArgsConstructor
public class CancelledTransactionKafkaListener {
    public final DmsService dmsServiceImpl;

    @KafkaListener(topics = "${topic.kafka.cancelPaymentTopic}", groupId = "cancelled-group")
    public void onCancelledTransaction(MyMessageDto<PaymentRequestDto> myMessageDto, Acknowledgment ack) {
        PaymentRequestDto requestDto = myMessageDto.getMyEntity();
        try {
            if (requestDto == null) {
                log.info("RequestDto is null");
                throw new IllegalArgumentException("RequestDto is null");
            } else {
                dmsServiceImpl.cancelledRequestForPayment(requestDto);
                ack.acknowledge();
            }
        } catch (Exception e) {
            log.error("Cancelled failed to start. Exception: {}", e.getMessage());
        }
    }
}
