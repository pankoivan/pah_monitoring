package org.pah_monitoring.main.services.security_codes.implementations;

import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.repositorites.security_codes.PageAccessSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final PageAccessSecurityCodeRepository repository;

    private final SecurityCodeGenerationService codeGenerationService;

    @Autowired
    public RegistrationSecurityCodeServiceImpl(PageAccessSecurityCodeRepository repository, SecurityCodeGenerationService codeGenerationService) {
        this.repository = repository;
        this.codeGenerationService = codeGenerationService;
    }

    @Override
    public void save(RegistrationSecurityCode code) {
        // todo
    }

    @Override
    public boolean existsByCode(UUID code) {
        return repository.existsByCode(code);
    }

}
