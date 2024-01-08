package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.security_codes.PageAccessSecurityCode;
import org.pah_monitoring.main.repositorites.security_codes.PageAccessSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class PageAccessSecurityCodeServiceImpl implements PageAccessSecurityCodeService {

    private final PageAccessSecurityCodeRepository repository;

    private final SecurityCodeGenerationService codeGenerationService;

    @Override
    public void save(PageAccessSecurityCode code) {
        // todo
    }

    @Override
    public boolean existsByCode(UUID code) {
        return repository.existsByCode(code);
    }

}
