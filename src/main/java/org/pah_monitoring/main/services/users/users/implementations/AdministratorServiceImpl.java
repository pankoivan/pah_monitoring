package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.main.entities.dto.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.dto.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.dto.saving.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.enums.Role;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.utils.UuidUtilsException;
import org.pah_monitoring.main.repositorites.users.AdministratorRepository;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository repository;

    private final UserSecurityInformationService securityInformationService;

    private final EmployeeInformationService employeeInformationService;

    private final RegistrationSecurityCodeService codeService;

    @Override
    public Administrator findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Администратор с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public Administrator save(AdministratorSavingDto savingDto) throws DataSavingServiceException {

        try {
            return edit(findById(savingDto.getId()), savingDto);
        } catch (DataSearchingServiceException e) {
            return create(savingDto);
        }

    }

    @Override
    public void checkDataValidityForSaving(AdministratorSavingDto administratorSavingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        securityInformationService.checkDataValidityForSaving(administratorSavingDto.getUserSecurityInformationSavingDto(), bindingResult);
        employeeInformationService.checkDataValidityForSaving(administratorSavingDto.getEmployeeInformationSavingDto(), bindingResult);

    }

    private Administrator create(AdministratorSavingDto savingDto) throws DataSavingServiceException {

        RegistrationSecurityCode code;
        try {
            code = codeService.findByStringUuid(savingDto.getCode());
        } catch (UuidUtilsException | DataSearchingServiceException e) {
            throw new DataSavingServiceException(e.getMessage(), e);
        }

        if (codeService.isExpired(code)) {
            throw new DataSavingServiceException(
                    "Истёк срок действия кода. Код был действителен до %s"
                            .formatted(DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(code.getExpirationDate()))
            );
        }
        if (!codeService.isSuitableForRole(code, Role.ADMINISTRATOR)) {
            throw new DataSavingServiceException("Код не предназначен для роли \"%s\"".formatted(Role.ADMINISTRATOR));
        }

        UserSecurityInformationSavingDto securityInformationSavingDto = savingDto.getUserSecurityInformationSavingDto();
        securityInformationSavingDto.setId(null);

        EmployeeInformationSavingDto employeeInformationSavingDto = savingDto.getEmployeeInformationSavingDto();
        employeeInformationSavingDto.setId(null);

        employeeInformationSavingDto.getUserInformationSavingDto().setId(null);

        try {
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

    private Administrator edit(Administrator administrator, AdministratorSavingDto savingDto) throws DataSavingServiceException {

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
                                    administrator.getEmployeeInformation().getHospital())
                            )
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

}
