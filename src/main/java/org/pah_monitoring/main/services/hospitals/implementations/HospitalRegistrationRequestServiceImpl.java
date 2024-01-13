package org.pah_monitoring.main.services.hospitals.implementations;

import lombok.AllArgsConstructor;
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
    public HospitalRegistrationRequest save(HospitalRegistrationRequest request) throws DataSavingServiceException {
        return null;
    }

    @Override
    public void deleteById(Integer id) throws DataDeletionServiceException {

    }

    @Override
    public void checkDataValidityForSaving(HospitalRegistrationRequest request, BindingResult bindingResult) throws DataValidationServiceException {

    }

    @Override
    public void checkDataValidityForDeleting(HospitalRegistrationRequest request) throws DataValidationServiceException {

    }

    @Override
    public int parsePathId(String pathId) throws UrlValidationServiceException {
        return 0;
    }

}
