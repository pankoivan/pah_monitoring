package org.pah_monitoring.auxiliary.exceptions.rest.bad_request;

import org.pah_monitoring.auxiliary.exceptions.rest.bad_request.common.BadRequestRestException;

public class MalformedUrlRestException extends BadRequestRestException {

    public MalformedUrlRestException() {
        super();
    }

    public MalformedUrlRestException(String msg) {
        super(msg);
    }

    public MalformedUrlRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
