package com.fraud.alert.infrastructure.config;

import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.BindingBuilder;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

@Configuration
public class RabbitMQConfig {

    @Value("${fraud.alert.exchange}")
    private String exchangeName;

    @Value("${fraud.alert.queue}")
    private String queueName;

    @Value("${fraud.alert.routing-key}")
    private String routingKey;

    @Bean
    public TopicExchange fraudExchange() {
        return new TopicExchange(exchangeName);
    }

    @Bean
    public Queue fraudAlertQueue() {
        return new Queue(queueName, true);
    }

    @Bean
    public Binding binding(Queue fraudAlertQueue, TopicExchange fraudExchange) {
        return BindingBuilder
                .bind(fraudAlertQueue)
                .to(fraudExchange)
                .with(routingKey);
    }

    @Bean
    public Jackson2JsonMessageConverter messageConverter() {
        return new Jackson2JsonMessageConverter();
    }
}
