package org.pah_monitoring.main.services.main.users.info.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.dto.in.users.info.security.UserSecurityInformationAddingDto;
import org.pah_monitoring.main.dto.in.users.info.security.UserSecurityInformationEditingDto;
import org.pah_monitoring.main.dto.in.users.info.security.UserSecurityInformationSavingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.entities.main.users.info.UserSecurityInformation;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalEmployee;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.HospitalUser;
import org.pah_monitoring.main.entities.main.users.users.common.interfaces.User;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.users.info.UserSecurityInformationRepository;
import org.pah_monitoring.main.services.additional.users.interfaces.CurrentUserCheckService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
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

    private CurrentUserCheckService checkService;

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public UserSecurityInformation findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Логин-информации с id \"%s\" не существует".formatted(id))
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
                            .password(
                                    editingDto.getPassword() != null
                                            ? passwordEncoder.encode(editingDto.getPassword())
                                            : securityInformation.getPassword()
                            )
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

    }

    @Override
    public void checkDataValidityForEditing(UserSecurityInformationEditingDto editingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        checkDataValidityForSaving(editingDto, bindingResult);

        if (editingDto.getPassword() != null) {
            if (editingDto.getPassword().length() < 3 || editingDto.getPassword().length() > 63) {
                throw new DataValidationServiceException("Минимальная длина пароля - 3 символа, максимальная - 63 символа");
            }
        }

        Optional<UserSecurityInformation> securityInformation = repository.findByEmail(editingDto.getEmail());
        if (securityInformation.isPresent() && !securityInformation.get().getId().equals(editingDto.getId())) {
            throw new DataValidationServiceException("Пользователь с почтой \"%s\" уже зарегистрирован".formatted(editingDto.getEmail()));
        }

        if (codeService.existsByEmail(editingDto.getEmail())) {
            throw new DataValidationServiceException("Пользователю с почтой \"%s\" уже выдан код".formatted(editingDto.getEmail()));
        }

        try {
            HospitalRegistrationRequest request = requestService.findByEmail(editingDto.getEmail());
            if (request.getHospital().getCurrentState() != Hospital.CurrentState.REGISTERED) {
                throw new DataValidationServiceException(
                        "Почта \"%s\" указана в заявке на регистрацию медицинского учреждения".formatted(editingDto.getEmail())
                );
            }
        } catch (DataSearchingServiceException ignored) {}

    }

    @Override
    public void checkDataValidityForSaving(UserSecurityInformationSavingDto savingDto, BindingResult bindingResult)
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
