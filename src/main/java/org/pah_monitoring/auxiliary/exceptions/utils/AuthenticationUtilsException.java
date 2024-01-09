package org.pah_monitoring.auxiliary.exceptions.utils;

import org.pah_monitoring.auxiliary.exceptions.utils.common.UtilsException;

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
