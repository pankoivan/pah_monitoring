package org.pah_monitoring.main.services.main.security_codes.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.UuidUtils;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.repositorites.main.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final RegistrationSecurityCodeRepository repository;

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

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Transactional
    @Override
    public void deleteByEmail(String email) throws DataSearchingServiceException, DataDeletionServiceException {
        if (repository.findByEmail(email).isEmpty()) {
            throw new DataSearchingServiceException("Не существует кода, связанного с почтой \"%s\"".formatted(email));
        }
        try {
            repository.deleteByEmail(email);
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с полем \"email\" = \"%s\" не была удалена".formatted(email), e);
        }
    }

}
