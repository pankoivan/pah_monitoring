package org.pah_monitoring.auxiliary.exceptions.rest;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class RestDataValidationException extends RestException {

    public RestDataValidationException() {
        super();
    }

    public RestDataValidationException(String msg) {
        super(msg);
    }

    public RestDataValidationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
