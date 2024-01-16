package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalRegistrationRequestSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRegistrationRequestRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

import java.time.LocalDateTime;

@AllArgsConstructor
@Service
public class HospitalRegistrationRequestServiceImpl implements HospitalRegistrationRequestService {

    private final HospitalRegistrationRequestRepository repository;

    private final HospitalService hospitalService;

    @Override
    public HospitalRegistrationRequest findById(Integer id) throws DataSearchingServiceException {
        return repository.findById(id).orElseThrow(
                () -> new DataSearchingServiceException("Заявка с id \"%s\" не существует".formatted(id))
        );
    }

    @Override
    public HospitalRegistrationRequest add(HospitalRegistrationRequestSavingDto savingDto) throws DataSavingServiceException {
        try {
            return repository.save(
                    HospitalRegistrationRequest
                            .builder()
                            .name(savingDto.getName())
                            .lastname(savingDto.getLastname())
                            .patronymic(savingDto.getPatronymic())
                            .post(savingDto.getPost())
                            .passport(savingDto.getPassport())
                            .phoneNumber(PhoneNumberUtils.readable(savingDto.getPhoneNumber()))
                            .email(savingDto.getEmail())
                            .comment(savingDto.getComment())
                            .date(LocalDateTime.now())
                            .hospital(hospitalService.add(savingDto.getHospitalSavingDto()))
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
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
    public void checkDataValidityForSaving(HospitalRegistrationRequestSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByPassport(savingDto.getPassport())) {
            throw new DataValidationServiceException(
                    "Человек с паспортными данными \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                            .formatted(savingDto.getPassport())
            );
        }
        if (repository.existsByPhoneNumber(savingDto.getPhoneNumber())) {
            throw new DataValidationServiceException(
                    "Человек с номером телефона \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                            .formatted(savingDto.getPhoneNumber())
            );
        }

        hospitalService.checkDataValidityForSaving(savingDto.getHospitalSavingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForDeleting(HospitalRegistrationRequest request) throws DataValidationServiceException {
        if (request.getHospital().getCurrentState() != Hospital.CurrentState.WAITING_CODE) {
            throw new DataValidationServiceException(
                    "Медицинское учреждение \"%s\" не может быть удалено, так как заявка на его регистрацию была подтверждена"
            );
        }
    }

}
