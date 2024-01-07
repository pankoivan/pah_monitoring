package org.pah_monitoring.main.services.security_codes.implementations;

import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class SecurityCodeGenerationServiceImpl implements SecurityCodeGenerationService {

    private final RegistrationSecurityCodeService registrationSecurityCodeService;

    private final PageAccessSecurityCodeService pageAccessSecurityCodeService;

    @Autowired
    public SecurityCodeGenerationServiceImpl(RegistrationSecurityCodeService registrationSecurityCodeService,
                                             PageAccessSecurityCodeService pageAccessSecurityCodeService) {

        this.registrationSecurityCodeService = registrationSecurityCodeService;
        this.pageAccessSecurityCodeService = pageAccessSecurityCodeService;
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
