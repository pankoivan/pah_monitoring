package org.pah_monitoring.main.exceptions.controller.mvc.common;

import org.pah_monitoring.main.exceptions.controller.common.ControllerException;

public class MvcControllerException extends ControllerException {

    public MvcControllerException() {
        super();
    }

    public MvcControllerException(String msg) {
        super(msg);
    }

    public MvcControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
