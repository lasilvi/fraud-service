package com.fraud.alert.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.amqp.support.converter.MessageConverter;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    public static final String EXCHANGE_NAME = "fraud.exchange";
    public static final String QUEUE_NAME = "fraud.alert.queue";
    public static final String ROUTING_KEY = "fraud.alert";

    @Bean
    public TopicExchange fraudExchange() {
        return new TopicExchange(EXCHANGE_NAME);
    }

    @Bean
    public Queue fraudAlertQueue() {
        return new Queue(QUEUE_NAME, true);
    }

    @Bean
    public Binding binding(Queue fraudAlertQueue, TopicExchange fraudExchange) {
        return BindingBuilder.bind(fraudAlertQueue)
                .to(fraudExchange)
                .with(ROUTING_KEY);
    }

    @Bean
    public MessageConverter jackson2JsonMessageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
