package com.chaka.test.service;

import com.chaka.test.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueProducerImpl implements IQueueProducer{

    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${exchange.topic}")
    private String topicExchange;

    @Value("${exchange.fanout}")
    private String fanoutExchange;

    @Autowired
    RabbitTemplate rabbitTemplate;

    @Override
    public void sendToDirectExchange(Transaction transaction, String routingKey) {
        rabbitTemplate.convertAndSend(directExchange, routingKey, transaction);
    }

    @Override
    public void sendToTopicExchange(Transaction transaction, String topic) {
        rabbitTemplate.convertAndSend(topicExchange, topic, transaction);
    }

    @Override
    public void sendToFanoutExchange(Transaction transaction) {
        rabbitTemplate.convertAndSend(fanoutExchange, "", transaction); // routingKey is ignored
    }
}
