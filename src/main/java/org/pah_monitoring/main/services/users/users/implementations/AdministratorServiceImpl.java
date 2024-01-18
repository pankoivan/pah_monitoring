package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.dto.saving.users.AdministratorSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.repositorites.users.AdministratorRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class AdministratorServiceImpl implements AdministratorService {

    private AdministratorRepository repository;

    private UserSecurityInformationService securityInformationService;

    private EmployeeInformationService employeeInformationService;

    private RegistrationSecurityCodeService codeService;

    private HospitalService hospitalService;

    @Override
    public List<Administrator> findAll() {
        return repository.findAll();
    }

    @Override
    public Administrator findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Администратор с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public List<Administrator> findAllByHospitalId(Integer id) throws DataSearchingServiceException {
        return repository.findAllByEmployeeInformationHospitalId(hospitalService.findById(id).getId());
    }

    @Override
    public Administrator add(AdministratorSavingDto savingDto) throws DataSavingServiceException {

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(null);

        EmployeeInformationSavingDto employeeInformationSavingDto = savingDto.getEmployeeInformationSavingDto();
        employeeInformationSavingDto.setId(null);

        employeeInformationSavingDto.getUserInformationSavingDto().setId(null);

        try {
            RegistrationSecurityCode code = codeService.findByStringUuid(savingDto.getCode());
            return repository.save(
                    Administrator
                            .builder()
                            .userSecurityInformation(securityInformationService.save(securityInformationSavingDto))
                            .employeeInformation(employeeInformationService.save(employeeInformationSavingDto, code.getHospital()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public Administrator edit(AdministratorSavingDto savingDto) throws DataSearchingServiceException, DataSavingServiceException {

        Administrator administrator = findById(savingDto.getId());

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(administrator.getUserSecurityInformation().getId());

        EmployeeInformationSavingDto employeeInformationSavingDto = savingDto.getEmployeeInformationSavingDto();
        employeeInformationSavingDto.setId(administrator.getEmployeeInformation().getId());

        employeeInformationSavingDto.getUserInformationSavingDto().setId(administrator.getEmployeeInformation().getUserInformation().getId());

        try {
            return repository.save(
                    Administrator
                            .builder()
                            .userSecurityInformation(securityInformationService.save(securityInformationSavingDto))
                            .employeeInformation(employeeInformationService.save(
                                    employeeInformationSavingDto,
                                    administrator.getEmployeeInformation().getHospital()
                            ))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public void checkCodeValidityForRegistration(AdministratorSavingDto savingDto) throws DataValidationServiceException {

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

        if (codeService.isNotSuitableForRole(code, Role.ADMINISTRATOR)) {
            throw new DataValidationServiceException("Код не предназначен для роли \"%s\"".formatted(Role.ADMINISTRATOR.getAlias()));
        }

        if (codeService.isNotSuitableForEmail(code, savingDto.getUserSecurityInformationSavingDto().getEmail())) {
            throw new DataValidationServiceException(
                    "Код не предназначен для почты \"%s\"".formatted(savingDto.getUserSecurityInformationSavingDto().getEmail())
            );
        }

    }

    @Override
    public void checkDataValidityForSaving(AdministratorSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        securityInformationService.checkDataValidityForSaving(savingDto.getUserSecurityInformationSavingDto(), bindingResult);
        employeeInformationService.checkDataValidityForSaving(savingDto.getEmployeeInformationSavingDto(), bindingResult);

    }

}
