package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface UserSecurityInformationService extends SavingValidationService<UserSecurityInformationAddingDto> {

    boolean existsByEmail(String email);

    UserSecurityInformation findById(Integer id) throws DataSearchingServiceException;

    UserSecurityInformation add(UserSecurityInformationAddingDto addingDto) throws DataSavingServiceException;

}
