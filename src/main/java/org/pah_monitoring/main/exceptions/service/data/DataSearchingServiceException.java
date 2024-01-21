package org.pah_monitoring.main.exceptions.service.data;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class DataSearchingServiceException extends ServiceException {

    public DataSearchingServiceException() {
        super();
    }

    public DataSearchingServiceException(String msg) {
        super(msg);
    }

    public DataSearchingServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
