package org.pah_monitoring.main.dto.out.security_codes;

import lombok.Builder;
import lombok.Data;
import org.pah_monitoring.main.dto.out.common.interfaces.OutDto;

@Data
@Builder
public class RegistrationSecurityCodeOutDto implements OutDto {

    private String email;

    private String roleAlias;

    private String hospitalName;

    private String formattedExpirationDate;

}
