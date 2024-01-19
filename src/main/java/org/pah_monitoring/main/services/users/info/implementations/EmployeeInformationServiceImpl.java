package org.pah_monitoring.main.services.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.EmployeeInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.EmployeeInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.EmployeeInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.EmployeeInformation;
import org.pah_monitoring.main.entities.users.users.common.User;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.users.info.EmployeeInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.EmployeeInformationService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class EmployeeInformationServiceImpl implements EmployeeInformationService {

    private final EmployeeInformationRepository repository;

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
    public void checkAccessForEditing(EmployeeInformation requestedEditingInfo) throws NotEnoughRightsServiceException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (
                (principal instanceof User user) && (user.getUserSecurityInformation().getId().equals(requestedEditingInfo.getId()))
            // todo: allow admin to edit user-info in same hospital
        ) {
            return;
        }

        throw new NotEnoughRightsServiceException(
                "Недостаточно прав для редактирования рабочей информации с id \"%s\"".formatted(requestedEditingInfo.getId())
        );

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

}
