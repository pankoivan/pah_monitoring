package org.pah_monitoring.main.mappers.users.info;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.users.info.UserSecurityInformationOutDto;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("userSecurityInformationMapper")
public class UserSecurityInformationToOutDtoMapper implements BaseEntityToOutDtoMapper<UserSecurityInformation, UserSecurityInformationOutDto> {

    @Override
    @NullWhenNull
    public UserSecurityInformationOutDto map(UserSecurityInformation userSecurityInformation) {
        return UserSecurityInformationOutDto
                .builder()
                .email(userSecurityInformation.getEmail())
                .build();
    }

}
