package com.fraud.alert.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void shouldCreateExchangeBeanWithCorrectName() {
        TopicExchange exchange = config.fraudExchange();
        assertNotNull(exchange);
        assertEquals("fraud.exchange", exchange.getName());
    }

    @Test
    void shouldCreateQueueBeanAndIsDurable() {
        Queue queue = config.fraudAlertQueue();
        assertNotNull(queue);
        assertEquals("fraud.alert.queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void shouldBindQueueToExchangeWithCorrectRoutingKey() {
        Queue queue = config.fraudAlertQueue();
        TopicExchange exchange = config.fraudExchange();
        Binding binding = config.binding(queue, exchange);
        assertNotNull(binding);
        assertEquals("fraud.alert.queue", binding.getDestination());
        assertEquals("fraud.exchange", binding.getExchange());
        assertEquals("fraud.alert", binding.getRoutingKey());
    }

    @Test
    void shouldCreateJackson2JsonMessageConverterBean() {
        MessageConverter converter = config.jackson2JsonMessageConverter();
        assertNotNull(converter);
    }
}
