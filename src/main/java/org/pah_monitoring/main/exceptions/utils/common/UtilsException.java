package org.pah_monitoring.main.exceptions.utils.common;

import org.pah_monitoring.main.exceptions.common.CheckedException;

public class UtilsException extends CheckedException {

    public UtilsException() {
        super();
    }

    public UtilsException(String msg) {
        super(msg);
    }

    public UtilsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
