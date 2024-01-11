package org.pah_monitoring.main.exceptions.controller.mvc;

import org.pah_monitoring.main.exceptions.controller.common.ControllerException;

public class UrlValidationMvcControllerException extends ControllerException {

    public UrlValidationMvcControllerException() {
        super();
    }

    public UrlValidationMvcControllerException(String msg) {
        super(msg);
    }

    public UrlValidationMvcControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
