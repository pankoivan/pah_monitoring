package org.pah_monitoring.main.exceptions.controller.rest.internal_server;

import org.pah_monitoring.main.exceptions.controller.rest.internal_server.common.InternalServerErrorRestControllerException;

public class DataSavingRestControllerException extends InternalServerErrorRestControllerException {

    public DataSavingRestControllerException() {
        super();
    }

    public DataSavingRestControllerException(String msg) {
        super(msg);
    }

    public DataSavingRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
