package org.pah_monitoring.main.services.users.users.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.DoctorSavingDto;
import org.pah_monitoring.main.entities.users.Doctor;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface DoctorService extends SavingValidationService<DoctorSavingDto> {

    Doctor findById(Integer id) throws DataSearchingServiceException;

    Doctor save(DoctorSavingDto savingDto) throws DataSavingServiceException;

}
