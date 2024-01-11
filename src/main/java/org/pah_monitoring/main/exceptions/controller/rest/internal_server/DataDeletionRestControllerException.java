package org.pah_monitoring.main.exceptions.controller.rest.internal_server;

import org.pah_monitoring.main.exceptions.controller.rest.internal_server.common.InternalServerErrorRestControllerException;

public class DataDeletionRestControllerException extends InternalServerErrorRestControllerException {

    public DataDeletionRestControllerException() {
        super();
    }

    public DataDeletionRestControllerException(String msg) {
        super(msg);
    }

    public DataDeletionRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
