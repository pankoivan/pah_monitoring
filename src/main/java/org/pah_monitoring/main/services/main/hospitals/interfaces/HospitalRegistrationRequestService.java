package org.pah_monitoring.main.services.main.hospitals.interfaces;

import org.pah_monitoring.main.entities.additional.dto.saving.hospitals.HospitalRegistrationRequestAddingDto;
import org.pah_monitoring.main.entities.main.hospitals.HospitalRegistrationRequest;
import org.pah_monitoring.main.exceptions.service.data.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.deletion.DataDeletionValidationService;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.additional.validation.interfaces.url.UrlValidationService;

public interface HospitalRegistrationRequestService extends DataAddingValidationService<HospitalRegistrationRequestAddingDto>,
        DataDeletionValidationService<HospitalRegistrationRequest>, UrlValidationService {

    boolean existsByEmail(String email);

    HospitalRegistrationRequest findById(Integer id) throws DataSearchingServiceException;

    HospitalRegistrationRequest add(HospitalRegistrationRequestAddingDto addingDto) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataSearchingServiceException, DataDeletionServiceException;

}
