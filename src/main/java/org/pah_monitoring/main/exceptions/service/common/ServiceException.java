package org.pah_monitoring.main.exceptions.service.common;

import org.pah_monitoring.main.exceptions.common.CheckedException;

public class ServiceException extends CheckedException {

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
