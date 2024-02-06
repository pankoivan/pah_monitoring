package org.pah_monitoring.main.services.additional.email.interfaces;

public interface EmailService<T> {

    void send(String recipient, T content);

}
