package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByMainAdminSavingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service("codeGeneratorByMainAdmin")
public class RegistrationSecurityCodeGenerationByMainAdminServiceImpl
        implements RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByMainAdminSavingDto> {

    private RegistrationSecurityCodeRepository repository;

    private HospitalRegistrationRequestService requestService;

    @Override
    public RegistrationSecurityCode generate(RegistrationSecurityCodeByMainAdminSavingDto savingDto) throws DataSavingServiceException {
        try {
            HospitalRegistrationRequest request = requestService.findById(savingDto.getHospitalRegistrationRequestId());
            return repository.save(
                    RegistrationSecurityCode
                            .builder()
                            .code(UUID.randomUUID())
                            .role(Role.ADMINISTRATOR)
                            .email(requestService.findById(request.getId()).getEmail())
                            .expirationDate(LocalDateTime.now().plusDays(savingDto.getExpirationDate().getDays()))
                            .hospital(request.getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(RegistrationSecurityCodeByMainAdminSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
