package org.pah_monitoring.main.services.main.patient_additions.implementations;

import lombok.AllArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.enums.AchievementEnum;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.main.patient_additions.AchievementRepository;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

@AllArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;

    @Override
    public Achievement achievement(AchievementEnum achievement) throws DataSearchingServiceException {
        return repository.findByName(achievement.getAlias()).orElseThrow(
                () -> new DataSearchingServiceException("Награда с названием \"%s\" не существует".formatted(achievement.getAlias()))
        );
    }

    @Override
    public void awardPatient() {
        // todo: many-many rules for patient awarding here
    }

}
