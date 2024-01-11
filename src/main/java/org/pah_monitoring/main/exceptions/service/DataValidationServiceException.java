package org.pah_monitoring.main.exceptions.service;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class DataValidationServiceException extends ServiceException {

    public DataValidationServiceException() {
        super();
    }

    public DataValidationServiceException(String msg) {
        super(msg);
    }

    public DataValidationServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
