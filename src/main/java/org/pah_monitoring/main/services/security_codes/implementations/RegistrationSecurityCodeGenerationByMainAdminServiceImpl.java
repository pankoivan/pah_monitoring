package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByMainAdminAddingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("codeGeneratorByMainAdmin")
public class RegistrationSecurityCodeGenerationByMainAdminServiceImpl
        implements RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByMainAdminAddingDto> {

    private final RegistrationSecurityCodeRepository repository;

    private HospitalRegistrationRequestService requestService;

    @Override
    public RegistrationSecurityCode add(RegistrationSecurityCodeByMainAdminAddingDto addingDto) throws DataSavingServiceException {
        try {
            HospitalRegistrationRequest request = requestService.findById(addingDto.getHospitalRegistrationRequestId());
            return repository.save(
                    RegistrationSecurityCode
                            .builder()
                            .code(UUID.randomUUID())
                            .role(Role.ADMINISTRATOR)
                            .email(request.getEmail())
                            .expirationDate(LocalDateTime.now().plusDays(addingDto.getExpirationDate().getDays()))
                            .hospital(request.getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(RegistrationSecurityCodeByMainAdminAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        HospitalRegistrationRequest request;
        try {
            request = requestService.findById(addingDto.getHospitalRegistrationRequestId());
        } catch (DataSearchingServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }

        if (request.getHospital().getCurrentState() != Hospital.CurrentState.WAITING_CODE) {
            throw new DataValidationServiceException(
                    "Для медицинского учреждения \"%s\" уже сгенерирован код, или оно уже зарегистрировано"
                            .formatted(request.getHospital().getName())
            );
        }

    }

}
