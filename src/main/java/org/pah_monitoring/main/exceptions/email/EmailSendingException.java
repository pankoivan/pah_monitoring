package org.pah_monitoring.main.exceptions.email;

import org.pah_monitoring.main.exceptions.common.UncheckedException;

public class EmailSendingException extends UncheckedException {

    public EmailSendingException() {
        super();
    }

    public EmailSendingException(String msg) {
        super(msg);
    }

    public EmailSendingException(String msg, Throwable throwable) {
        super(msg, throwable);
    }

}
