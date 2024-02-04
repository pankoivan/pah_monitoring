package org.pah_monitoring.main.services.main.hospitals.interfaces;

import org.pah_monitoring.main.dto.in.hospitals.HospitalAddingDto;
import org.pah_monitoring.main.entities.main.hospitals.Hospital;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataValidationServiceException;
import org.pah_monitoring.main.filtration.filters.common.EntityFilter;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

import java.util.List;
import java.util.Map;

public interface HospitalService extends DataAddingValidationService<HospitalAddingDto>, UrlValidationService {

    List<Hospital> findAll();

    List<Hospital> findAll(Map<String, String> parameters, EntityFilter.PageStat pageStat);

    Hospital findById(Integer id) throws DataSearchingServiceException;

    Hospital add(HospitalAddingDto addingDto) throws DataSavingServiceException;

    void upgrade(Hospital hospital);

    void checkHospitalCurrentState(Hospital requestedHospital) throws DataValidationServiceException;

    void checkAccessRightsForObtainingConcrete(Hospital requestedHospital) throws NotEnoughRightsServiceException;

}
