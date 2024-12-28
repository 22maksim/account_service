package com.example.account_service.service.kafka;

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
public class KafkaServiceImpl implements KafkaService {

    private final KafkaTemplate<String, String> kafkaTemplate;

    private String blockFundsTopic;

    private String confirmPaymentTopic;

    private String cancelPaymentTopic;

    private String successfulAuthorizationTopic;


    public void sendBlockFundsMessage(String accountId, double amount) {
        String message = String.format("{\"accountId\":\"%s\",\"amount\":%.2f}", accountId, amount);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(blockFundsTopic, message);
        future.thenAccept(sendResult -> {
            log.info("Send message in topic {}", sendResult.getRecordMetadata().topic());
        });
        future.exceptionally(ex -> {
            log.error("Failed to send message: {}", ex.getMessage());
            return null;
        });
    }

    public void sendConfirmPaymentMessage(String paymentId, double amount) {
        String message = String.format("{\"paymentId\":\"%s\",\"amount\":%.2f}", paymentId, amount);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(confirmPaymentTopic, paymentId, message);
    }

    public void sendCancelPaymentMessage(String paymentId, String reason) {
        String message = String.format("{\"paymentId\":\"%s\",\"reason\":\"%s\"}", paymentId, reason);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(new ProducerRecord<>(cancelPaymentTopic, paymentId, message));
    }

    public void sendSuccessfulAuthorizationMessage(String accountId, String sessionId) {
        String message = String.format("{\"accountId\":\"%s\",\"sessionId\":\"%s\"}", accountId, sessionId);
        CompletableFuture<SendResult<String, String>> future = kafkaTemplate.send(new ProducerRecord<>(successfulAuthorizationTopic, accountId, message));
    }
}
