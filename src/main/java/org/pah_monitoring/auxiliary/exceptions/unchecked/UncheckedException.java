package org.pah_monitoring.auxiliary.exceptions.unchecked;

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
