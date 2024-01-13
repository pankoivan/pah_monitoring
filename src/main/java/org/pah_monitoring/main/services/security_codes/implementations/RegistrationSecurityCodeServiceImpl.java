package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.stereotype.Service;

import java.util.UUID;

@AllArgsConstructor
@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final RegistrationSecurityCodeRepository repository;

    @Override
    public boolean existsByStringCode(String code) {
        try {
            return repository.existsByCode(UUID.fromString(code));
        } catch (IllegalArgumentException e) {
            return false;
        }
    }

}
