package com.freshguard.coldtrack.platform.alerting.application.internal.commandservices;

import com.freshguard.coldtrack.platform.alerting.domain.model.aggregates.Alert;
import com.freshguard.coldtrack.platform.alerting.infrastructure.persistence.jpa.repositories.AlertRepository;
import com.freshguard.coldtrack.platform.shared.domain.exceptions.ResourceNotFoundException;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

@Service
public class AlertCommandService {
    private final AlertRepository repository;

    public AlertCommandService(AlertRepository repository) {
        this.repository = repository;
    }

    @Transactional
    public Alert acknowledge(String code) {
        var alert = find(code);
        alert.acknowledge();
        return repository.save(alert);
    }

    @Transactional
    public Alert resolve(String code) {
        var alert = find(code);
        alert.resolve();
        return repository.save(alert);
    }

    private Alert find(String code) {
        return repository.findByAlertCode(code).orElseThrow(() -> new ResourceNotFoundException("Alert not found: " + code));
    }
}
