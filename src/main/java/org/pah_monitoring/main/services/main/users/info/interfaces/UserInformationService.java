package org.pah_monitoring.main.services.main.users.info.interfaces;

import org.pah_monitoring.main.dto.in.users.info.user.UserInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.services.main.users.info.interfaces.common.UserInfoService;

public interface UserInformationService extends
        UserInfoService<UserInformation, UserInformationAddingDto, UserInformationEditingDto, UserInformationSavingDto> {

}
