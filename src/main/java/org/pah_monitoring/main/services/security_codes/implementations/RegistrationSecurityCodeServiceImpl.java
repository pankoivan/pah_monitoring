package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.repositorites.security_codes.PageAccessSecurityCodeRepository;
import org.pah_monitoring.main.services.other.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.security_codes.interfaces.SecurityCodeGenerationService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final PageAccessSecurityCodeRepository repository;

    private final SecurityCodeGenerationService codeGenerationService;

    private final HospitalService hospitalService;

    @Override
    public void save(RegistrationSecurityCode code) {

        code.setCode(codeGenerationService.generate());
        code.setExpirationDate(LocalDateTime.now().plusDays(code.getExpirationDateEnum().getDays()));

        if (!hospitalService.isNew(code.getHospital())) {

        }

    }

    @Override
    public boolean existsByCode(UUID code) {
        return repository.existsByCode(code);
    }

}
