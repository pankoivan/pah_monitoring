package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private EmployeeInformationRepository repository;

    private UserInformationService userInformationService;

    @Override
    public EmployeeInformation save(EmployeeInformationSavingDto savingDto, Hospital hospital) throws DataSavingServiceException {
        try {
            return repository.save(
                    EmployeeInformation
                            .builder()
                            .id(savingDto.getId())
                            .post(savingDto.getPost())
                            .hospital(hospital)
                            .userInformation(userInformationService.save(savingDto.getUserInformationSavingDto()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(EmployeeInformationSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        userInformationService.checkDataValidityForSaving(savingDto.getUserInformationSavingDto(), bindingResult);

    }

}
