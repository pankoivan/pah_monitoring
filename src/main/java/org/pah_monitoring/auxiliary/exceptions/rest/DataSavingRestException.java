package org.pah_monitoring.auxiliary.exceptions.rest;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class DataSavingRestException extends RestException {

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
