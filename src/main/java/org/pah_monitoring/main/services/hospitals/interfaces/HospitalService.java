package org.pah_monitoring.main.services.hospitals.interfaces;

import org.pah_monitoring.main.entities.dto.saving.HospitalSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataDeletionServiceException;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.DeletionValidationService;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.UrlValidationService;

public interface HospitalService extends SavingValidationService<HospitalSavingDto>, DeletionValidationService<Hospital>,
        UrlValidationService {

    Hospital save(HospitalSavingDto hospital) throws DataSavingServiceException;

    void deleteById(Integer id) throws DataDeletionServiceException;

}
