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
    public Patient save(PatientSavingDto savingDto) throws DataSavingServiceException {
        try {
            return edit(findById(savingDto.getId()), savingDto);
        } catch (DataSearchingServiceException e) {
            return create(savingDto);
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

    private Patient create(PatientSavingDto savingDto) throws DataSavingServiceException {

        RegistrationSecurityCode code;
        try {
            code = codeService.findByStringUuid(savingDto.getCode());
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new DataSavingServiceException(e.getMessage(), e);
        }

        if (codeService.isExpired(code)) {
            throw new DataSavingServiceException(
                    "Истёк срок действия кода. Код был действителен до %s"
                            .formatted(DateTimeFormatConstants.DAY_MONTH_YEAR_WHITESPACE_HOUR_MINUTE_SECOND.format(code.getExpirationDate()))
            );
        }
        if (!codeService.isSuitableForRole(code, Role.PATIENT)) {
            throw new DataSavingServiceException("Код не предназначен для роли \"%s\"".formatted(Role.PATIENT));
        }

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(null);

        UserInformationSavingDto userInformationSavingDto = savingDto.getUserInformationSavingDto();
        userInformationSavingDto.setId(null);

        try {
            return repository.save(
                    Patient
                            .builder()
                            .userSecurityInformation(securityInformationService.save(securityInformationSavingDto))
                            .userInformation(userInformationService.save(userInformationSavingDto))
                            .hospital(code.getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    private Patient edit(Patient patient, PatientSavingDto savingDto) throws DataSavingServiceException {

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(patient.getUserSecurityInformation().getId());

        UserInformationSavingDto userInformationSavingDto = savingDto.getUserInformationSavingDto();
        userInformationSavingDto.setId(patient.getUserInformation().getId());

        try {
            return repository.save(
                    Patient
                            .builder()
                            .userSecurityInformation(securityInformationService.save(securityInformationSavingDto))
                            .userInformation(userInformationService.save(userInformationSavingDto))
                            .hospital(patient.getHospital())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

}
