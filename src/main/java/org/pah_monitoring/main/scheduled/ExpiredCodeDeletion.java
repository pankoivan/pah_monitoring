package org.pah_monitoring.main.scheduled;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class ExpiredCodeDeletion {

    private final RegistrationSecurityCodeService service;

    @Scheduled(fixedRate = 5000)
    public void deleteExpired() {
        service.deleteExpired();
    }

}
