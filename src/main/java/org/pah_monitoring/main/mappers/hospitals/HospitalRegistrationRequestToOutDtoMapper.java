package org.pah_monitoring.main.mappers.hospitals;

import org.pah_monitoring.main.dto.out.hospitals.HospitalRegistrationRequestOutDto;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("hospitalRegistrationRequestMapper")
public class HospitalRegistrationRequestToOutDtoMapper
        implements BaseEntityToOutDtoMapper<HospitalRegistrationRequest, HospitalRegistrationRequestOutDto> {

    @Override
    public HospitalRegistrationRequestOutDto map(HospitalRegistrationRequest hospitalRegistrationRequest) {
        return HospitalRegistrationRequestOutDto
                .builder()
                .hospitalName(hospitalRegistrationRequest.getHospital().getName())
                .email(hospitalRegistrationRequest.getEmail())
                .build();
    }

}
