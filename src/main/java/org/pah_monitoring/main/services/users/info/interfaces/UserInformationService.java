package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface UserInformationService extends SavingValidationService<UserInformationAddingDto> {

    UserInformation findById(Integer id) throws DataSearchingServiceException;

    UserInformation add(UserInformationAddingDto addingDto) throws DataSavingServiceException;

}
