package org.pah_monitoring.main.services.common.interfaces;

import org.pah_monitoring.main.exceptions.service.UrlValidationServiceException;

public interface UrlValidationService {

    int parsePathId(String pathId) throws UrlValidationServiceException;

}
