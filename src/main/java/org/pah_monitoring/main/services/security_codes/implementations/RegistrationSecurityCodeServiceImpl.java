package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.other.Hospital;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final RegistrationSecurityCodeRepository repository;

    @Override
    public void save(RegistrationSecurityCode code, UUID generatedCode, Hospital hospital) {
        code.setCode(generatedCode);
        code.setHospital(hospital);
        code.setExpirationDate(LocalDateTime.now().plusDays(code.getExpirationDateEnum().getDays()));
        repository.save(code);
    }

    @Override
    public boolean existsByCode(UUID code) {
        return repository.existsByCode(code);
    }

}
