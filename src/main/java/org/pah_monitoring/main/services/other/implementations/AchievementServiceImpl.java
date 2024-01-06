package org.pah_monitoring.main.services.other.implementations;

import org.pah_monitoring.main.repositorites.other.AchievementRepository;
import org.pah_monitoring.main.services.other.interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;

    @Autowired
    public AchievementServiceImpl(AchievementRepository repository) {
        this.repository = repository;
    }

}
