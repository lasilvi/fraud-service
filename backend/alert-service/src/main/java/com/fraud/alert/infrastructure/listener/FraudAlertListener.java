package com.fraud.alert.infrastructure.listener;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import com.fraud.alert.domain.model.FraudAlertEvent;

@Component
public class FraudAlertListener {

    private static final Logger logger = LoggerFactory.getLogger(FraudAlertListener.class);

    @RabbitListener(queues = "fraud.alert.queue")
    public void handleFraudAlert(FraudAlertEvent event) {
        if ("HIGH".equals(event.riskLevel())) {
            logger.warn("HIGH RISK ALERT — userId={}, amount={}, reasons={}", 
                    event.userId(), event.amount(), event.reasons());
        } else {
            logger.info("Fraud alert received — userId={}, riskLevel={}, amount={}", 
                    event.userId(), event.riskLevel(), event.amount());
        }
    }
}
