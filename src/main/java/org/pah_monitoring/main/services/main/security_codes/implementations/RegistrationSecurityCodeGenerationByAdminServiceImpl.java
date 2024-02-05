package org.pah_monitoring.main.services.main.security_codes.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.security_codes.RegistrationSecurityCodeByAdminAddingDto;
import org.pah_monitoring.main.entities.main.enums.Role;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.main.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserExtractionService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.UUID;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service("codeGeneratorByAdmin")
public class RegistrationSecurityCodeGenerationByAdminServiceImpl
        implements RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByAdminAddingDto> {

    private final RegistrationSecurityCodeRepository repository;

    private UserSecurityInformationService securityInformationService;

    private HospitalRegistrationRequestService requestService;

    private CurrentUserExtractionService extractionService;

    @Override
    public RegistrationSecurityCode add(RegistrationSecurityCodeByAdminAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    RegistrationSecurityCode
                            .builder()
                            .code(UUID.randomUUID())
                            .role(addingDto.getRole())
                            .email(addingDto.getEmail())
                            .expirationDate(LocalDateTime.now().plusDays(addingDto.getExpirationDate().getDays()))
                            .hospital(extractionService.administrator().getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(RegistrationSecurityCodeByAdminAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        if (repository.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException("Пользователю с почтой \"%s\" уже выдан код".formatted(addingDto.getEmail()));
        }

        if (securityInformationService.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException("Пользователь с почтой \"%s\" уже зарегистрирован".formatted(addingDto.getEmail()));
        }

        if (requestService.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Почта \"%s\" указана в заявке на регистрацию медицинского учреждения".formatted(addingDto.getEmail())
            );
        }

        if (addingDto.getRole() == Role.MAIN_ADMINISTRATOR) {
            throw new DataValidationServiceException(
                    "Для роли \"%s\" не предусмотрена генерация кода".formatted(Role.MAIN_ADMINISTRATOR.getAlias())
            );
        }

    }

}
