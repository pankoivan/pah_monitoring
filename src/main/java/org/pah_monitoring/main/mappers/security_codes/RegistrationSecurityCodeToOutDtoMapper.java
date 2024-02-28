package org.pah_monitoring.main.mappers.security_codes;

import org.pah_monitoring.main.aop.annotations.NullWhenNull;
import org.pah_monitoring.main.dto.out.security_codes.RegistrationSecurityCodeOutDto;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.mappers.common.interfaces.BaseEntityToOutDtoMapper;
import org.springframework.stereotype.Component;

@Component("codeMapper")
public class RegistrationSecurityCodeToOutDtoMapper implements BaseEntityToOutDtoMapper<RegistrationSecurityCode, RegistrationSecurityCodeOutDto> {

    @Override
    @NullWhenNull
    public RegistrationSecurityCodeOutDto map(RegistrationSecurityCode registrationSecurityCode) {
        return RegistrationSecurityCodeOutDto
                .builder()
                .email(registrationSecurityCode.getEmail())
                .roleAlias(registrationSecurityCode.getRole().getAlias())
                .hospitalName(registrationSecurityCode.getHospital().getName())
                .formattedExpirationDate(registrationSecurityCode.getFormattedExpirationDate())
                .build();
    }

}
