package org.pah_monitoring.main.exceptions.controller.rest.bad_request.common;

import org.pah_monitoring.main.exceptions.controller.rest.common.RestControllerException;

public class BadRequestRestControllerException extends RestControllerException {

    public BadRequestRestControllerException() {
        super();
    }

    public BadRequestRestControllerException(String msg) {
        super(msg);
    }

    public BadRequestRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
