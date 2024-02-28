package org.pah_monitoring.main.mappers.users.users.common;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.users.users.common.UserOutDto;
import org.pah_monitoring.main.entities.main.users.users.common.User;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("userMapper")
public class UserToOutDtoMapper implements BaseEntityToOutDtoMapper<User, UserOutDto> {

    @Override
    @NullWhenNull
    public UserOutDto map(User user) {
        return UserOutDto
                .builder()
                .fullName(user.getUserInformation().getFullName())
                .build();
    }

}
