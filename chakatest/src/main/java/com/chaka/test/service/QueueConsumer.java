package com.chaka.test.service;

import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.core.Message;
import org.springframework.amqp.core.MessageListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueConsumer implements MessageListener  {

    @Override
    public void onMessage(Message message) {
        log.info("Consuming Message - " + new String(message.getBody()));
    }
}
