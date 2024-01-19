package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.users.info.interfaces.common.UserInfoService;
import org.springframework.validation.BindingResult;

public interface EmployeeInformationService extends
        UserInfoService<EmployeeInformation, EmployeeInformationAddingDto, EmployeeInformationEditingDto, EmployeeInformationSavingDto> {

    void checkDataValidityForAdding(EmployeeInformationAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException;

}
