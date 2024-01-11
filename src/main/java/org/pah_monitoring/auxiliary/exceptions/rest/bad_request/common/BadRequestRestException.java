package org.pah_monitoring.auxiliary.exceptions.rest.bad_request.common;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class BadRequestRestException extends RestException {

    public BadRequestRestException() {
        super();
    }

    public BadRequestRestException(String msg) {
        super(msg);
    }

    public BadRequestRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
