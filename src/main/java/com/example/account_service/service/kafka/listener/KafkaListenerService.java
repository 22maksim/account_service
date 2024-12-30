package com.example.account_service.service.kafka.listener;

import com.example.account_service.dto.MyMessageDto;
import com.example.account_service.dto.PaymentRequestDto;
import lombok.RequiredArgsConstructor;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class KafkaListenerService {

    @KafkaListener(topics = "${topic.kafka.authorizationTopic}", groupId = "${kafka.partitions.paymentRequest}")
    public void onAuthorizationPaymentTransaction(MyMessageDto<PaymentRequestDto> myMessageDto) {

    }

    @KafkaListener(topics = "${topic.kafka.blockFundsTopic}", groupId = "${kafka.partitions.paymentRequest}")
    public void onBlockFundsMessage(MyMessageDto<PaymentRequestDto> myMessageDto) {

    }

    @KafkaListener(topics = "${topic.kafka.authorizationTopic}")
    public void onClearingTransaction(MyMessageDto<PaymentRequestDto> myMessageDto) {

    }

    @KafkaListener(topics = "${topic.kafka.cancelPaymentTopic}", groupId = "${kafka.partitions.paymentRequest}")
    public void onCancelledTransaction(MyMessageDto<PaymentRequestDto> myMessageDto) {

    }
}
