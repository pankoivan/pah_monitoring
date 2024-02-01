package org.pah_monitoring.main.services.main.patient_additions.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.main.patient_additions.AchievementRepository;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;

}
