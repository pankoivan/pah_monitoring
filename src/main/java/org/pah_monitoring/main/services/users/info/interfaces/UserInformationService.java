package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.UserInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface UserInformationService extends SavingValidationService<UserInformationSavingDto> {

    UserInformation save(UserInformationSavingDto savingDto) throws DataSavingServiceException;

}
