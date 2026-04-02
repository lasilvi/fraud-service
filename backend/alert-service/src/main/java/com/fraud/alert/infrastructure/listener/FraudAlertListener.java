package com.fraud.alert.infrastructure.listener;

import com.fraud.alert.application.port.out.SaveFraudAlertPort;
import com.fraud.alert.domain.model.FraudAlert;
import com.fraud.alert.domain.model.FraudAlertEvent;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.amqp.rabbit.annotation.RabbitListener;
import org.springframework.stereotype.Component;

import java.time.LocalDateTime;

@Component
public class FraudAlertListener {

    private static final Logger logger = LoggerFactory.getLogger(FraudAlertListener.class);

    private final SaveFraudAlertPort saveFraudAlertPort;

    public FraudAlertListener(SaveFraudAlertPort saveFraudAlertPort) {
        this.saveFraudAlertPort = saveFraudAlertPort;
    }

    @RabbitListener(queues = "fraud.alert.queue")
    public void handleFraudAlert(FraudAlertEvent event) {
        logger.info("Fraud alert received — userId={}, riskLevel={}, amount={}",
                event.userId(), event.riskLevel(), event.amount());

        FraudAlert alert = new FraudAlert(null, event.userId(), event.amount(),
                event.riskLevel(), event.reasons(), LocalDateTime.now());
        saveFraudAlertPort.save(alert);

        if ("HIGH".equals(event.riskLevel())) {
            logger.warn("HIGH RISK ALERT — userId={}, amount={}, reasons={}",
                    event.userId(), event.amount(), event.reasons());
        }
    }
}
