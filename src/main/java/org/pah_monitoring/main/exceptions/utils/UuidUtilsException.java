package org.pah_monitoring.main.exceptions.utils;

import org.pah_monitoring.main.exceptions.utils.common.UtilsException;

public class UuidUtilsException extends UtilsException {

    public UuidUtilsException() {
        super();
    }

    public UuidUtilsException(String msg) {
        super(msg);
    }

    public UuidUtilsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
