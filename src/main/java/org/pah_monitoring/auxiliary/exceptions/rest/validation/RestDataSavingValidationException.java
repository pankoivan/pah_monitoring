package org.pah_monitoring.auxiliary.exceptions.rest.validation;

import org.pah_monitoring.auxiliary.exceptions.rest.validation.common.RestDataValidationException;

public class RestDataSavingValidationException extends RestDataValidationException {

    public RestDataSavingValidationException() {
        super();
    }

    public RestDataSavingValidationException(String msg) {
        super(msg);
    }

    public RestDataSavingValidationException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
