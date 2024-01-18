package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
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
    public EmployeeInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Логин-информация с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public EmployeeInformation add(EmployeeInformationAddingDto savingDto) throws DataSavingServiceException {

        try {
            return repository.save(
                    EmployeeInformation
                            .builder()
                            .post(savingDto.getPost())
                            .userInformation(userInformationService.add(savingDto.getUserInformationAddingDto()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public EmployeeInformation edit(EmployeeInformationEditingDto editingDto)
            throws DataSearchingServiceException, DataSavingServiceException {

        EmployeeInformation employeeInformation = findById(editingDto.getId());
        try {
            return repository.save(
                    EmployeeInformation
                            .builder()
                            .id(employeeInformation.getId())
                            .post(editingDto.getPost())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }

    }

    @Override
    public void checkDataValidityForEditing(EmployeeInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataSearchingServiceException, DataValidationServiceException {

        // todo: later

    }

    @Override
    public void checkDataValidityForSaving(EmployeeInformationAddingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        userInformationService.checkDataValidityForSaving(savingDto.getUserInformationAddingDto(), bindingResult);

    }

}
