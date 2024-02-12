package org.pah_monitoring.main.services.main.patient_additions.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.enums.AchievementEnum;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.main.patient_additions.AchievementRepository;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class AchievementServiceImpl implements AchievementService {

    private final AchievementRepository repository;

    private final PatientService patientService;

    @Override
    public List<Achievement> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Achievement> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return patientService.findById(patientId).getAchievements();
    }

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
