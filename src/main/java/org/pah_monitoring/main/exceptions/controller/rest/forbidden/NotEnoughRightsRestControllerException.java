package org.pah_monitoring.main.exceptions.controller.rest.forbidden;

import org.pah_monitoring.main.exceptions.controller.rest.common.RestControllerException;

public class NotEnoughRightsRestControllerException extends RestControllerException {

    public NotEnoughRightsRestControllerException() {
        super();
    }

    public NotEnoughRightsRestControllerException(String msg) {
        super(msg);
    }

    public NotEnoughRightsRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
