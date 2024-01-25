package org.pah_monitoring.main.exceptions.service.api_client;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class ApiClientServiceException extends ServiceException {

    public ApiClientServiceException() {
        super();
    }

    public ApiClientServiceException(String msg) {
        super(msg);
    }

    public ApiClientServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
