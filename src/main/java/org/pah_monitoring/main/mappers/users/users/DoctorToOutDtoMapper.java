package org.pah_monitoring.main.mappers.users.users;

import org.pah_monitoring.main.dto.out.users.users.DoctorOutDto;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("doctorMapper")
public class DoctorToOutDtoMapper implements BaseEntityToOutDtoMapper<Doctor, DoctorOutDto> {

    @Override
    public DoctorOutDto map(Doctor doctor) {
        return DoctorOutDto
                .builder()
                .fullName(doctor.getEmployeeInformation().getUserInformation().getFullName())
                .build();
    }

}
