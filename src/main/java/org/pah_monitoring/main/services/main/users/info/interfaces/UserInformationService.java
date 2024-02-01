package org.pah_monitoring.main.services.main.users.info.interfaces;

import org.pah_monitoring.main.entities.additional.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.additional.dto.saving.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.entities.additional.dto.saving.users.info.saving.UserInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.services.main.users.info.interfaces.common.UserInfoService;

public interface UserInformationService extends
        UserInfoService<UserInformation, UserInformationAddingDto, UserInformationEditingDto, UserInformationSavingDto> {

}
