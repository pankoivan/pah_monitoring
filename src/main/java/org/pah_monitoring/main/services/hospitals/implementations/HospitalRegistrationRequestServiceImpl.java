package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.HospitalRegistrationRequestSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
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
    public HospitalRegistrationRequest save(HospitalRegistrationRequestSavingDto requestDto) throws DataSavingServiceException {
        try {
            Hospital hospital = hospitalService.save(requestDto.getHospitalDto());
            return repository.save(
                    HospitalRegistrationRequest
                            .builder()
                            .name(requestDto.getName())
                            .lastname(requestDto.getLastname())
                            .patronymic(requestDto.getPatronymic())
                            .post(requestDto.getPost())
                            .passport(requestDto.getPassport())
                            .phoneNumber(requestDto.getPhoneNumber())
                            .email(requestDto.getEmail())
                            .comment(requestDto.getComment())
                            .hospital(hospital)
                            .date(hospital.getDate())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(requestDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {

    }

    @Override
    public void checkDataValidityForSaving(HospitalRegistrationRequestSavingDto requestDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByPassport(requestDto.getPassport())) {
            throw new DataValidationServiceException("Человек с паспортными данными \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                    .formatted(requestDto.getPassport()));
        }
        if (repository.existsByPhoneNumber(requestDto.getPhoneNumber())) {
            throw new DataValidationServiceException("Человек с номером телефона \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                    .formatted(requestDto.getPhoneNumber()));
        }

        hospitalService.checkDataValidityForSaving(requestDto.getHospitalDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForDeleting(HospitalRegistrationRequest request) throws DataValidationServiceException {

    }

    @Override
    public int parsePathId(String pathId) throws UrlValidationServiceException {
        return 0;
    }

}
