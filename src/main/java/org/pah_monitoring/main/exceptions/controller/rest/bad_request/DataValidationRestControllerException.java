package org.pah_monitoring.main.exceptions.controller.rest.bad_request;

import org.pah_monitoring.main.exceptions.controller.rest.bad_request.common.BadRequestRestControllerException;

public class DataValidationRestControllerException extends BadRequestRestControllerException {

    public DataValidationRestControllerException() {
        super();
    }

    public DataValidationRestControllerException(String msg) {
        super(msg);
    }

    public DataValidationRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
