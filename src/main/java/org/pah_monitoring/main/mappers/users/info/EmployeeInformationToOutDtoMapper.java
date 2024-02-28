package org.pah_monitoring.main.mappers.users.info;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.users.info.EmployeeInformationOutDto;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("employeeInformationMapper")
public class EmployeeInformationToOutDtoMapper implements BaseEntityToOutDtoMapper<EmployeeInformation, EmployeeInformationOutDto> {

    @Override
    @NullWhenNull
    public EmployeeInformationOutDto map(EmployeeInformation employeeInformation) {
        return EmployeeInformationOutDto
                .builder()
                .post(employeeInformation.getPost())
                .build();
    }

}
