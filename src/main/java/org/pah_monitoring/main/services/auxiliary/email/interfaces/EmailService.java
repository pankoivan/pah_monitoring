package org.pah_monitoring.main.services.auxiliary.email.interfaces;

public interface EmailService<T> {

    void send(String recipient, T content, boolean enabled); // todo: remove enabled

}
