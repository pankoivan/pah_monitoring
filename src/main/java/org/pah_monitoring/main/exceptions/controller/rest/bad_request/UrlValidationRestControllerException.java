package org.pah_monitoring.main.exceptions.controller.rest.bad_request;

import org.pah_monitoring.main.exceptions.controller.rest.bad_request.common.BadRequestRestControllerException;

public class UrlValidationRestControllerException extends BadRequestRestControllerException {

    public UrlValidationRestControllerException() {
        super();
    }

    public UrlValidationRestControllerException(String msg) {
        super(msg);
    }

    public UrlValidationRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
