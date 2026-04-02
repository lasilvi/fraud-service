package com.fraud.alert.infrastructure.listener;

import com.fraud.alert.domain.model.FraudEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

@Component
public class FraudAlertListener {

    private static final Logger logger = LoggerFactory.getLogger(FraudAlertListener.class);

    @RabbitListener(queues = "${fraud.alert.queue}")
    public void handleFraudAlert(FraudEvent event) {
        logger.info("====== FRAUD ALERT RECEIVED ======");
        logger.info("User ID: {}", event.userId());
        logger.info("Amount: {}", event.amount());
        logger.info("Risk Level: {}", event.riskLevel());
        logger.info("Reasons: {}", event.reasons());
        logger.info("===================================");
    }
}
