package org.pah_monitoring.main.services.other.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.other.AchievementRepository;
import org.pah_monitoring.main.services.other.interfaces.AchievementService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;

}
