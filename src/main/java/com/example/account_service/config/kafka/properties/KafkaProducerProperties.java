package com.example.account_service.config.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.producer")
public record KafkaProducerProperties(
        String bootstrapServers,
        String keySerializer,
        String valueSerializer
) {
}
