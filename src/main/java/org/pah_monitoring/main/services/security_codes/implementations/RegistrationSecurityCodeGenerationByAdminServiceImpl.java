package org.pah_monitoring.main.services.security_codes.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.AuthenticationUtils;
import org.pah_monitoring.main.entities.dto.saving.security_codes.RegistrationSecurityCodeByAdminSavingDto;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.security_codes.RegistrationSecurityCodeRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeGenerationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;
import java.util.UUID;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service("codeGeneratorByAdmin")
public class RegistrationSecurityCodeGenerationByAdminServiceImpl
        implements RegistrationSecurityCodeGenerationService<RegistrationSecurityCodeByAdminSavingDto> {

    private RegistrationSecurityCodeRepository repository;

    private UserSecurityInformationService securityInformationService;

    private HospitalService hospitalService;

    @Override
    public RegistrationSecurityCode generate(RegistrationSecurityCodeByAdminSavingDto savingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    RegistrationSecurityCode
                            .builder()
                            .code(UUID.randomUUID())
                            .role(savingDto.getRole())
                            .email(savingDto.getEmail())
                            .expirationDate(LocalDateTime.now().plusDays(savingDto.getExpirationDate().getDays()))
                            .hospital(
                                    hospitalService.findById(9)
                                    /*AuthenticationUtils.extractCurrentUser(
                                            SecurityContextHolder.getContext().getAuthentication(),
                                            Administrator.class
                                    ).getEmployeeInformation()
                                            .getHospital()*/
                            )
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(RegistrationSecurityCodeByAdminSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (securityInformationService.existsByEmail(savingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Пользователь с почтой \"%s\" уже зарегистрирован".formatted(savingDto.getEmail())
            );
        }

    }

}
