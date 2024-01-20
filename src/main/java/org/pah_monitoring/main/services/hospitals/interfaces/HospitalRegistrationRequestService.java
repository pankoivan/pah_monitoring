package org.pah_monitoring.main.services.hospitals.interfaces;

import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalRegistrationRequestAddingDto;
import org.pah_monitoring.main.entities.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.DataDeletionValidationService;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.url.UrlValidationService;

public interface HospitalRegistrationRequestService extends DataSavingValidationService<HospitalRegistrationRequestAddingDto>,
        DataDeletionValidationService<HospitalRegistrationRequest>, UrlValidationService {

    boolean existsByEmail(String email);

    HospitalRegistrationRequest findById(Integer id) throws DataSearchingServiceException;

    HospitalRegistrationRequest add(HospitalRegistrationRequestAddingDto addingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataSearchingServiceException, DataDeletionServiceException;

}
