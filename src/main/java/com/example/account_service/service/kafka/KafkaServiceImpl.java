package com.example.account_service.service.kafka;

import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Properties;

@Service
@RequiredArgsConstructor
public class KafkaServiceImpl implements KafkaService {

    private final KafkaProducer<String, String> producer;

    @Value("${kafka.topic.blockFunds}")
    private String blockFundsTopic;

    @Value("${kafka.topic.confirmPayment}")
    private String confirmPaymentTopic;

    @Value("${kafka.topic.cancelPayment}")
    private String cancelPaymentTopic;

    @Value("${kafka.topic.successfulAuthorization}")
    private String successfulAuthorizationTopic;

    public KafkaService(@Value("${kafka.bootstrap.servers}") String bootstrapServers) {
        Properties props = new Properties();
        props.put("bootstrap.servers", bootstrapServers);
        props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");
        this.producer = new KafkaProducer<>(props);
    }

    public void sendBlockFundsMessage(String accountId, double amount) {
        String message = String.format("{\"accountId\":\"%s\",\"amount\":%.2f}", accountId, amount);
        producer.send(new ProducerRecord<>(blockFundsTopic, accountId, message));
    }

    public void sendConfirmPaymentMessage(String paymentId, double amount) {
        String message = String.format("{\"paymentId\":\"%s\",\"amount\":%.2f}", paymentId, amount);
        producer.send(new ProducerRecord<>(confirmPaymentTopic, paymentId, message));
    }

    public void sendCancelPaymentMessage(String paymentId, String reason) {
        String message = String.format("{\"paymentId\":\"%s\",\"reason\":\"%s\"}", paymentId, reason);
        producer.send(new ProducerRecord<>(cancelPaymentTopic, paymentId, message));
    }

    public void sendSuccessfulAuthorizationMessage(String accountId, String sessionId) {
        String message = String.format("{\"accountId\":\"%s\",\"sessionId\":\"%s\"}", accountId, sessionId);
        producer.send(new ProducerRecord<>(successfulAuthorizationTopic, accountId, message));
    }

    public void close() {
        producer.close();
    }
}
