package com.fraud.alert.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.Binding;
import org.springframework.amqp.core.Queue;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.Jackson2JsonMessageConverter;
import org.springframework.test.util.ReflectionTestUtils;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

    private final RabbitMQConfig config = new RabbitMQConfig();

    @Test
    void shouldCreateExchangeBeanWithCorrectName() {
        // Given
        ReflectionTestUtils.setField(config, "exchangeName", "fraud.exchange");

        // When
        TopicExchange exchange = config.fraudExchange();

        // Then
        assertNotNull(exchange);
        assertEquals("fraud.exchange", exchange.getName());
    }

    @Test
    void shouldCreateQueueBeanAndIsDurable() {
        // Given
        ReflectionTestUtils.setField(config, "queueName", "fraud.alert.queue");

        // When
        Queue queue = config.fraudAlertQueue();

        // Then
        assertNotNull(queue);
        assertEquals("fraud.alert.queue", queue.getName());
        assertTrue(queue.isDurable());
    }

    @Test
    void shouldBindQueueToExchangeWithCorrectRoutingKey() {
        // Given
        ReflectionTestUtils.setField(config, "exchangeName", "fraud.exchange");
        ReflectionTestUtils.setField(config, "queueName", "fraud.alert.queue");
        ReflectionTestUtils.setField(config, "routingKey", "fraud.alert");

        Queue queue = config.fraudAlertQueue();
        TopicExchange exchange = config.fraudExchange();

        // When
        Binding binding = config.binding(queue, exchange);

        // Then
        assertNotNull(binding);
        assertEquals("fraud.alert.queue", binding.getDestination());
        assertEquals("fraud.exchange", binding.getExchange());
        assertEquals("fraud.alert", binding.getRoutingKey());
    }

    @Test
    void shouldCreateJackson2JsonMessageConverterBean() {
        // When
        Jackson2JsonMessageConverter converter = config.messageConverter();

        // Then
        assertNotNull(converter);
    }
}
