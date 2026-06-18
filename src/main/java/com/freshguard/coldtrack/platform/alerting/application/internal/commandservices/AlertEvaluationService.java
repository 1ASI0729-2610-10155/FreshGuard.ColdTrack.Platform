package com.freshguard.coldtrack.platform.alerting.application.internal.commandservices;

import com.freshguard.coldtrack.platform.alerting.domain.model.aggregates.Alert;
import com.freshguard.coldtrack.platform.alerting.domain.model.valueobjects.*;
import com.freshguard.coldtrack.platform.alerting.infrastructure.persistence.jpa.repositories.AlertRepository;
import org.springframework.messaging.simp.SimpMessagingTemplate;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

/** Evaluates telemetry against default cold-chain thresholds. */
@Service
public class AlertEvaluationService {
    private static final double MAX_TEMPERATURE = 25.0;
    private static final double MAX_HUMIDITY = 60.0;
    private final AlertRepository repository;
    private final SimpMessagingTemplate messagingTemplate;

    public AlertEvaluationService(AlertRepository repository, SimpMessagingTemplate messagingTemplate) {
        this.repository = repository;
        this.messagingTemplate = messagingTemplate;
    }

    @Transactional
    public void evaluate(String shipmentCode, String sensorCode, double temperature, double humidity) {
        if (temperature > MAX_TEMPERATURE) {
            publish(repository.save(new Alert(AlertSeverity.CRITICAL, AlertType.TEMPERATURE, shipmentCode, sensorCode,
                    "Temperature exceeds the allowed threshold", temperature, MAX_TEMPERATURE)));
        }
        if (humidity > MAX_HUMIDITY) {
            publish(repository.save(new Alert(AlertSeverity.WARNING, AlertType.HUMIDITY, shipmentCode, sensorCode,
                    "Humidity exceeds the recommended threshold", humidity, MAX_HUMIDITY)));
        }
    }

    private void publish(Alert alert) {
        messagingTemplate.convertAndSend("/topic/alerts", alert);
    }
}
