package org.pah_monitoring.main.email.implementations;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.text.EmailMessageText;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.patient_additions.Achievement;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("achievementEmailSender")
public class PatientAwardingEmailSenderImpl implements EmailSender<Achievement> {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String applicationEmail;

    @Value("${my.email.enabled}")
    private final boolean enabled;

    @Override
    public void send(String recipient, Achievement achievement) {
        if (enabled) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                helper.setFrom(applicationEmail);
                helper.setTo(recipient);
                helper.setSubject(EmailMessageText.patientAwardingTitle);

                helper.setText(EmailMessageText.patientAwardingText.formatted(
                        achievement.getName(),
                        achievement.getDescription()
                ), true);

                mailSender.send(message);
            } catch (Exception e) {
                throw new EmailSendingException("Произошла ошибка при уведомлении пациента о полученной награде", e);
            }
        }
    }

}
