package org.pah_monitoring.main.exceptions.service;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class EntityNotFoundServiceException extends ServiceException {

    public EntityNotFoundServiceException() {
        super();
    }

    public EntityNotFoundServiceException(String msg) {
        super(msg);
    }

    public EntityNotFoundServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
