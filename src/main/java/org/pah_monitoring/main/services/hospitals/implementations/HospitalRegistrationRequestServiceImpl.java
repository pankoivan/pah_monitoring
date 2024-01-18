package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalRegistrationRequestAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.utils.PhoneNumberUtilsException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRegistrationRequestRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.pah_monitoring.main.services.security_codes.interfaces.RegistrationSecurityCodeService;
import org.pah_monitoring.main.services.users.info.interfaces.UserSecurityInformationService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter(onMethod = @__(@Autowired))
@Service
public class HospitalRegistrationRequestServiceImpl implements HospitalRegistrationRequestService {

    private HospitalRegistrationRequestRepository repository;

    private HospitalService hospitalService;

    private UserSecurityInformationService securityInformationService;

    private RegistrationSecurityCodeService securityCodeService;

    @Override
    public HospitalRegistrationRequest findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Заявка с id \"%s\" не существует".formatted(id))
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
                            .phoneNumber(PhoneNumberUtils.readable(addingDto.getPhoneNumber()))
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
    public void deleteById(Integer id) throws DataSearchingServiceException, DataDeletionServiceException {
        HospitalRegistrationRequest request = findById(id);
        try {
            repository.deleteById(request.getId());
        } catch (Exception e) {
            throw new DataDeletionServiceException("Сущность с идентификатором \"%s\" не была удалена".formatted(id), e);
        }
    }

    @Override
    public void checkDataValidityForSaving(HospitalRegistrationRequestAddingDto addingDto, BindingResult bindingResult)
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
        try {
            if (repository.existsByPhoneNumber(PhoneNumberUtils.readable(addingDto.getPhoneNumber()))) {
                throw new DataValidationServiceException(
                        "Человек с номером телефона \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                                .formatted(addingDto.getPhoneNumber())
                );
            }
        } catch (PhoneNumberUtilsException e) {
            throw new DataValidationServiceException(e.getMessage(), e);
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

        hospitalService.checkDataValidityForSaving(addingDto.getHospitalAddingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForDeleting(HospitalRegistrationRequest request) throws DataValidationServiceException {
        if (request.getHospital().getCurrentState() != Hospital.CurrentState.WAITING_CODE) {
            throw new DataValidationServiceException(
                    "Медицинское учреждение \"%s\" не может быть удалено, так как заявка на его регистрацию была подтверждена"
                            .formatted(request.getHospital().getName())
            );
        }
    }

}
