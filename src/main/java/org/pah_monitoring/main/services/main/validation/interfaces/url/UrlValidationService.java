package org.pah_monitoring.main.services.main.validation.interfaces.url;

import org.pah_monitoring.main.exceptions.service.url.UrlValidationServiceException;

public interface UrlValidationService {

    default Integer parsePathId(String pathId) throws UrlValidationServiceException {
        int id;
        try {
            id = Integer.parseInt(pathId);
        } catch (NumberFormatException e) {
            throw new UrlValidationServiceException("Некорректный id: \"%s\"".formatted(pathId));
        }
        if (id <= 0) {
            throw new UrlValidationServiceException("Некорректный id: \"%s\"".formatted(pathId));
        }
        return id;
    }

}
