package org.pah_monitoring.main.services.hospitals.interfaces;

import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.services.validation.interfaces.SavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.UrlValidationService;

import java.util.List;

public interface HospitalService extends SavingValidationService<HospitalAddingDto>, UrlValidationService {

    List<Hospital> findAll();

    Hospital findById(Integer id) throws DataSearchingServiceException;

    Hospital findByIdWithCurrentStateCheck(Integer id) throws DataSearchingServiceException, DataValidationServiceException;

    Hospital add(HospitalAddingDto addingDto) throws DataSavingServiceException;

    void upgrade(Hospital hospital);

}
