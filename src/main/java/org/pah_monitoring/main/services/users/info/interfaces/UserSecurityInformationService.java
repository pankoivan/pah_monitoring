package org.pah_monitoring.main.services.users.info.interfaces;

import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.services.users.info.interfaces.common.UserInfoService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;

public interface UserSecurityInformationService extends
        UserInfoService<UserSecurityInformation, UserSecurityInformationAddingDto, UserSecurityInformationEditingDto>,
        SavingValidationService<UserSecurityInformationAddingDto> {

    boolean existsByEmail(String email);

}
