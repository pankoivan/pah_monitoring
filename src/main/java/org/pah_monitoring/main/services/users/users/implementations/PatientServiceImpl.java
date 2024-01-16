package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.dto.saving.users.PatientSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.Patient;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.SecurityCodeValidationServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.repositorites.users.PatientRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class PatientServiceImpl implements PatientService {

    private final PatientRepository repository;

    private final UserSecurityInformationService securityInformationService;

    private final UserInformationService userInformationService;

    private final RegistrationSecurityCodeService codeService;

    @Override
    public Patient findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Пациент с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public Patient add(PatientSavingDto savingDto) throws SecurityCodeValidationServiceException, DataSavingServiceException {

        RegistrationSecurityCode code;
        try {
            code = codeService.findByStringUuid(savingDto.getCode());
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new SecurityCodeValidationServiceException(e.getMessage(), e);
        }

        if (codeService.isExpired(code)) {
            throw new SecurityCodeValidationServiceException(
                    "Истёк срок действия кода. Код был действителен до %s"
                            .formatted(DateTimeFormatConstants.DAY_MONTH_YEAR_WHITESPACE_HOUR_MINUTE_SECOND.format(code.getExpirationDate()))
            );
        }

        if (codeService.isNotSuitableForRole(code, Role.PATIENT)) {
            throw new SecurityCodeValidationServiceException("Код не предназначен для роли \"%s\"".formatted(Role.PATIENT.getAlias()));
        }

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(null);

        UserInformationSavingDto userInformationSavingDto = savingDto.getUserInformationSavingDto();
        userInformationSavingDto.setId(null);

        try {
            return repository.save(
                    Patient
                            .builder()
                            .hospital(code.getHospital())
                            .userSecurityInformation(securityInformationService.save(securityInformationSavingDto))
                            .userInformation(userInformationService.save(userInformationSavingDto))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public Patient edit(PatientSavingDto savingDto) throws DataSearchingServiceException, DataSavingServiceException {

        Patient patient = findById(savingDto.getId());

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(patient.getUserSecurityInformation().getId());

        UserInformationSavingDto userInformationSavingDto = savingDto.getUserInformationSavingDto();
        userInformationSavingDto.setId(patient.getUserInformation().getId());

        try {
            return repository.save(
                    Patient
                            .builder()
                            .hospital(patient.getHospital())
                            .userSecurityInformation(securityInformationService.save(securityInformationSavingDto))
                            .userInformation(userInformationService.save(userInformationSavingDto))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public void checkDataValidityForSaving(PatientSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        securityInformationService.checkDataValidityForSaving(savingDto.getUserSecurityInformationSavingDto(), bindingResult);
        userInformationService.checkDataValidityForSaving(savingDto.getUserInformationSavingDto(), bindingResult);

    }

}
