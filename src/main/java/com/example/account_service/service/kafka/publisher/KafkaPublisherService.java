package com.example.account_service.service.kafka.publisher;

import com.example.account_service.dto.MyMessageDto;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.SendResult;
import org.springframework.stereotype.Service;

import java.io.Serializable;
import java.util.concurrent.CompletableFuture;

@Slf4j
@Service
@RequiredArgsConstructor
public class KafkaPublisherService<T extends Serializable> {
    private final KafkaTemplate<String, MyMessageDto<T>> kafkaTemplate;

    public void sendResponseMessage(MyMessageDto<T> message, String topic) {
        CompletableFuture<SendResult<String, MyMessageDto<T>>> future =
                kafkaTemplate.send(topic,  message);
        future.exceptionally(ex -> {
            log.error("Failed to send message in topic: {}. Error: {}", topic, ex.getMessage());
            return null;
        });
    }

}
