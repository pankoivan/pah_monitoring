package org.pah_monitoring.main.exceptions.common;

public class UncheckedException extends RuntimeException {

    public UncheckedException() {
        super();
    }

    public UncheckedException(String msg) {
        super(msg);
    }

    public UncheckedException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
