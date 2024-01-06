package org.pah_monitoring.main.services.auxiliary.interfaces;

public interface EmailService {

    void send(String to, String subject, String text);

}
