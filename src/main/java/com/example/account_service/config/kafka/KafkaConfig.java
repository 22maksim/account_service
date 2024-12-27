package com.example.account_service.config.kafka;

import com.example.account_service.config.kafka.properties.KafkaConsumerProperties;
import com.example.account_service.config.kafka.properties.KafkaProducerProperties;
import lombok.RequiredArgsConstructor;
import org.apache.kafka.clients.consumer.ConsumerConfig;
import org.apache.kafka.clients.producer.ProducerConfig;
import org.springframework.boot.autoconfigure.kafka.KafkaProperties;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.kafka.annotation.EnableKafka;
import org.springframework.kafka.config.ConcurrentKafkaListenerContainerFactory;
import org.springframework.kafka.core.*;

import java.util.Map;

@EnableKafka
@Configuration
@RequiredArgsConstructor
public class KafkaConfig {
    private final KafkaProperties kafkaProperties;
    private final KafkaConsumerProperties consumerProperties;
    private final KafkaProducerProperties producerProperties;

    @Bean
    public ProducerFactory<String, String> producerFactory() {
        return new DefaultKafkaProducerFactory<>(
                Map.of(
                        ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, producerProperties.bootstrapServers(),
                        ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG, producerProperties.keySerializer(),
                        ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG, producerProperties.valueSerializer()
                )
        );
    }

    @Bean
    public KafkaTemplate<String, String> kafkaTemplate() {
        return new KafkaTemplate<>(producerFactory());
    }

    @Bean
    public ConsumerFactory<String, String> consumerFactory() {
        return new DefaultKafkaConsumerFactory<>(
                Map.of(
                        ConsumerConfig.BOOTSTRAP_SERVERS_CONFIG, consumerProperties.bootstrapServers(),
                        ConsumerConfig.GROUP_ID_CONFIG, consumerProperties.groupId(),
                        ConsumerConfig.KEY_DESERIALIZER_CLASS_CONFIG, consumerProperties.keyDeserializer(),
                        ConsumerConfig.VALUE_DESERIALIZER_CLASS_CONFIG, consumerProperties.valueDeserializer(),
                        ConsumerConfig.AUTO_OFFSET_RESET_CONFIG, consumerProperties.autoOffsetReset()
                )
        );
    }

    @Bean
    public ConcurrentKafkaListenerContainerFactory<String, String> kafkaListenerContainerFactory() {
        ConcurrentKafkaListenerContainerFactory<String, String> factory = new ConcurrentKafkaListenerContainerFactory<>();
        factory.setConsumerFactory(consumerFactory());
        return factory;
    }
}
