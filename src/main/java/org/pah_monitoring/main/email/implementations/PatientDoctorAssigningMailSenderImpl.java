package org.pah_monitoring.main.email.implementations;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.text.EmailMessageText;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.users.users.Doctor;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

import java.util.List;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("doctorAssigningEmailSender")
public class PatientDoctorAssigningMailSenderImpl implements EmailSender<Doctor> {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String applicationEmail;

    @Value("${my.email.enabled}")
    private final boolean enabled;

    @Override
    public void send(String recipient, Doctor doctor) {
        if (enabled) {
            try {
                allowedRecipients(recipient); // todo: delete in final version
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                helper.setFrom(applicationEmail);
                helper.setTo(recipient);
                helper.setSubject(EmailMessageText.patientDoctorAssigningTitle);

                helper.setText(EmailMessageText.patientDoctorAssigningText.formatted(
                        doctor.getId(),
                        doctor.getUserInformation().getFullName(),
                        doctor.getUserInformation().getId()
                ), true);

                mailSender.send(message);
            } catch (Exception e) {
                throw new EmailSendingException("Произошла ошибка при уведомлении пациента о назначенном лечащем враче", e);
            }
        }
    }

    private void allowedRecipients(String to) { // todo: delete in final version
        if (!List.of("pank-tanya@yandex.ru", "ivan.tornado2@yandex.ru").contains(to)) {
            throw new Error("Recipient \"%s\" is not allowed".formatted(to));
        }
    }

}
