package org.pah_monitoring.main.services.main.patient_additions.implementations;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.main.examinations.indicators.AnalysisFile;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.repositorites.patient_additions.AchievementRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.patient_additions.interfaces.AchievementService;
import org.pah_monitoring.main.services.main.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;
import java.util.function.Function;
import java.util.stream.Collectors;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
@Transactional
public class AchievementServiceImpl implements AchievementService {

    @Setter(onMethod = @__(@Autowired(required = false)))
    private Map<String, Achievement> achievements;

    private final AchievementRepository repository;

    private PatientService patientService;

    private CurrentUserCheckService checkService;

    @PostConstruct
    private void achievementsInit() {
        achievements = repository.findAll()
                .stream()
                .collect(Collectors.toMap(Achievement::getName, Function.identity()));
    }

    @Override
    public List<Achievement> findAll() {
        return repository.findAll();
    }

    @Override
    public List<Achievement> findAllByPatientId(Integer patientId) throws DataSearchingServiceException {
        return patientService.findById(patientId).getAchievements();
    }

    @Override
    public void checkAccessRightsForObtainingPatientAchievements(Patient patient) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isMainAdministrator() ||
                checkService.isHospitalUserFromSameHospital(patient.getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

    @Override
    public void award() {
        patientService.findAll().forEach(patient -> {
            historian(patient);
            inhaleExhale(patient);
            walk(patient);
            unconscious(patient);
            transformation(patient);
            athlete(patient);
            waterBalance(patient);
            diet(patient);
            desperate(patient);
            beginner(patient);
            verified(patient);
            diligent(patient);
            persistent(patient);
            purposeful(patient);
            unshakable(patient);
            champion(patient);
        });
    }

    private void historian(Patient patient) {
        if (patient.hasAnamnesis() && patient.hasNoAchievement(achievements.get("Историк"))) {
            patientService.award(patient, achievements.get("Историк"));
        }
    }

    private void inhaleExhale(Patient patient) {
        if (!patient.getSpirometries().isEmpty() && patient.hasNoAchievement(achievements.get("Вдох-выдох"))) {
            patientService.award(patient, achievements.get("Вдох-выдох"));
        }
    }

    private void walk(Patient patient) {
        if (!patient.getWalkTests().isEmpty() && patient.hasNoAchievement(achievements.get("Прогулка"))) {
            patientService.award(patient, achievements.get("Прогулка"));
        }
    }

    private void unconscious(Patient patient) {
        if (!patient.getFaintings().isEmpty() && patient.hasNoAchievement(achievements.get("Без сознания"))) {
            patientService.award(patient, achievements.get("Без сознания"));
        }
    }

    private void transformation(Patient patient) {
        if (!patient.getPhysicalChanges().isEmpty() && patient.hasNoAchievement(achievements.get("Трансформация"))) {
            patientService.award(patient, achievements.get("Трансформация"));
        }
    }

    private void athlete(Patient patient) {
        if (!patient.getOverallHealths().isEmpty() && patient.hasNoAchievement(achievements.get("Спортсмен"))) {
            patientService.award(patient, achievements.get("Спортсмен"));
        }
    }

    private void waterBalance(Patient patient) {
        if (!patient.getLiquids().isEmpty() && patient.hasNoAchievement(achievements.get("Водный баланс"))) {
            patientService.award(patient, achievements.get("Водный баланс"));
        }
    }

    private void diet(Patient patient) {
        if (!patient.getWeights().isEmpty() && patient.hasNoAchievement(achievements.get("Диета"))) {
            patientService.award(patient, achievements.get("Диета"));
        }
    }

    private void desperate(Patient patient) {
        int catheterizationCount = (int) patient.getAnalysisFiles()
                .stream()
                .filter(analysisFile -> analysisFile.getAnalysisType() == AnalysisFile.AnalysisType.CATHETERIZATION)
                .count();
        if (catheterizationCount >= 1 && patient.hasNoAchievement(achievements.get("Отчаянный"))) {
            patientService.award(patient, achievements.get("Отчаянный"));
        }
    }

    private void beginner(Patient patient) {
        if (patient.getIndicatorsCount() >= 5 && patient.hasNoAchievement(achievements.get("Начинающий"))) {
            patientService.award(patient, achievements.get("Начинающий"));
        }
    }

    private void verified(Patient patient) {
        if (patient.getIndicatorsCount() >= 10 && patient.hasNoAchievement(achievements.get("Проверенный"))) {
            patientService.award(patient, achievements.get("Проверенный"));
        }
    }

    private void diligent(Patient patient) {
        if (patient.getIndicatorsCount() >= 20 && patient.hasNoAchievement(achievements.get("Старательный"))) {
            patientService.award(patient, achievements.get("Старательный"));
        }
    }

    private void persistent(Patient patient) {
        if (patient.getIndicatorsCount() >= 50 && patient.hasNoAchievement(achievements.get("Упорный"))) {
            patientService.award(patient, achievements.get("Упорный"));
        }
    }

    private void purposeful(Patient patient) {
        if (patient.getIndicatorsCount() >= 100 && patient.hasNoAchievement(achievements.get("Целеустремлённый"))) {
            patientService.award(patient, achievements.get("Целеустремлённый"));
        }
    }

    private void unshakable(Patient patient) {
        if (patient.getIndicatorsCount() >= 250 && patient.hasNoAchievement(achievements.get("Непоколебимый"))) {
            patientService.award(patient, achievements.get("Непоколебимый"));
        }
    }

    private void champion(Patient patient) {
        if (patient.getAchievementsCount() == achievements.size() - 1) {
            patientService.award(patient, achievements.get("Чемпион"));
        }
    }

}
