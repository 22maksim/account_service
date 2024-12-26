package com.example.account_service.model;

import lombok.AllArgsConstructor;
import org.springframework.data.redis.connection.Message;

@AllArgsConstructor
public class MessageFromPublisher implements Message {
    private final byte[] channel;
    private final byte[] body;

    @Override
    public byte[] getBody() {
        return this.body;
    }

    @Override
    public byte[] getChannel() {
        return this.channel;
    }
}
