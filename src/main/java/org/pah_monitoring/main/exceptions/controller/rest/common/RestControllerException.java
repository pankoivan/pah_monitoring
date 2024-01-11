package org.pah_monitoring.main.exceptions.controller.rest.common;

import org.pah_monitoring.main.exceptions.controller.common.ControllerException;

public class RestControllerException extends ControllerException {

    public RestControllerException() {
        super();
    }

    public RestControllerException(String msg) {
        super(msg);
    }

    public RestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
