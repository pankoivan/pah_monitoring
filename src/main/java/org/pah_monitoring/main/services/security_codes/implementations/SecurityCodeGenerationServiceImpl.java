package org.pah_monitoring.main.services.security_codes.implementations;

import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

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

    @Autowired
    public void setRegistrationSecurityCodeService(RegistrationSecurityCodeService registrationSecurityCodeService) {
        this.registrationSecurityCodeService = registrationSecurityCodeService;
    }

    @Autowired
    public void setPageAccessSecurityCodeService(PageAccessSecurityCodeService pageAccessSecurityCodeService) {
        this.pageAccessSecurityCodeService = pageAccessSecurityCodeService;
    }

}
