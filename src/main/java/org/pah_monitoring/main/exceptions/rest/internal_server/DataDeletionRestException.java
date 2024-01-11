package org.pah_monitoring.main.exceptions.rest.internal_server;

import org.pah_monitoring.main.exceptions.rest.internal_server.common.InternalServerProblemRestException;

public class DataDeletionRestException extends InternalServerProblemRestException {

    public DataDeletionRestException() {
        super();
    }

    public DataDeletionRestException(String msg) {
        super(msg);
    }

    public DataDeletionRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
