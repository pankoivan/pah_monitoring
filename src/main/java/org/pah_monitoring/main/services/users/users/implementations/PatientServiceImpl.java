package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
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
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.users.users.interfaces.DoctorService;
import org.pah_monitoring.main.services.users.users.interfaces.PatientService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class PatientServiceImpl implements PatientService {

    private PatientRepository repository;

    private UserSecurityInformationService securityInformationService;

    private UserInformationService userInformationService;

    private RegistrationSecurityCodeService codeService;

    private HospitalService hospitalService;

    private DoctorService doctorService;

    @Override
    public List<Patient> findAll() {
        return repository.findAll();
    }

    @Override
    public Patient findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Пациент с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<Patient> findAllByHospitalId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByHospitalId(hospitalService.findById(id).getId());
    }

    @Override
    public Patient add(PatientSavingDto savingDto) throws DataSavingServiceException {

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(null);

        UserInformationSavingDto userInformationSavingDto = savingDto.getUserInformationSavingDto();
        userInformationSavingDto.setId(null);

        try {
            RegistrationSecurityCode code = codeService.findByStringUuid(savingDto.getCode());
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
    public void checkCodeValidityForRegistration(PatientSavingDto savingDto) throws DataValidationServiceException {

        RegistrationSecurityCode code;
        try {
            code = codeService.findByStringUuid(savingDto.getCode());
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
        }

        if (codeService.isExpired(code)) {
            throw new DataValidationServiceException(
                    "Истёк срок действия кода. Код был действителен до %s"
                            .formatted(DateTimeFormatConstants.DAY_MONTH_YEAR_WHITESPACE_HOUR_MINUTE_SECOND.format(code.getExpirationDate()))
            );
        }

        if (codeService.isNotSuitableForRole(code, Role.PATIENT)) {
            throw new DataValidationServiceException("Код не предназначен для роли \"%s\"".formatted(Role.PATIENT.getAlias()));
        }

        if (codeService.isNotSuitableForEmail(code, savingDto.getUserSecurityInformationSavingDto().getEmail())) {
            throw new DataValidationServiceException(
                    "Код не предназначен для почты \"%s\"".formatted(savingDto.getUserSecurityInformationSavingDto().getEmail())
            );
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
