package org.pah_monitoring.auxiliary.exceptions.controller.common;

import org.pah_monitoring.auxiliary.exceptions.common.UncheckedException;

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
