package org.pah_monitoring.main.services.hospitals.interfaces;

import org.pah_monitoring.main.entities.dto.saving.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.DataValidationServiceException;
import org.pah_monitoring.main.exceptions.service.NotEnoughRightsServiceException;
import org.pah_monitoring.main.services.validation.interfaces.data.saving.DataSavingValidationService;
import org.pah_monitoring.main.services.validation.interfaces.url.UrlValidationService;

import java.util.List;

public interface HospitalService extends DataSavingValidationService<HospitalAddingDto>, UrlValidationService {

    List<Hospital> findAll();

    Hospital findById(Integer id) throws DataSearchingServiceException;

    Hospital add(HospitalAddingDto addingDto) throws DataSavingServiceException;

    void upgrade(Hospital hospital);

    void checkHospitalCurrentState(Hospital requestedHospital) throws DataValidationServiceException;

    void checkAccessRightsForObtainingAll() throws NotEnoughRightsServiceException;

    void checkAccessRightsForObtainingHospital(Hospital requestedHospital) throws NotEnoughRightsServiceException;

}
