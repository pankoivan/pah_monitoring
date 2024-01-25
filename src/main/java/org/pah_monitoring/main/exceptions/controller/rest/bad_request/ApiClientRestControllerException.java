package org.pah_monitoring.main.exceptions.controller.rest.bad_request;

import org.pah_monitoring.main.exceptions.controller.rest.bad_request.common.BadRequestRestControllerException;

public class ApiClientRestControllerException extends BadRequestRestControllerException {

    public ApiClientRestControllerException() {
        super();
    }

    public ApiClientRestControllerException(String msg) {
        super(msg);
    }

    public ApiClientRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
