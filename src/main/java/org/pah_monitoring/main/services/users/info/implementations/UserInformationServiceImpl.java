package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserInformationService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class UserInformationServiceImpl implements UserInformationService {

    private final UserInformationRepository repository;

    @Override
    public UserInformation save(UserInformationSavingDto savingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    UserInformation
                            .builder()
                            .id(savingDto.getId())
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
    public void checkDataValidityForSaving(UserInformationSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
