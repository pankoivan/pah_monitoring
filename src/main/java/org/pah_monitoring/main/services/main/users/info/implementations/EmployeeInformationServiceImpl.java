package org.pah_monitoring.main.services.main.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.main.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private final EmployeeInformationRepository repository;

    private UserInformationService userInformationService;

    private CurrentUserCheckService checkService;

    @Override
    public EmployeeInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Рабочая информация с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public EmployeeInformation add(EmployeeInformationAddingDto addingDto) throws DataSavingServiceException {

        try {
            return repository.save(
                    EmployeeInformation
                            .builder()
                            .post(addingDto.getPost())
                            .userInformation(userInformationService.add(addingDto.getUserInformationAddingDto()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }

    }

    @Override
    public EmployeeInformation edit(EmployeeInformationEditingDto editingDto) throws DataSavingServiceException {

        try {
            EmployeeInformation employeeInformation = findById(editingDto.getId());
            return repository.save(
                    EmployeeInformation
                            .builder()
                            .id(employeeInformation.getId())
                            .post(editingDto.getPost())
                            .userInformation(employeeInformation.getUserInformation())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }

    }

    @Override
    public void checkDataValidityForAdding(EmployeeInformationAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(addingDto, bindingResult);

        userInformationService.checkDataValidityForAdding(addingDto.getUserInformationAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(EmployeeInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForSaving(EmployeeInformationSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

    @Override
    public void checkAccessRightsForEditing(User userWithRequestedEditingInfo) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSameUser(userWithRequestedEditingInfo) ||
                checkService.isAdministratorFromSameHospital(((HospitalUser) userWithRequestedEditingInfo).getHospital())

        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
