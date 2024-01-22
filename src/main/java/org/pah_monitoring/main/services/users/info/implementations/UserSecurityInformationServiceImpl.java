package org.pah_monitoring.main.services.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.entities.dto.saving.users.info.adding.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.editing.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.entities.dto.saving.users.info.saving.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserSecurityInformationRepository;
import org.pah_monitoring.main.services.auxiliary.access.interfaces.AccessRightsCheckService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
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

    private AccessRightsCheckService checkService;

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
    public UserSecurityInformation add(UserSecurityInformationAddingDto addingDto) throws DataSavingServiceException {

        try {
            return repository.save(
                    UserSecurityInformation
                            .builder()
                            .email(addingDto.getEmail())
                            .password(passwordEncoder.encode(addingDto.getPassword()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }

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
