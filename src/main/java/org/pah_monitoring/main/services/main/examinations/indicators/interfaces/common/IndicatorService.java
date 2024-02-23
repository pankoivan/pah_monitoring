package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSavingServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.data.saving.DataAddingValidationService;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

public interface IndicatorService<T, M> extends DataAddingValidationService<M>, UrlValidationService {

    T findById(Integer id) throws DataSearchingServiceException;

    T add(M addingDto) throws DataSavingServiceException;

    void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException;

}
