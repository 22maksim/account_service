package com.example.account_service.service.pending;

import com.example.account_service.config.kafka.topic.KafkaTopics;
import com.example.account_service.exeption.DataPaymentRequestEx;
import com.example.account_service.mapper.pending.PendingMapper;
import com.example.account_service.model.Pending;
import com.example.account_service.model.dto.MyMessageDto;
import com.example.account_service.model.dto.payment.PaymentRequestDto;
import com.example.account_service.model.dto.payment.PaymentResponseDto;
import com.example.account_service.model.enums.PaymentStatus;
import com.example.account_service.repository.pending.PendingRepository;
import com.example.account_service.repository.request.RequestRepository;
import com.example.account_service.service.kafka.publisher.KafkaPublisherService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Slf4j
@Service
@RequiredArgsConstructor
public class PendingServiceImpl implements PendingService {
    private final PendingRepository pendingRepository;
    private final PendingMapper pendingMapper;
    private final RequestRepository requestRepository;
    private final KafkaPublisherService<PaymentResponseDto> kafkaPublisherService;
    private final KafkaTopics kafkaTopics;

    @Override
    @Transactional
    public void createPending(PaymentRequestDto paymentRequestDto) {
        if (!requestRepository.existsById(paymentRequestDto.getRequestId())) {
            log.error("request by id {} not exist", paymentRequestDto.getRequestId());
            throw new DataPaymentRequestEx("request by id " + paymentRequestDto.getRequestId() + " not exist");
        }

        Pending pending = pendingMapper.requestDtoToPending(paymentRequestDto);
        pending.setPaymentStatus(PaymentStatus.WAITING);

        PaymentResponseDto responseDto = pendingMapper.pendingToResponseDto(pending);
        responseDto.setRequestId(paymentRequestDto.getRequestId());

        MyMessageDto<PaymentResponseDto> messageDto = new MyMessageDto<>("Request added to queue", responseDto);
        sendInKafka(messageDto);

        pendingRepository.save(pending);
    }

    @Transactional("kafkaTransactionManager")
    public void sendInKafka(MyMessageDto<PaymentResponseDto> messageDto) {
        kafkaPublisherService.sendResponseMessage(messageDto, kafkaTopics.responseAuthorisationTopic());
    }
}
