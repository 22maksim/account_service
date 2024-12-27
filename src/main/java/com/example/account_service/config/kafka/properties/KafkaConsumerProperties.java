package com.example.account_service.config.kafka.properties;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties(prefix = "spring.kafka.consumer")
public record KafkaConsumerProperties(
        String bootstrapServers,
        String groupId,
        String keyDeserializer,
        String valueDeserializer,
        String autoOffsetReset
) {
}
