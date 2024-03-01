package org.pah_monitoring.main.services.main.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.user.UserInformationSavingDto;
import org.pah_monitoring.main.entities.main.users.info.UserInformation;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository repository;

    private CurrentUserCheckService checkService;

    @Override
    public UserInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Общей информации с id \"%s\" не существует".formatted(id))
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
                            .phoneNumber(PhoneNumberUtils.toReadable(addingDto.getPhoneNumber()))
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
                            .phoneNumber(PhoneNumberUtils.toReadable(editingDto.getPhoneNumber()))
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
    public void checkUserActivity(User user) throws DataValidationServiceException {
        if (user.isHospitalEmployee() && ((HospitalEmployee) user).isDismissed()) {
            throw new DataValidationServiceException("Этот сотрудник уволен, поэтому вы не можете редактировать информацию о нём");
        }
        if (user.isPatient() && user.isNotActive()) {
            throw new DataValidationServiceException("Этот пациент неактивен, поэтому вы не можете редактировать информацию о нём");
        }
    }

    @Override
    public void checkAccessRightsForEditing(User userWithRequestedEditingInfo) throws NotEnoughRightsServiceException {
        if (!(
                checkService.isSelf(userWithRequestedEditingInfo) ||
                checkService.isAdministratorFromSameHospital(((HospitalUser) userWithRequestedEditingInfo).getHospital())
        )) {
            throw new NotEnoughRightsServiceException("Недостаточно прав");
        }
    }

}
