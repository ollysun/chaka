package com.moses.rabbitmq.service;

import com.moses.rabbitmq.model.Transaction;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Service
public class ConsumerService {

    private static final Logger LOGGER = LoggerFactory.getLogger(ConsumerService.class);

    @RabbitListener(queues = "${queue.A}")
    public void handleQueueAMessageReception(Transaction transaction) {
        LOGGER.info("Message received in Queue A : " + transaction);
    }

    @RabbitListener(queues = "${queue.B}")
    public void handleQueueBMessageReception(Transaction transaction) {
        LOGGER.info("Message received in Queue B : " + transaction);
    }

    @RabbitListener(queues = "${queue.C}")
    public void handleQueueCMessageReception(Transaction transaction) {
        LOGGER.info("Message received in Queue C : " + transaction);
    }

    @RabbitListener(queues = "${queue.D}")
    public void handleQueueDMessageReception(Transaction transaction) {
        LOGGER.info("Message received in Queue D : " + transaction);
    }

    @RabbitListener(queues = "${queue.E}")
    public void handleQueueEMessageReception(Transaction transaction) {
        LOGGER.info("Message received in Queue E : " + transaction);
    }

    @RabbitListener(queues = "${queue.F}")
    public void handleQueueFMessageReception(Transaction transaction) {
        LOGGER.info("Message received in Queue F : " + transaction);
    }
}