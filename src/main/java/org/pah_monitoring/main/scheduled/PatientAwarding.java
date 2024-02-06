package org.pah_monitoring.main.scheduled;

import lombok.RequiredArgsConstructor;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.springframework.scheduling.annotation.Scheduled;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Component
public class PatientAwarding {

    private final AchievementService service;

    @Scheduled(fixedRateString = "${my.scheduled.rate.patient-awarding}")
    public void awardPatient() {
        service.awardPatient();
    }

}
