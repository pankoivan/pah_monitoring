package org.pah_monitoring.main.services.main.examinations.indicators.interfaces.common;

import org.pah_monitoring.main.entities.main.users.users.Patient;
import org.pah_monitoring.main.exceptions.service.access.NotEnoughRightsServiceException;
import org.pah_monitoring.main.exceptions.service.data.DataSearchingServiceException;
import org.pah_monitoring.main.services.main.validation.interfaces.url.UrlValidationService;

public interface IndicatorService<T> extends UrlValidationService {

    T findById(Integer id) throws DataSearchingServiceException;

    void checkAccessRightsForObtaining(Patient patient) throws NotEnoughRightsServiceException;

}
