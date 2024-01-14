package org.pah_monitoring.main.services.users.users.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.AdministratorSavingDto;
import org.pah_monitoring.main.entities.users.Administrator;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.AdministratorRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.pah_monitoring.main.services.users.users.interfaces.AdministratorService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class AdministratorServiceImpl implements AdministratorService {

    private final AdministratorRepository repository;

    private final UserSecurityInformationService userSecurityInformationService;

    private final EmployeeInformationService employeeInformationService;

    @Override
    public Administrator findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Администратор с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public Administrator save(AdministratorSavingDto savingDto) throws DataSavingServiceException {

        if (isNew()) {
            return create(savingDto);
        } else {
            return edit(savingDto);
        }

    }

    @Override
    public void checkDataValidityForSaving(AdministratorSavingDto administratorSavingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        userSecurityInformationService.checkDataValidityForSaving(administratorSavingDto.getUserSecurityInformationSavingDto(), bindingResult);
        employeeInformationService.checkDataValidityForSaving(administratorSavingDto.getEmployeeInformationSavingDto(), bindingResult);

    }

    private Administrator create(AdministratorSavingDto administratorDto) {

        if (codeNotForRole || isCodeExpired) {
            throw new DataValidationServiceException("");
        }

        administratorDto.getUserSecurityInformationDto().setId(null);
        administratorDto.getEmployeeInformationDto().setId(null);
        UserSecurityInformation userSecurityInformation = userSecurityInformationService
                .save(administratorDto.getUserSecurityInformationDto());
        EmployeeInformation employeeInformation = employeeInformationService
                .save(administratorDto.getEmployeeInformationDto());

    }

    private Administrator edit(AdministratorSavingDto administratorDto) {

    }

}
