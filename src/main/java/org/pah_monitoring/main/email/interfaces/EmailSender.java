package org.pah_monitoring.main.email.interfaces;

import org.pah_monitoring.main.exceptions.email.EmailSendingException;

public interface EmailSender<T> {

    void send(String recipient, T content) throws EmailSendingException;

}
