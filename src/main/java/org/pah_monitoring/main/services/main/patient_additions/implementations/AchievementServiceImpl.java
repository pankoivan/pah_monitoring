package org.pah_monitoring.main.services.main.patient_additions.implementations;

import jakarta.annotation.PostConstruct;
import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.email.interfaces.EmailSender;
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
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("achievementEmailSender")
    private EmailSender<Achievement> achievementEmailSender;

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
        Achievement achievement = achievements.get("Историк");
        if (patient.hasAnamnesis() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void inhaleExhale(Patient patient) {
        Achievement achievement = achievements.get("Вдох-выдох");
        if (!patient.getSpirometries().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void walk(Patient patient) {
        Achievement achievement = achievements.get("Прогулка");
        if (!patient.getWalkTests().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void unconscious(Patient patient) {
        Achievement achievement = achievements.get("Без сознания");
        if (!patient.getFaintings().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void transformation(Patient patient) {
        Achievement achievement = achievements.get("Трансформация");
        if (!patient.getPhysicalChanges().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void athlete(Patient patient) {
        Achievement achievement = achievements.get("Спортсмен");
        if (!patient.getOverallHealths().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void waterBalance(Patient patient) {
        Achievement achievement = achievements.get("Водный баланс");
        if (!patient.getLiquids().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void diet(Patient patient) {
        Achievement achievement = achievements.get("Диета");
        if (!patient.getWeights().isEmpty() && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void desperate(Patient patient) {
        Achievement achievement = achievements.get("Отчаянный");
        int catheterizationCount = (int) patient.getAnalysisFiles()
                .stream()
                .filter(analysisFile -> analysisFile.getAnalysisType() == AnalysisFile.AnalysisType.CATHETERIZATION)
                .count();
        if (catheterizationCount >= 1 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void beginner(Patient patient) {
        Achievement achievement = achievements.get("Начинающий");
        if (patient.getIndicatorsCount() >= 5 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void verified(Patient patient) {
        Achievement achievement = achievements.get("Проверенный");
        if (patient.getIndicatorsCount() >= 10 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void diligent(Patient patient) {
        Achievement achievement = achievements.get("Старательный");
        if (patient.getIndicatorsCount() >= 20 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void persistent(Patient patient) {
        Achievement achievement = achievements.get("Упорный");
        if (patient.getIndicatorsCount() >= 50 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void purposeful(Patient patient) {
        Achievement achievement = achievements.get("Целеустремлённый");
        if (patient.getIndicatorsCount() >= 100 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void unshakable(Patient patient) {
        Achievement achievement = achievements.get("Непоколебимый");
        if (patient.getIndicatorsCount() >= 250 && patient.hasNoAchievement(achievement)) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

    private void champion(Patient patient) {
        Achievement achievement = achievements.get("Чемпион");
        if (patient.getAchievementsCount() == achievements.size() - 1) {
            patientService.award(patient, achievement);
            achievementEmailSender.send(patient.getUserSecurityInformation().getEmail(), achievement);
        }
    }

}
