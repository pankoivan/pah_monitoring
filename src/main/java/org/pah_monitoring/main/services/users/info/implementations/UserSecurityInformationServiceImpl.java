package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserSecurityInformationRepository;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class UserSecurityInformationServiceImpl implements UserSecurityInformationService {

    private final UserSecurityInformationRepository repository;

    private final PasswordEncoder passwordEncoder;

    @Override
    public UserSecurityInformation save(UserSecurityInformationSavingDto securityInformationDto) {
        try {
            return repository.save(
                    UserSecurityInformation
                            .builder()
                            .id(securityInformationDto.getId())
                            .email(securityInformationDto.getEmail())
                            .password(passwordEncoder.encode(securityInformationDto.getPassword()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(securityInformationDto));
        }
    }

    @Override
    public void checkDataValidityForSaving(UserSecurityInformationSavingDto userSecurityInformationSavingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByEmail(userSecurityInformationSavingDto.getEmail())) {
            throw new DataValidationServiceException("Почта \"%s\" уже занята".formatted(userSecurityInformationSavingDto.getEmail()));
        }

    }

}
