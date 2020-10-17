package com.chaka.test.service;

import com.chaka.test.model.Transaction;
import lombok.extern.slf4j.Slf4j;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Service;

@Slf4j
@Service
public class QueueRabbitListener {

//    @RabbitListener(queues = "testQueue")
//    public void receivedTestQueueMessage(Transaction employee) {
//        log.info("Received Message From RabbitMQListener: " + employee);
//    }
//
//    @RabbitListener(queues = "financeQueue")
//    public void receivedFinanceQueueMessage(Transaction employee) {
//        log.info("Received Message From RabbitMQListener: " + employee);
//    }
//
//    @RabbitListener(queues = "marketingQueue")
//    public void receivedMarketingQueueMessage(Transaction employee) {
//        log.info("Received Message From RabbitMQListener: " + employee);
//    }
}
