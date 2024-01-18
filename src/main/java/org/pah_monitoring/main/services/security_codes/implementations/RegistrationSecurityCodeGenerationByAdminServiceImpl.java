package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByAdminAddingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.users.Administrator;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
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
                            .hospital(
                                    AuthenticationUtils.extractCurrentUser(
                                            SecurityContextHolder.getContext().getAuthentication(),
                                            Administrator.class
                                    ).getHospital()
                            )
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(RegistrationSecurityCodeByAdminAddingDto addingDto, BindingResult bindingResult)
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
