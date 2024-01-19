package org.pah_monitoring.main.services.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.users.users.common.User;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserSecurityInformationRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.security.core.context.SecurityContextHolder;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.util.Optional;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class UserSecurityInformationServiceImpl implements UserSecurityInformationService {

    private final UserSecurityInformationRepository repository;

    private final PasswordEncoder passwordEncoder;

    private RegistrationSecurityCodeService codeService;

    private HospitalRegistrationRequestService requestService;

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
    public void checkAccessForEditing(UserSecurityInformation requestedEditingInfo) throws NotEnoughRightsServiceException {

        Object principal = SecurityContextHolder.getContext().getAuthentication().getPrincipal();

        if (
                (principal instanceof User user) && (user.getUserSecurityInformation().getId().equals(requestedEditingInfo.getId()))
            // todo: allow admin to edit user-info in same hospital
        ) {
            return;
        }

        throw new NotEnoughRightsServiceException(
                "Недостаточно прав для редактирования логин-информации с id \"%s\"".formatted(requestedEditingInfo.getId())
        );

    }

    @Override
    public UserSecurityInformation edit(UserSecurityInformationEditingDto editingDto) throws DataSavingServiceException {

        try {
            UserSecurityInformation securityInformation = findById(editingDto.getId());
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
    public void checkDataValidityForAdding(UserSecurityInformationAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(addingDto, bindingResult);

        if (repository.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException("Почта \"%s\" уже занята".formatted(addingDto.getEmail()));
        }

    }

    @Override
    public void checkDataValidityForEditing(UserSecurityInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

        Optional<UserSecurityInformation> securityInformation = repository.findByEmail(editingDto.getEmail());
        if (securityInformation.isPresent() && !securityInformation.get().getId().equals(editingDto.getId())) {
            throw new DataValidationServiceException("Почта \"%s\" уже занята".formatted(editingDto.getEmail()));
        }

        if (codeService.existsByEmail(editingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Нельзя сменить почту на \"%s\", так как для этой почты был сгенерирован код для другого человека"
                            .formatted(editingDto.getEmail()));
        }

        if (requestService.existsByEmail(editingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Нельзя сменить почту на \"%s\", так как она указана в заявке на регистрацию медицинского учреждения"
                            .formatted(editingDto.getEmail())
            );
        }

    }

    @Override
    public void checkDataValidityForSaving(UserSecurityInformationSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

    }

}
