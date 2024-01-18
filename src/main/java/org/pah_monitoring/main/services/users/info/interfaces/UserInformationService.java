package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.services.users.info.interfaces.common.UserInfoService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface UserInformationService extends
        UserInfoService<UserInformation, UserInformationAddingDto, UserInformationEditingDto>,
        SavingValidationService<UserInformationAddingDto> {

}
