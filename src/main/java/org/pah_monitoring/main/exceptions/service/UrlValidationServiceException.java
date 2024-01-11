package org.pah_monitoring.main.exceptions.service;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class UrlValidationServiceException extends ServiceException {

    public UrlValidationServiceException() {
        super();
    }

    public UrlValidationServiceException(String msg) {
        super(msg);
    }

    public UrlValidationServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
