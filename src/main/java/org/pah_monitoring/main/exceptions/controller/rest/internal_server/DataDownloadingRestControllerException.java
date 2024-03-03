package org.pah_monitoring.main.exceptions.controller.rest.internal_server;

import org.pah_monitoring.main.exceptions.controller.rest.internal_server.common.InternalServerErrorRestControllerException;

public class DataDownloadingRestControllerException extends InternalServerErrorRestControllerException {

    public DataDownloadingRestControllerException() {
        super();
    }

    public DataDownloadingRestControllerException(String msg) {
        super(msg);
    }

    public DataDownloadingRestControllerException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
