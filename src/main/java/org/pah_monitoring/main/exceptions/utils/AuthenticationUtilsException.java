package org.pah_monitoring.main.exceptions.utils;

import org.pah_monitoring.main.exceptions.utils.common.UtilsException;

public class AuthenticationUtilsException extends UtilsException {

    public AuthenticationUtilsException() {
        super();
    }

    public AuthenticationUtilsException(String msg) {
        super(msg);
    }

    public AuthenticationUtilsException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
