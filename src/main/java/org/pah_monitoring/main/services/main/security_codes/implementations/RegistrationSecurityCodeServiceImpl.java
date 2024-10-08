package org.pah_monitoring.main.services.main.security_codes.implementations;

import jakarta.transaction.Transactional;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.UuidUtils;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.UUID;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class RegistrationSecurityCodeServiceImpl implements RegistrationSecurityCodeService {

    private final RegistrationSecurityCodeRepository repository;

    private HospitalService hospitalService;

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
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
    @Transactional
    public void deleteByEmail(String email) throws DataDeletionServiceException {
        try {
            repository.deleteByEmail(email);
        } catch (Exception e) {
            throw new DataDeletionServiceException("Код, сгенерированный для почты \"%s\", не был удалён".formatted(email), e);
        }
    }

    @Override
    public void deleteExpired() {
        repository.findAll()
                .stream()
                .filter(RegistrationSecurityCode::isExpired)
                .forEach(code -> {
                    if (code.isForHospitalThatWaitingRegistration()) {
                        hospitalService.downgrade(code.getHospital());
                    }
                    repository.deleteById(code.getId());
                });
    }

}
