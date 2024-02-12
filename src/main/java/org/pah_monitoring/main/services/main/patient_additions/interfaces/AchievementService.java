package org.pah_monitoring.main.services.main.patient_additions.interfaces;

import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;

import java.util.List;

public interface AchievementService {

    List<Achievement> findAll();

    List<Achievement> findAllByPatientId(Integer patientId) throws DataSearchingServiceException;

    void award();

}
