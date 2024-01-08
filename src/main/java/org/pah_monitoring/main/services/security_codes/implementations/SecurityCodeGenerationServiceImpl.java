package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@NoArgsConstructor
@Setter
@Service
public class SecurityCodeGenerationServiceImpl implements SecurityCodeGenerationService {

    private RegistrationSecurityCodeService registrationSecurityCodeService;

    private PageAccessSecurityCodeService pageAccessSecurityCodeService;

    @Override
    public UUID generate() {
        UUID code = UUID.randomUUID();
        while (registrationSecurityCodeService.existsByCode(code) || pageAccessSecurityCodeService.existsByCode(code)) {
            code = UUID.randomUUID();
        }
        return code;
    }

}
