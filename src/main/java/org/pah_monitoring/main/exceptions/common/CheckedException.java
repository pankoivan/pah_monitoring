package org.pah_monitoring.main.exceptions.common;

public class CheckedException extends Exception {

    public CheckedException() {
        super();
    }

    public CheckedException(String msg) {
        super(msg);
    }

    public CheckedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
