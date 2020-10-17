package com.chaka.test.configuration;

import com.chaka.test.service.QueueConsumer;
import com.chaka.test.service.QueueRabbitListener;
import org.springframework.amqp.core.*;
import org.springframework.amqp.rabbit.annotation.EnableRabbit;
import org.springframework.amqp.rabbit.config.SimpleRabbitListenerContainerFactory;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.amqp.rabbit.listener.MessageListenerContainer;
import org.springframework.amqp.rabbit.listener.SimpleMessageListenerContainer;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.amqp.rabbit.connection.ConnectionFactory;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQProducerConfiguration {

    @Value("${queue.A}")
    private String queueA;

    @Value("${queue.B}")
    private String queueB;

    @Value("${queue.C}")
    private String queueC;

    @Value("${queue.D}")
    private String queueD;

    @Value("${queue.E}")
    private String queueE;

    @Value("${queue.F}")
    private String queueF;


    @Value("${exchange.direct}")
    private String directExchange;

    @Value("${exchange.topic}")
    private String topicExchange;

    @Value("${exchange.fanout}")
    private String fanoutExchange;

    @Value("${routing.direct.1}")
    private String direct1RoutingKey;

    @Value("${routing.direct.2}")
    private String direct2RoutingKey;

    @Value("${routing.topic.rabbitmq.#}")
    private String topicRabbitMQRoutingKey;

    @Value("${routing.topic.rabbitmq.spring.#}")
    private String topicRabbitMQSpringRoutingKey;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    public Queue queueA() {
        return new Queue(queueA);
    }

    @Bean
    public Queue queueB() {
        return new Queue(queueB);
    }

    @Bean
    public Queue queueC() {
        return new Queue(queueC);
    }

    @Bean
    public Queue queueD() {
        return new Queue(queueD);
    }

    @Bean
    public Queue queueE() {
        return new Queue(queueE);
    }

    @Bean
    public Queue queueF() {
        return new Queue(queueF);
    }

    @Bean
    public DirectExchange directExchange() {
        return new DirectExchange(directExchange);
    }

    @Bean
    public TopicExchange topicExchange() {
        return new TopicExchange(topicExchange);
    }

    @Bean
    public FanoutExchange fanoutExchange() {
        return new FanoutExchange(fanoutExchange);
    }

    @Bean
    public Binding bindingDirectExchangeQueueADirect1(DirectExchange directExchange, Queue queueA) {
        return BindingBuilder.bind(queueA).to(directExchange).with(direct1RoutingKey);
    }

    @Bean
    public Binding bindingDirectExchangeQueueBDirect2(DirectExchange directExchange, Queue queueB) {
        return BindingBuilder.bind(queueB).to(directExchange).with(direct2RoutingKey);
    }

    @Bean
    public Binding bindingTopicExchangeQueueCTopicRabbitMQ(TopicExchange topicExchange, Queue queueC) {
        return BindingBuilder.bind(queueC).to(topicExchange).with(topicRabbitMQRoutingKey);
    }

    @Bean
    public Binding bindingTopicExchangeQueueDTopicRabbitMQSpring(TopicExchange topicExchange, Queue queueD) {
        return BindingBuilder.bind(queueD).to(topicExchange).with(topicRabbitMQSpringRoutingKey);
    }

    @Bean
    public Binding bindingFanoutExchangeQueueEFanout(FanoutExchange fanoutExchange, Queue queueE) {
        return BindingBuilder.bind(queueE).to(fanoutExchange);
    }

    @Bean
    public Binding bindingFanoutExchangeQueueFFanout(FanoutExchange fanoutExchange, Queue queueF) {
        return BindingBuilder.bind(queueF).to(fanoutExchange);
    }

    @Bean
    public MessageConverter jsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }

    @Bean
    public RabbitTemplate rabbitTemplate(ConnectionFactory connectionFactory) {
        final RabbitTemplate rabbitTemplate = new RabbitTemplate(connectionFactory);
        rabbitTemplate.setMessageConverter(jsonMessageConverter());
        return rabbitTemplate;
    }

    //for direct exchange messageListener
//    @Bean
//    MessageListenerContainer container(ConnectionFactory connectionFactory) {
//        SimpleMessageListenerContainer container = new SimpleMessageListenerContainer();
//        container.setConnectionFactory(connectionFactory);
//        container.setQueues(queue());
//        container.setMessageListener(new QueueConsumer());
//        return container;
//    }


}
