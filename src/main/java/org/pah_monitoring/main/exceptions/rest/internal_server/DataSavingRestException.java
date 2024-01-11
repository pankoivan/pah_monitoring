package org.pah_monitoring.main.exceptions.rest.internal_server;

import org.pah_monitoring.main.exceptions.rest.internal_server.common.InternalServerProblemRestException;

public class DataSavingRestException extends InternalServerProblemRestException {

    public DataSavingRestException() {
        super();
    }

    public DataSavingRestException(String msg) {
        super(msg);
    }

    public DataSavingRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
