package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.PatientSavingDto;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface PatientService extends SavingValidationService<PatientSavingDto> {

    Patient findById(Integer id) throws DataSearchingServiceException;

    Patient save(PatientSavingDto savingDto) throws DataValidationServiceException, DataSavingServiceException;

}
