package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface EmployeeInformationService extends SavingValidationService<EmployeeInformationSavingDto> {

    EmployeeInformation save(EmployeeInformationSavingDto savingDto, Hospital hospital) throws DataSavingServiceException;

}
