package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.Setter;
import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Setter
@Service
public class SecurityCodeGenerationServiceImpl implements SecurityCodeGenerationService {

    private RegistrationSecurityCodeService registrationSecurityCodeService;

    private PageAccessSecurityCodeService pageAccessSecurityCodeService;

    public SecurityCodeGenerationServiceImpl() {

    }

    @Override
    public UUID generate() {
        UUID code = UUID.randomUUID();
        while (registrationSecurityCodeService.existsByCode(code) || pageAccessSecurityCodeService.existsByCode(code)) {
            code = UUID.randomUUID();
        }
        return code;
    }

}
