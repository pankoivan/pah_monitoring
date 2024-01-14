package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private final EmployeeInformationRepository repository;

    @Override
    public EmployeeInformation save(EmployeeInformationSavingDto employeeInformationDto) throws DataSavingServiceException {
        try {
            return null; // todo

            if (isNew()) {

            }

        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(employeeInformationDto));
        }
    }

    @Override
    public void checkDataValidityForSaving(EmployeeInformation employeeInformation, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
