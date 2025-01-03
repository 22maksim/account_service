package com.example.account_service.config.kafka;

import com.example.account_service.config.kafka.topic.KafkaTopics;
import com.example.account_service.model.dto.MyMessageDto;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.core.*;
import org.springframework.kafka.transaction.KafkaTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import java.io.Serializable;

@EnableKafka
@Configuration
@EnableTransactionManagement
@RequiredArgsConstructor
public class KafkaConfig<T extends Serializable> {
    private final KafkaProperties kafkaProperties;
    private final KafkaTopics kafkaTopics;

    @Bean
    public ProducerFactory<String, MyMessageDto<T>> producerFactory() {
        DefaultKafkaProducerFactory<String, MyMessageDto<T>> producerFactory = new DefaultKafkaProducerFactory<>(
                kafkaProperties.buildProducerProperties()
        );
        producerFactory.setTransactionIdPrefix("tx-");
        return producerFactory;
    }

    @Bean
    public KafkaTransactionManager<String, MyMessageDto<T>> kafkaTransactionManager(
            ProducerFactory<String, MyMessageDto<T>> producerFactory
    ) {
        return new KafkaTransactionManager<>(producerFactory);
    }

    @Bean
    public KafkaTemplate<String, MyMessageDto<T>> kafkaTemplate(
            ProducerFactory<String, MyMessageDto<T>> producerFactory
    ) {
        return new KafkaTemplate<>(producerFactory);
    }

    @Bean
    public ConsumerFactory<String, MyMessageDto<T>> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                kafkaProperties.buildConsumerProperties()
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, MyMessageDto<T>> kafkaListenerContainerFactory(
            ConsumerFactory<String, MyMessageDto<T>> consumerFactory
    ) {
        ConcurrentKafkaListenerContainerFactory<String, MyMessageDto<T>> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory);
        return factory;
    }

    @Bean
    public NewTopic paymentAuthorizationTopic() {
        return TopicBuilder.name(kafkaTopics.authorizationTopic())
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentClearingTopic() {
        return TopicBuilder.name(kafkaTopics.clearingPaymentTopic())
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentCancelTopic() {
        return TopicBuilder.name(kafkaTopics.cancelPaymentTopic())
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentResponseAuthorisationTopic() {
        return TopicBuilder.name(kafkaTopics.responseAuthorisationTopic())
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentResponseClearingTopic() {
        return TopicBuilder.name(kafkaTopics.responseClearingTopic())
                .partitions(5)
                .replicas(1)
                .build();
    }

    @Bean
    public NewTopic paymentResponseCancelTopic() {
        return TopicBuilder.name(kafkaTopics.responseCancelTopic())
                .partitions(5)
                .replicas(1)
                .build();
    }
}
