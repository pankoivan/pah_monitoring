package org.pah_monitoring.main.mappers.users.users;

import org.pah_monitoring.main.dto.out.users.users.PatientOutDto;
import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("patientMapper")
public class PatientToOutDtoMapper implements BaseEntityToOutDtoMapper<Patient, PatientOutDto> {

    @Override
    public PatientOutDto map(Patient patient) {
        return PatientOutDto
                .builder()
                .fullName(patient.getUserInformation().getFullName())
                .build();
    }

}
