package org.pah_monitoring.main.exceptions.service.data;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class DataSavingServiceException extends ServiceException {

    public DataSavingServiceException() {
        super();
    }

    public DataSavingServiceException(String msg) {
        super(msg);
    }

    public DataSavingServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
