package com.fraud.infrastructure.messaging;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.ArgumentCaptor;
import org.mockito.Mock;
import org.mockito.junit.jupiter.MockitoExtension;
import org.springframework.amqp.rabbit.core.RabbitTemplate;

import java.math.BigDecimal;
import java.util.List;

import static org.junit.jupiter.api.Assertions.*;
import static org.mockito.ArgumentMatchers.eq;
import static org.mockito.Mockito.*;

@ExtendWith(MockitoExtension.class)
class RabbitMQFraudAlertPublisherTest {

	@Mock
	private RabbitTemplate rabbitTemplate;

	private RabbitMQFraudAlertPublisher publisher;

	@BeforeEach
	void setUp() {
		publisher = new RabbitMQFraudAlertPublisher(rabbitTemplate, "fraud.exchange", "fraud.alert");
	}

	@Test
	void shouldPublishFraudAlertWithCorrectContent() {
		publisher.publish("user123", new BigDecimal("20000"), "HIGH", List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"));

		ArgumentCaptor<RabbitMQFraudAlertPublisher.FraudAlertMessage> captor =
			ArgumentCaptor.forClass(RabbitMQFraudAlertPublisher.FraudAlertMessage.class);
		verify(rabbitTemplate).convertAndSend(eq("fraud.exchange"), eq("fraud.alert"), captor.capture());

		RabbitMQFraudAlertPublisher.FraudAlertMessage message = captor.getValue();
		assertEquals("user123", message.userId());
		assertEquals(new BigDecimal("20000"), message.amount());
		assertEquals("HIGH", message.riskLevel());
		assertEquals(List.of("HIGH_AMOUNT", "UNUSUAL_LOCATION"), message.reasons());
	}

	@Test
	void shouldPublishWithSingleReason() {
		publisher.publish("user456", new BigDecimal("16000"), "MEDIUM", List.of("HIGH_AMOUNT"));

		ArgumentCaptor<RabbitMQFraudAlertPublisher.FraudAlertMessage> captor =
			ArgumentCaptor.forClass(RabbitMQFraudAlertPublisher.FraudAlertMessage.class);
		verify(rabbitTemplate).convertAndSend(eq("fraud.exchange"), eq("fraud.alert"), captor.capture());

		RabbitMQFraudAlertPublisher.FraudAlertMessage message = captor.getValue();
		assertEquals("user456", message.userId());
		assertEquals("MEDIUM", message.riskLevel());
		assertEquals(1, message.reasons().size());
	}

	@Test
	void shouldCallRabbitTemplateExactlyOnce() {
		publisher.publish("user789", new BigDecimal("25000.50"), "HIGH", List.of("HIGH_AMOUNT"));

		verify(rabbitTemplate, times(1)).convertAndSend(anyString(), anyString(), any(Object.class));
		verifyNoMoreInteractions(rabbitTemplate);
	}
}
