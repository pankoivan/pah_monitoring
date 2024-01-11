package org.pah_monitoring.main.exceptions.controller.common;

import org.pah_monitoring.main.exceptions.common.UncheckedException;

public class ControllerException extends UncheckedException {

    public ControllerException() {
        super();
    }

    public ControllerException(String msg) {
        super(msg);
    }

    public ControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
