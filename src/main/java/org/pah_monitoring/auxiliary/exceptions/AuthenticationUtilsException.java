package org.pah_monitoring.auxiliary.exceptions;

// todo: change to extends CheckedException

public class AuthenticationUtilsException extends Exception {

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
