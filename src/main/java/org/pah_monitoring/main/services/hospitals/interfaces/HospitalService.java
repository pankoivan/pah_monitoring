package org.pah_monitoring.main.services.hospitals.interfaces;

import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalSavingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.UrlValidationService;

public interface HospitalService extends SavingValidationService<HospitalSavingDto>, UrlValidationService {

    Hospital add(HospitalSavingDto savingDto) throws DataSavingServiceException;

    void codeReceived(Hospital hospital);

    void registered(Hospital hospital);

}
