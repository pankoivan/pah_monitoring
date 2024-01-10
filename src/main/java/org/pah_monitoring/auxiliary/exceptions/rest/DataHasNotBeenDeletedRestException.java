package org.pah_monitoring.auxiliary.exceptions.rest;

public class DataHasNotBeenDeletedRestException extends DataDeletionRestException {

    public DataHasNotBeenDeletedRestException() {
        super();
    }

    public DataHasNotBeenDeletedRestException(String msg) {
        super(msg);
    }

    public DataHasNotBeenDeletedRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
