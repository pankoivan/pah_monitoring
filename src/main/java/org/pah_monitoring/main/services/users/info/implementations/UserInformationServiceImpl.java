package org.pah_monitoring.main.services.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.UserInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository repository;

    private AccessRightsCheckService checkService;

    @Override
    public UserInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Общая информация с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public UserInformation add(UserInformationAddingDto addingDto) throws DataSavingServiceException {

        try {
            return repository.save(
                    UserInformation
                            .builder()
                            .name(addingDto.getName())
                            .lastname(addingDto.getLastname())
                            .patronymic(addingDto.getPatronymic())
                            .gender(addingDto.getGender())
                            .birthdate(addingDto.getBirthdate())
                            .phoneNumber(PhoneNumberUtils.readable(addingDto.getPhoneNumber()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }

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
