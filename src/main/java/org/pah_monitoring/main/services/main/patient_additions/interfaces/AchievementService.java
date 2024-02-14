package org.pah_monitoring.main.services.main.patient_additions.interfaces;

import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

import java.util.List;

public interface AchievementService extends UrlValidationService {

    List<Achievement> findAll();

    List<Achievement> findAllByPatientId(Integer patientId) throws DataSearchingServiceException;

    void checkAccessRightsForObtainingPatientAchievements(Patient patient) throws NotEnoughRightsServiceException;

    void award();

}
