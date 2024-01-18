package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface EmployeeInformationService extends SavingValidationService<EmployeeInformationAddingDto> {

    EmployeeInformation findById(Integer id) throws DataSearchingServiceException;

    EmployeeInformation add(EmployeeInformationAddingDto addingDto, Hospital hospital) throws DataSavingServiceException;

}
