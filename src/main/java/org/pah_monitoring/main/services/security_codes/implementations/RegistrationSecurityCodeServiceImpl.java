package org.pah_monitoring.main.services.security_codes.implementations;

import jakarta.transaction.Transactional;
import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.UuidUtils;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private RegistrationSecurityCodeRepository repository;

    @Override
    public boolean isExpired(RegistrationSecurityCode code) {
        return LocalDateTime.now().isAfter(code.getExpirationDate());
    }

    @Override
    public boolean isNotSuitableForRole(RegistrationSecurityCode code, Role role) {
        return code.getRole() != role;
    }

    @Override
    public boolean isNotSuitableForEmail(RegistrationSecurityCode code, String email) {
        return !code.getEmail().equals(email);
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
    public boolean existsByUuid(UUID uuid) {
        return repository.existsByCode(uuid);
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

    @Transactional
    @Override
    public void deleteByEmail(String email) throws DataSearchingServiceException, DataDeletionServiceException {
        if (repository.findByEmail(email).isEmpty()) {
            throw new DataSearchingServiceException("Для почты \"%s\" не существует кода".formatted(email));
        }
        try {
            repository.deleteByEmail(email);
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с полем \"email\" = \"%s\" не была удалена".formatted(email), e);
        }
    }

}
