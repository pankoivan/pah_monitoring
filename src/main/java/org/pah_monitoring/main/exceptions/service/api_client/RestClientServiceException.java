package org.pah_monitoring.main.exceptions.service.api_client;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class RestClientServiceException extends ServiceException {

    public RestClientServiceException() {
        super();
    }

    public RestClientServiceException(String msg) {
        super(msg);
    }

    public RestClientServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
