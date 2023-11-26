package org.pah_monitoring.auxiliary.exceptions;

public class NotHandledErrorStatusCodeException extends Exception {

    public NotHandledErrorStatusCodeException() {
        super();
    }

    public NotHandledErrorStatusCodeException(String msg) {
        super(msg);
    }

    public NotHandledErrorStatusCodeException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
