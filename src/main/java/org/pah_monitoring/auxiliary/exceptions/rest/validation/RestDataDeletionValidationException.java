package org.pah_monitoring.auxiliary.exceptions.rest.validation;

import org.pah_monitoring.auxiliary.exceptions.rest.validation.common.RestDataValidationException;

public class RestDataDeletionValidationException extends RestDataValidationException {

    public RestDataDeletionValidationException() {
        super();
    }

    public RestDataDeletionValidationException(String msg) {
        super(msg);
    }

    public RestDataDeletionValidationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
