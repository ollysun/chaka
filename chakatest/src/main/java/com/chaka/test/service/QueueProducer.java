package com.chaka.test.service;

import com.chaka.test.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueProducer {

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Value("${spring.rabbitmq.template.exchange}")
    String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    private String routingKey;

    public void send(Transaction transaction) {
        rabbitTemplate.convertAndSend(exchange, routingKey, transaction);
        log.info("Send msg = " + transaction);

    }
}
