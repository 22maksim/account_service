package com.example.account_service.config.redis.listener;

import org.springframework.data.redis.connection.Message;
import org.springframework.data.redis.connection.MessageListener;

import java.util.Arrays;

public class MessageListenerInfo implements MessageListener {
    @Override
    public void onMessage(Message message, byte[] pattern) {
        System.out.println("Get new message from Redis: " + Arrays.toString(message.getBody()));
    }
}
