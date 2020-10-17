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
@EnableRabbit
public class RabbitMQConfiguration {

    @Value("${spring.rabbitmq.template.queue}")
    String queueName;

    @Value("${spring.rabbitmq.template.exchange}")
    String exchange;

    @Value("${spring.rabbitmq.template.routing-key}")
    String routingKey;

    @Autowired
    private ConnectionFactory connectionFactory;

    @Bean
    Queue queue() {
        return new Queue(queueName, false);
    }

    @Bean
    Queue financeQueue() {
        return new Queue("financeQueue", false);
    }

    @Bean
    Queue marketingQueue() {
        return new Queue("marketingQueue", false);
    }

//    @Bean(direct)
//    DirectExchange exchange() {
//        return new DirectExchange(exchange);
//    }

    @Bean
    FanoutExchange fanoutExchange() {
        return new FanoutExchange("fanout-exchange");
    }
    @Bean
    Binding binding(Queue queue, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(queue).to(exchange).with(routingKey);
        return BindingBuilder.bind(queue).to(fanoutExchange);
    }

    @Bean
    Binding marketingBinding(Queue marketingQueue, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(marketingQueue).to(exchange).with("marketing");
        return BindingBuilder.bind(marketingQueue).to(fanoutExchange);
    }

    @Bean
    Binding financeBinding(Queue financeQueue, FanoutExchange fanoutExchange) {
//        return BindingBuilder.bind(financeQueue).to(exchange).with("finance");
        return BindingBuilder.bind(financeQueue).to(fanoutExchange);
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
