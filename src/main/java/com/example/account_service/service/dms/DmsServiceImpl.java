package com.example.account_service.service.dms;

import com.example.account_service.config.kafka.topic.KafkaTopics;
import com.example.account_service.dto.MyMessageDto;
import com.example.account_service.dto.PaymentRequestDto;
import com.example.account_service.service.kafka.publisher.KafkaPublisherService;
import lombok.RequiredArgsConstructor;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class DmsServiceImpl implements DmsService {
    private final KafkaPublisherService<PaymentRequestDto> kafkaPublisherService;
    private final KafkaTopics kafkaTopics;

    @Override
    public void authorisationRequestForPayment(PaymentRequestDto paymentRequestDto) {

        MyMessageDto<PaymentRequestDto> myMessageDto = new MyMessageDto();
        kafkaPublisherService.sendResponseMessage();
    }

    @Override
    public void cancelledRequestForPayment(PaymentRequestDto paymentRequestDto) {

    }

    @Override
    public void clearingRequestForPayment(PaymentRequestDto paymentRequestDto) {

    }
}
