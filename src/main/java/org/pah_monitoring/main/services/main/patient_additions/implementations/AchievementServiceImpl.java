package org.pah_monitoring.main.services.main.patient_additions.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.main.patient_additions.AchievementRepository;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;

    @Override
    public Achievement findAchievementByName(String name) throws DataSearchingServiceException {
        return repository.findByName(name).orElseThrow(
                () -> new DataSearchingServiceException("Награда с названием \"%s\" не существует".formatted(name))
        );
    }

}
