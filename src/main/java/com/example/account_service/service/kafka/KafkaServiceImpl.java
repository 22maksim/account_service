package com.example.account_service.service.kafka;

import com.example.account_service.config.kafka.partitions.KafkaPartitions;
import com.example.account_service.config.kafka.topic.KafkaTopics;
import com.example.account_service.dto.MyMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaServiceImpl<T> implements KafkaService {
    private final KafkaTemplate<String, Object> kafkaTemplate;
    private final KafkaTopics kafkaTopics;
    private final KafkaPartitions kafkaPartitions;

    public void sendBlockFundsMessage(MyMessageDto<T> message) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopics.blockFundsTopic(), kafkaPartitions.paymentResponse(), message);
        future.exceptionally(ex -> {
            log.error("Failed to send message in block topic: {}", ex.getMessage());
            return null;
        });
    }

    public void sendConfirmPaymentMessage(MyMessageDto<T> message) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopics.confirmPaymentTopic(), kafkaPartitions.paymentResponse(), message);
        future.exceptionally(ex -> {
            log.error("Failed to send message in confirm topic: {}", ex.getMessage());
            return null;
        });
    }

    public void sendCancelPaymentMessage(MyMessageDto<T> message) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopics.cancelPaymentTopic(), kafkaPartitions.paymentResponse(), message);
        future.exceptionally(ex -> {
            log.error("Failed to send message in cancelled topic: {}", ex.getMessage());
            return null;
        });
    }

    public void sendSuccessfulAuthorizationMessage(MyMessageDto<T> message) {
        CompletableFuture<SendResult<String, Object>> future =
                kafkaTemplate.send(kafkaTopics.authorizationTopic(), kafkaPartitions.paymentResponse(), message);
        future.exceptionally(ex -> {
            log.error("Failed to send message in authorization topic: {}", ex.getMessage());
            return null;
        });
    }
}
