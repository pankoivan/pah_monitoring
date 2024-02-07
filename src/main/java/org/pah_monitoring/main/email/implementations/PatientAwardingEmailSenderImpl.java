package org.pah_monitoring.main.email.implementations;

import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("codeEmailSender")
public class PatientAwardingEmailSenderImpl implements EmailSender<Achievement> {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String applicationEmail;

    @Value("${my.email.enabled}")
    private final boolean enabled;

    @Override
    public void send(String recipient, Achievement achievement) {

    }

}
