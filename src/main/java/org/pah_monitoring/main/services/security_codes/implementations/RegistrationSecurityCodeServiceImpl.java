package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.utils.UuidUtils;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
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
    public boolean isExpired(RegistrationSecurityCode code) {
        return code.getExpirationDate().isAfter(LocalDateTime.now());
    }

    @Override
    public boolean isSuitableForRole(RegistrationSecurityCode code, Role role) {
        return code.getRole() == role;
    }

    @Override
    public RegistrationSecurityCode findByUuid(UUID uuid) throws DataSearchingServiceException {
        return repository.findByCode(uuid).orElseThrow(
                () -> new DataSearchingServiceException("Код \"%s\" не существует".formatted(uuid))
        );
    }

    @Override
    public RegistrationSecurityCode findByStringUuid(String stringUuid) throws UuidUtilsException, DataSearchingServiceException {
        return repository.findByCode(UuidUtils.fromString(stringUuid)).orElseThrow(
                () -> new DataSearchingServiceException("Код \"%s\" не существует".formatted(stringUuid))
        );
    }

    @Override
    public boolean existsByStringUuid(String stringUuid) {
        try {
            findByStringUuid(stringUuid);
            return true;
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            return false;
        }
    }

}
