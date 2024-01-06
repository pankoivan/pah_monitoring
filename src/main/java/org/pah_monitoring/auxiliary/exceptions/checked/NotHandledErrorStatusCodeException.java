package org.pah_monitoring.auxiliary.exceptions.checked;

public class NotHandledErrorStatusCodeException extends CheckedException {

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
