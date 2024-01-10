package org.pah_monitoring.auxiliary.exceptions.rest;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class DataDeletionRestException extends RestException {

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
