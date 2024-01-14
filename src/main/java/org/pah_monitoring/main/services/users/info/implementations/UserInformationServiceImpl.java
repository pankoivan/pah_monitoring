package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.UserInformationSavingDto;
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
    public UserInformation save(UserInformationSavingDto userInformationDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    UserInformation
                            .builder()
                            .id(userInformationDto.getId())
                            .name(userInformationDto.getName())
                            .lastname(userInformationDto.getLastname())
                            .patronymic(userInformationDto.getPatronymic())
                            .gender(userInformationDto.getGender())
                            .birthdate(userInformationDto.getBirthdate())
                            .phoneNumber(PhoneNumberUtils.readable(userInformationDto.getPhoneNumber()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(userInformationDto));
        }
    }

    @Override
    public void checkDataValidityForSaving(UserInformationSavingDto userInformationSavingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
