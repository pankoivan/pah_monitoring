package org.pah_monitoring.main.exceptions.controller.rest.bad_request;

import org.pah_monitoring.main.exceptions.controller.rest.bad_request.common.BadRequestRestControllerException;

public class RestClientRestControllerException extends BadRequestRestControllerException {

    public RestClientRestControllerException() {
        super();
    }

    public RestClientRestControllerException(String msg) {
        super(msg);
    }

    public RestClientRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
