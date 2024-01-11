package org.pah_monitoring.main.exceptions.controller;

import org.pah_monitoring.main.exceptions.controller.common.ControllerException;

public class IncorrectUrlControllerException extends ControllerException {

    public IncorrectUrlControllerException() {
        super();
    }

    public IncorrectUrlControllerException(String msg) {
        super(msg);
    }

    public IncorrectUrlControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
