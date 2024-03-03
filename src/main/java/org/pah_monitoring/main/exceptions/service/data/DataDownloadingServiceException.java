package org.pah_monitoring.main.exceptions.service.data;

import org.pah_monitoring.main.exceptions.service.common.ServiceException;

public class DataDownloadingServiceException extends ServiceException {

    public DataDownloadingServiceException() {
        super();
    }

    public DataDownloadingServiceException(String msg) {
        super(msg);
    }

    public DataDownloadingServiceException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
