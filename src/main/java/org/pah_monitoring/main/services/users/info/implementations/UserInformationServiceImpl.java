package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserInformationEditingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class UserInformationServiceImpl implements UserInformationService {

    private UserInformationRepository repository;

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
    public UserInformation edit(UserInformationEditingDto editingDto)
            throws DataSearchingServiceException, DataSavingServiceException {

        UserInformation userInformation = findById(editingDto.getId());
        try {
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
    public void checkDataValidityForEditing(UserInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataSearchingServiceException, DataValidationServiceException {

        // todo: later

    }

    @Override
    public void checkDataValidityForSaving(UserInformationAddingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
