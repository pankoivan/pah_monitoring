package org.pah_monitoring.main.services.main.hospitals.interfaces;

import org.pah_monitoring.main.entities.additional.dto.saving.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.services.additional.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.additional.validation.interfaces.url.UrlValidationService;

import java.util.List;

public interface HospitalService extends DataAddingValidationService<HospitalAddingDto>, UrlValidationService {

    List<Hospital> findAll();

    Hospital findById(Integer id) throws DataSearchingServiceException;

    Hospital add(HospitalAddingDto addingDto) throws DataSavingServiceException;

    void upgrade(Hospital hospital);

    void checkHospitalCurrentState(Hospital requestedHospital) throws DataValidationServiceException;

    void checkAccessRightsForObtainingConcrete(Hospital requestedHospital) throws NotEnoughRightsServiceException;

}
