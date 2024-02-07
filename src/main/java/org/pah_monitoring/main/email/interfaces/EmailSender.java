package org.pah_monitoring.main.email.interfaces;

public interface EmailSender<T> {

    void send(String recipient, T content);

}
