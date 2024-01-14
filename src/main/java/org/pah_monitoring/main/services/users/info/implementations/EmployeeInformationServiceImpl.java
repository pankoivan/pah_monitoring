package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private final EmployeeInformationRepository repository;

    private final UserInformationService userInformationService;

    @Override
    public EmployeeInformation save(EmployeeInformationSavingDto employeeInformationDto, Hospital hospital)
            throws DataSavingServiceException {

        try {
            return repository.save(
                    EmployeeInformation
                            .builder()
                            .id(employeeInformationDto.getId())
                            .post(employeeInformationDto.getPost())
                            .userInformation(userInformationService.save(employeeInformationDto.getUserInformationSavingDto()))
                            .hospital(hospital)
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(employeeInformationDto), e);
        }

    }

    @Override
    public void checkDataValidityForSaving(EmployeeInformationSavingDto employeeInformation, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
