package org.pah_monitoring.auxiliary.exceptions.service.common;

import org.pah_monitoring.auxiliary.exceptions.common.UncheckedException;

public class ServiceException extends UncheckedException {

    public ServiceException() {
        super();
    }

    public ServiceException(String msg) {
        super(msg);
    }

    public ServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
