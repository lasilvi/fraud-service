package com.fraud.infrastructure.config;

import org.junit.jupiter.api.Test;
import org.springframework.amqp.core.TopicExchange;
import org.springframework.amqp.support.converter.MessageConverter;

import java.lang.reflect.Field;

import static org.junit.jupiter.api.Assertions.*;

class RabbitMQConfigTest {

	private final RabbitMQConfig config = new RabbitMQConfig();

	@Test
	void shouldCreateTopicExchangeWithCorrectName() throws Exception {
		Field field = RabbitMQConfig.class.getDeclaredField("exchangeName");
		field.setAccessible(true);
		field.set(config, "fraud.exchange");

		TopicExchange exchange = config.fraudExchange();
		assertNotNull(exchange);
		assertEquals("fraud.exchange", exchange.getName());
	}

	@Test
	void shouldCreateJackson2JsonMessageConverter() {
		MessageConverter converter = config.jackson2JsonMessageConverter();
		assertNotNull(converter);
	}
}
