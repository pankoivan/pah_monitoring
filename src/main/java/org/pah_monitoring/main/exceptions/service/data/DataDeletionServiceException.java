package org.pah_monitoring.main.exceptions.service.data;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class DataDeletionServiceException extends ServiceException {

    public DataDeletionServiceException() {
        super();
    }

    public DataDeletionServiceException(String msg) {
        super(msg);
    }

    public DataDeletionServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
