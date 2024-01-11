package org.pah_monitoring.main.exceptions.rest.bad_request;

import org.pah_monitoring.main.exceptions.rest.bad_request.common.BadRequestRestException;

public class DataValidationRestException extends BadRequestRestException {

    public DataValidationRestException() {
        super();
    }

    public DataValidationRestException(String msg) {
        super(msg);
    }

    public DataValidationRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
