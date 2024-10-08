package org.pah_monitoring.main.services.main.hospitals.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.dto.in.hospitals.HospitalRegistrationRequestAddingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRegistrationRequestRepository;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.main.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.main.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.main.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Service
public class HospitalRegistrationRequestServiceImpl implements HospitalRegistrationRequestService {

    private final HospitalRegistrationRequestRepository repository;

    private HospitalService hospitalService;

    private UserSecurityInformationService securityInformationService;

    private RegistrationSecurityCodeService securityCodeService;

    @Override
    public boolean existsByEmail(String email) {
        return repository.existsByEmail(email);
    }

    @Override
    public HospitalRegistrationRequest findByEmail(String email) throws DataSearchingServiceException {
        return repository.findByEmail(email).orElseThrow(
                () -> new DataSearchingServiceException("Заявки для почты \"%s\" не существует".formatted(email))
        );
    }

    @Override
    public HospitalRegistrationRequest findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Заявки с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public HospitalRegistrationRequest add(HospitalRegistrationRequestAddingDto addingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    HospitalRegistrationRequest
                            .builder()
                            .name(addingDto.getName())
                            .lastname(addingDto.getLastname())
                            .patronymic(addingDto.getPatronymic())
                            .post(addingDto.getPost())
                            .passport(addingDto.getPassport())
                            .phoneNumber(PhoneNumberUtils.toReadable(addingDto.getPhoneNumber()))
                            .email(addingDto.getEmail())
                            .comment(addingDto.getComment())
                            .date(LocalDateTime.now())
                            .hospital(hospitalService.add(addingDto.getHospitalAddingDto()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(addingDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {
        try {
            repository.deleteById(id);
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с идентификатором \"%s\" не была удалена".formatted(id), e);
        }
    }

    @Override
    public void checkDataValidityForAdding(HospitalRegistrationRequestAddingDto addingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }

        if (repository.existsByPassport(addingDto.getPassport())) {
            throw new DataValidationServiceException(
                    "Человек с паспортными данными \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                            .formatted(addingDto.getPassport())
            );
        }

        if (repository.existsByPhoneNumber(PhoneNumberUtils.toReadable(addingDto.getPhoneNumber()))) {
            throw new DataValidationServiceException(
                    "Человек с номером телефона \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                            .formatted(addingDto.getPhoneNumber())
            );
        }

        if (repository.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Почта \"%s\" уже указана в другой заявке".formatted(addingDto.getEmail())
            );
        }

        if (securityInformationService.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Человек с почтой \"%s\" уже зарегистрирован в приложении".formatted(addingDto.getEmail())
            );
        }

        if (securityCodeService.existsByEmail(addingDto.getEmail())) {
            throw new DataValidationServiceException(
                    "Человеку с почтой \"%s\" уже выдан код".formatted(addingDto.getEmail())
            );
        }

        hospitalService.checkDataValidityForAdding(addingDto.getHospitalAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForDeletion(HospitalRegistrationRequest request) throws DataValidationServiceException {
        if (request.getHospital().getCurrentState() != Hospital.CurrentState.WAITING_CODE) {
            throw new DataValidationServiceException(
                    "Медицинское учреждение \"%s\" не может быть удалено, так как заявка на его регистрацию была подтверждена"
                            .formatted(request.getHospital().getName())
            );
        }
    }

}
