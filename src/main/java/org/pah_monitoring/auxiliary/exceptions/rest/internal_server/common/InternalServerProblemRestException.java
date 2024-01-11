package org.pah_monitoring.auxiliary.exceptions.rest.internal_server.common;

import org.pah_monitoring.auxiliary.exceptions.rest.common.RestException;

public class InternalServerProblemRestException extends RestException {

    public InternalServerProblemRestException() {
        super();
    }

    public InternalServerProblemRestException(String msg) {
        super(msg);
    }

    public InternalServerProblemRestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
