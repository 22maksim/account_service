package com.example.account_service.config.kafka.topic;

import org.springframework.boot.context.properties.ConfigurationProperties;

@ConfigurationProperties("topic.kafka")
public record KafkaTopics(
        String blockFundsTopic,
        String confirmPaymentTopic,
        String cancelPaymentTopic,
        String successfulAuthorizationTopic
) {
}
