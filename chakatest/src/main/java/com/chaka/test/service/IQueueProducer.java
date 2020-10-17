package com.chaka.test.service;

import com.chaka.test.model.Transaction;

public interface IQueueProducer {
    void sendToDirectExchange(Transaction transaction, String routingKey);

    void sendToTopicExchange(Transaction transaction, String topic);

    void sendToFanoutExchange(Transaction transaction);
}
