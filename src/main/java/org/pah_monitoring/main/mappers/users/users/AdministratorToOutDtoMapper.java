package org.pah_monitoring.main.mappers.users.users;

import org.pah_monitoring.main.dto.out.users.users.common.UserOutDto;
import org.pah_monitoring.main.entities.main.users.users.Administrator;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("administratorMapper")
public class AdministratorToOutDtoMapper implements BaseEntityToOutDtoMapper<Administrator, UserOutDto> {

    @Override
    public UserOutDto map(Administrator administrator) {
        return UserOutDto
                .builder()
                .fullName(administrator.getEmployeeInformation().getUserInformation().getFullName())
                .build();
    }

}
