package org.pah_monitoring.main.services.main.patient_additions.interfaces;

import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;

public interface AchievementService {

    Achievement findAchievementByName(String name) throws DataSearchingServiceException;

}
