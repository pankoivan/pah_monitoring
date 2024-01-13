package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.main.entities.dto.saving.HospitalRegistrationRequestSavingDto;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;
import org.pah_monitoring.main.repositorites.hospitals.HospitalRegistrationRequestRepository;
import org.pah_monitoring.main.services.hospitals.interfaces.HospitalRegistrationRequestService;
import org.springframework.stereotype.Service;
import org.springframework.validation.BindingResult;

@AllArgsConstructor
@Service
public class HospitalRegistrationRequestServiceImpl implements HospitalRegistrationRequestService {

    private final HospitalRegistrationRequestRepository repository;

    @Override
    public HospitalRegistrationRequest save(HospitalRegistrationRequestSavingDto request) throws DataSavingServiceException {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {

    }

    @Override
    public void checkDataValidityForSaving(HospitalRegistrationRequestSavingDto request, BindingResult bindingResult)
            throws DataValidationServiceException {

        if (bindingResult.hasErrors()) {
            throw new DataValidationServiceException(bindingResultAnyErrorMessage(bindingResult));
        }
        if (repository.existsByPassport(request.getPassport())) {
            throw new DataValidationServiceException("Человек с паспортными данными \"%s\" уже подал заявку на регистрацию медицинского учреждения"
                    .formatted(request.getPassport()));
        }

        if (repository.existsByPhoneNumber(request.getPhoneNumber())) {
            throw new DataValidationServiceException("Человек с номером телефона \"%s\" уже подал заявку на регистрацию медицинского учреждения"
                    .formatted(request.getPhoneNumber()));
        }

    }

    @Override
    public void checkDataValidityForDeleting(HospitalRegistrationRequest request) throws DataValidationServiceException {

    }

    @Override
    public int parsePathId(String pathId) throws UrlValidationServiceException {
        return 0;
    }

}
