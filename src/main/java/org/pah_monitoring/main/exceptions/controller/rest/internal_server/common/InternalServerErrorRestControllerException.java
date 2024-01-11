package org.pah_monitoring.main.exceptions.controller.rest.internal_server.common;

import org.pah_monitoring.main.exceptions.controller.rest.common.RestControllerException;

public class InternalServerErrorRestControllerException extends RestControllerException {

    public InternalServerErrorRestControllerException() {
        super();
    }

    public InternalServerErrorRestControllerException(String msg) {
        super(msg);
    }

    public InternalServerErrorRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
