package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface UserSecurityInformationService extends SavingValidationService<UserSecurityInformationSavingDto> {

    UserSecurityInformation save(UserSecurityInformationSavingDto savingDto) throws DataSavingServiceException;

}
