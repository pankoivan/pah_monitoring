package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.utils.PhoneNumberUtils;
import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalRegistrationRequestSavingDto;
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

@AllArgsConstructor
@Service
public class HospitalRegistrationRequestServiceImpl implements HospitalRegistrationRequestService {

    private final HospitalRegistrationRequestRepository repository;

    private final HospitalService hospitalService;

    @Override
    public HospitalRegistrationRequest save(HospitalRegistrationRequestSavingDto savingDto) throws DataSavingServiceException {
        try {
            Hospital hospital = hospitalService.save(savingDto.getHospitalSavingDto());
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
                            .hospital(hospital)
                            .date(hospital.getDate())
                            .build()
            );
        } catch (Exception e) {
            throw new DataSavingServiceException("DTO-сущность \"%s\" не была сохранена".formatted(savingDto), e);
        }
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {

    }

    @Override
    public void checkDataValidityForSaving(HospitalRegistrationRequestSavingDto savingDto, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByPassport(savingDto.getPassport())) {
            throw new DataValidationServiceException("Человек с паспортными данными \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                    .formatted(savingDto.getPassport()));
        }
        if (repository.existsByPhoneNumber(savingDto.getPhoneNumber())) {
            throw new DataValidationServiceException("Человек с номером телефона \"%s\" уже подавал заявку на регистрацию медицинского учреждения"
                    .formatted(savingDto.getPhoneNumber()));
        }

        hospitalService.checkDataValidityForSaving(savingDto.getHospitalSavingDto(), bindingResult);

    }

    @Override
    public void checkDataValidityForDeleting(HospitalRegistrationRequest request) throws DataValidationServiceException {

    }

    @Override
    public int parsePathId(String pathId) throws UrlValidationServiceException {
        return 0;
    }

}
