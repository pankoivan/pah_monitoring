package org.pah_monitoring.main.exceptions.service;

public class SecurityCodeValidationServiceException extends DataValidationServiceException {

    public SecurityCodeValidationServiceException() {
        super();
    }

    public SecurityCodeValidationServiceException(String msg) {
        super(msg);
    }

    public SecurityCodeValidationServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
