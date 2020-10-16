package com.chaka.test.service;

import com.chaka.test.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueRabbitListener {

    @RabbitListener(queues = "${spring.rabbitmq.template.queue}")
    public void receivedMessage(Transaction employee) {
        log.info("Received Message From RabbitMQListener: " + employee);
    }
}
