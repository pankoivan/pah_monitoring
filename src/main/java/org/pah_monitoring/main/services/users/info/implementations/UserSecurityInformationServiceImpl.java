package org.pah_monitoring.main.services.users.info.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
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
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserSecurityInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Логин-информация с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public UserSecurityInformation add(UserSecurityInformationAddingDto savingDto) throws DataSavingServiceException {

        try {
            return repository.save(
                    UserSecurityInformation
                            .builder()
                            .email(savingDto.getEmail())
                            .password(passwordEncoder.encode(savingDto.getPassword()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }

    }

    @Override
    public UserSecurityInformation edit(UserSecurityInformationEditingDto editingDto)
            throws DataSearchingServiceException, DataSavingServiceException {

        UserSecurityInformation securityInformation = findById(editingDto.getId());
        try {
            return repository.save(
                    UserSecurityInformation
                            .builder()
                            .id(securityInformation.getId())
                            .email(editingDto.getEmail())
                            .password(passwordEncoder.encode(editingDto.getPassword()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(editingDto), e);
        }

    }

    @Override
    public void checkDataValidityForEditing(UserSecurityInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataSearchingServiceException, DataValidationServiceException {

        // todo: later

    }

    @Override
    public void checkDataValidityForSaving(UserSecurityInformationAddingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByEmail(savingDto.getEmail())) {
            throw new DataValidationServiceException("Почта \"%s\" уже занята".formatted(savingDto.getEmail()));
        }

    }

}
