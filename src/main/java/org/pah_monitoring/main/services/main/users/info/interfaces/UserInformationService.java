package org.pah_monitoring.main.services.main.users.info.interfaces;

import org.pah_monitoring.main.dto.in.users.info.user.UserInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.main.users.info.interfaces.common.UserInfoService;

public interface UserInformationService extends
        UserInfoService<UserInformation, UserInformationAddingDto, UserInformationEditingDto, UserInformationSavingDto> {

    void checkUserActivity(User user) throws DataValidationServiceException;

}
