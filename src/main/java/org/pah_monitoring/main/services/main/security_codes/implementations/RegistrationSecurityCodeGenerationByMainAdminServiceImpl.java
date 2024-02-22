package org.pah_monitoring.main.services.main.security_codes.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.security_codes.RegistrationSecurityCodeByMainAdminAddingDto;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
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

    @Qualifier("codeEmailSender")
    private EmailSender<RegistrationSecurityCode> codeEmailSender;

    @Override
    public RegistrationSecurityCode generate(RegistrationSecurityCodeByMainAdminAddingDto addingDto) throws DataSavingServiceException {
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
    public RegistrationSecurityCode generateAndSend(RegistrationSecurityCodeByMainAdminAddingDto addingDto)
            throws DataSavingServiceException, EmailSendingException {

        RegistrationSecurityCode code = generate(addingDto);
        try {
            codeEmailSender.send(code.getEmail(), code);
        } catch (EmailSendingException e) {
            repository.deleteById(code.getId());
            throw e;
        }
        return code;

    }

    @Override
    public void checkDataValidityForAdding(RegistrationSecurityCodeByMainAdminAddingDto addingDto, BindingResult bindingResult)
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
