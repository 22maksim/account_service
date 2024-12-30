package com.example.account_service.config.kafka.partitions;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("kafka.partitions")
public record KafkaPartitions(
        String paymentRequest,
        String paymentResponse
) {
}
