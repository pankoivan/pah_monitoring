package org.pah_monitoring.main.exceptions.service;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class NotEnoughRightsServiceException extends ServiceException {

    public NotEnoughRightsServiceException() {
        super();
    }

    public NotEnoughRightsServiceException(String msg) {
        super(msg);
    }

    public NotEnoughRightsServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
