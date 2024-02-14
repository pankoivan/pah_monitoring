package org.pah_monitoring.main.scheduled;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@AllArgsConstructor
@Component
public class ExpiredCodeDeletion {

    private final RegistrationSecurityCodeService service;

    @Scheduled(fixedRateString = "${my.scheduled.rate.expired-code-deletion}")
    public void deleteExpired() {
        service.deleteExpired();
    }

}
