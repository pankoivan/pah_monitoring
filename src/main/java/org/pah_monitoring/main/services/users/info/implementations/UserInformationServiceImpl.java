package org.pah_monitoring.main.services.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.UserInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.users.common.User;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository repository;

    @Override
    public UserInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Общая информация с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public UserInformation add(UserInformationAddingDto savingDto) throws DataSavingServiceException {

        try {
            return repository.save(
                    UserInformation
                            .builder()
                            .name(savingDto.getName())
                            .lastname(savingDto.getLastname())
                            .patronymic(savingDto.getPatronymic())
                            .gender(savingDto.getGender())
                            .birthdate(savingDto.getBirthdate())
                            .phoneNumber(PhoneNumberUtils.readable(savingDto.getPhoneNumber()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public void checkAccessForEditing(UserInformation requestedEditingInfo) throws NotEnoughRightsServiceException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (
                (principal instanceof User user) && (user.getUserSecurityInformation().getId().equals(requestedEditingInfo.getId()))
            // todo: allow admin to edit user-info in same hospital
        ) {
            return;
        }

        throw new NotEnoughRightsServiceException(
                "Недостаточно прав для редактирования общей информации с id \"%s\"".formatted(requestedEditingInfo.getId())
        );

    }

    @Override
    public UserInformation edit(UserInformationEditingDto editingDto) throws DataSavingServiceException {

        try {
            UserInformation userInformation = findById(editingDto.getId());
            return repository.save(
                    UserInformation
                            .builder()
                            .id(userInformation.getId())
                            .name(editingDto.getName())
                            .lastname(editingDto.getLastname())
                            .patronymic(editingDto.getPatronymic())
                            .gender(editingDto.getGender())
                            .birthdate(editingDto.getBirthdate())
                            .phoneNumber(PhoneNumberUtils.readable(editingDto.getPhoneNumber()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }

    }

    @Override
    public void checkDataValidityForAdding(UserInformationAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(addingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForEditing(UserInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

    }

    @Override
    public void checkDataValidityForSaving(UserInformationSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
