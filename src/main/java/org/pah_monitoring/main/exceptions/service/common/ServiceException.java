package org.pah_monitoring.main.exceptions.service.common;

import org.pah_monitoring.main.exceptions.common.UncheckedException;

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
