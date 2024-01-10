package org.pah_monitoring.auxiliary.exceptions.rest;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class RestDataDeletionException extends RestException {

    public RestDataDeletionException() {
        super();
    }

    public RestDataDeletionException(String msg) {
        super(msg);
    }

    public RestDataDeletionException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
