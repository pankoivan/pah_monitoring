package org.pah_monitoring.main.services.main.users.inactivity.interfaces;

import org.pah_monitoring.main.dto.in.users.inactivity.PatientInactivityAddingDto;
import org.pah_monitoring.main.entities.main.users.inactivity.PatientInactivity;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;

public interface PatientInactivityService extends DataAddingValidationService<PatientInactivityAddingDto> {

    PatientInactivity add(PatientInactivityAddingDto addingDto) throws DataSavingServiceException;

    void checkAccessRightsForAdding(Patient patient) throws NotEnoughRightsServiceException;

}
