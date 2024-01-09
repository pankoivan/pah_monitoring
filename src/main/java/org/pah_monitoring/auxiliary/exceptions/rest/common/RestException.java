package org.pah_monitoring.auxiliary.exceptions.rest.common;

import org.pah_monitoring.auxiliary.exceptions.common.UncheckedException;

public class RestException extends UncheckedException {

    public RestException() {
        super();
    }

    public RestException(String msg) {
        super(msg);
    }

    public RestException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
