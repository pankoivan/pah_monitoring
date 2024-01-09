package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.PageAccessSecurityCodeSavingDto;
import org.pah_monitoring.main.entities.security_codes.PageAccessSecurityCode;
import org.pah_monitoring.main.repositorites.security_codes.PageAccessSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.PageAccessSecurityCodeService;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@AllArgsConstructor
@Service
public class PageAccessSecurityCodeServiceImpl implements PageAccessSecurityCodeService {

    private final PageAccessSecurityCodeRepository repository;

    @Override
    public PageAccessSecurityCode save(PageAccessSecurityCodeSavingDto dtoCode, UUID generatedCode) {
        PageAccessSecurityCode code = PageAccessSecurityCode
                .builder()
                .code(generatedCode)
                .expirationDate(LocalDateTime.now().plusDays(dtoCode.expirationDate().getDays()))
                .build();
        return repository.save(code);
    }

    @Override
    public boolean existsByCode(UUID code) {
        return repository.existsByCode(code);
    }

}
