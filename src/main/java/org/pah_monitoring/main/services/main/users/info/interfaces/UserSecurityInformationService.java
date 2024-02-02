package org.pah_monitoring.main.services.main.users.info.interfaces;

import org.pah_monitoring.main.dto.in.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.saving.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.services.main.users.info.interfaces.common.UserInfoService;

public interface UserSecurityInformationService extends
        UserInfoService<UserSecurityInformation, UserSecurityInformationAddingDto, UserSecurityInformationEditingDto, UserSecurityInformationSavingDto> {

    boolean existsByEmail(String email);

}
