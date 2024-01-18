package org.pah_monitoring.main.exceptions.controller.mvc;

import org.pah_monitoring.main.exceptions.controller.mvc.common.MvcControllerException;

public class NotEnoughRightsMvcControllerException extends MvcControllerException {

    public NotEnoughRightsMvcControllerException() {
        super();
    }

    public NotEnoughRightsMvcControllerException(String msg) {
        super(msg);
    }

    public NotEnoughRightsMvcControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
