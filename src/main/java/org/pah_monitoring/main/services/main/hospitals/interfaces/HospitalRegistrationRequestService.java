package org.pah_monitoring.main.services.main.hospitals.interfaces;

import org.pah_monitoring.main.dto.in.hospitals.HospitalRegistrationRequestAddingDto;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.deletion.DataDeletionValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

public interface HospitalRegistrationRequestService extends DataAddingValidationService<HospitalRegistrationRequestAddingDto>,
        DataDeletionValidationService<HospitalRegistrationRequest>, UrlValidationService {

    boolean existsByEmail(String email);

    HospitalRegistrationRequest findByEmail(String email) throws DataSearchingServiceException;

    HospitalRegistrationRequest findById(Integer id) throws DataSearchingServiceException;

    HospitalRegistrationRequest add(HospitalRegistrationRequestAddingDto addingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataSearchingServiceException, DataDeletionServiceException;

}
