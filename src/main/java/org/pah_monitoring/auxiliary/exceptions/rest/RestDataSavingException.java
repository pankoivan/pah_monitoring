package org.pah_monitoring.auxiliary.exceptions.rest;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class RestDataSavingException extends RestException {

    public RestDataSavingException() {
        super();
    }

    public RestDataSavingException(String msg) {
        super(msg);
    }

    public RestDataSavingException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
