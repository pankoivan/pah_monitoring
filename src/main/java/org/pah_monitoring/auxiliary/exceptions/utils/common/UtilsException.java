package org.pah_monitoring.auxiliary.exceptions.utils.common;

import org.pah_monitoring.auxiliary.exceptions.common.CheckedException;

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
