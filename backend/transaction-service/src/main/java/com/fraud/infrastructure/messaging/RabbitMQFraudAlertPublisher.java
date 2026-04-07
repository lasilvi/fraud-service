package com.fraud.infrastructure.messaging;

import com.fraud.application.port.out.FraudAlertPublisherPort;
import org.springframework.amqp.rabbit.core.RabbitTemplate;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.math.BigDecimal;
import java.util.List;

@Component
public class RabbitMQFraudAlertPublisher implements FraudAlertPublisherPort {

	private final RabbitTemplate rabbitTemplate;
	private final String exchangeName;
	private final String routingKey;

	public RabbitMQFraudAlertPublisher(
		RabbitTemplate rabbitTemplate,
		@Value("${fraud.alert.exchange}") String exchangeName,
		@Value("${fraud.alert.routing-key}") String routingKey
	) {
		this.rabbitTemplate = rabbitTemplate;
		this.exchangeName = exchangeName;
		this.routingKey = routingKey;
	}

	@Override
	public void publish(String userId, BigDecimal amount, String riskLevel, List<String> reasons) {
		FraudAlertMessage message = new FraudAlertMessage(userId, amount, riskLevel, reasons);
		rabbitTemplate.convertAndSend(exchangeName, routingKey, message);
	}

	public record FraudAlertMessage(String userId, BigDecimal amount, String riskLevel, List<String> reasons) {
	}
}
