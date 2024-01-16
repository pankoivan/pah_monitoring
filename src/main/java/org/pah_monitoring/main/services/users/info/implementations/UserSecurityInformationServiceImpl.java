package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserSecurityInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class UserSecurityInformationServiceImpl implements UserSecurityInformationService {

    private UserSecurityInformationRepository repository;

    private PasswordEncoder passwordEncoder;

    @Override
    public UserSecurityInformation save(UserSecurityInformationSavingDto savingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    UserSecurityInformation
                            .builder()
                            .id(savingDto.getId())
                            .email(savingDto.getEmail())
                            .password(passwordEncoder.encode(savingDto.getPassword()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(UserSecurityInformationSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByEmail(savingDto.getEmail())) {
            throw new DataValidationServiceException("Почта \"%s\" уже занята".formatted(savingDto.getEmail()));
        }

    }

}
