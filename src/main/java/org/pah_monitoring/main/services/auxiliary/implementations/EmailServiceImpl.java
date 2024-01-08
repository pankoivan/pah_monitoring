package org.pah_monitoring.main.services.auxiliary.implementations;

import lombok.AllArgsConstructor;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.auxiliary.text.ApplicationNameText;
import org.pah_monitoring.auxiliary.text.EmailMessageText;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.services.auxiliary.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@AllArgsConstructor
@Component
public class EmailServiceImpl implements EmailService {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String applicationEmail;

    public void sendRegistrationCode(String to, RegistrationSecurityCode code) {

        allowedRecipients(to); // todo: delete in final version

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom(applicationEmail);

        message.setTo(to);

        message.setSubject(
                EmailMessageText.registrationCodeTitle.formatted(ApplicationNameText.applicationName)
        );

        message.setText(EmailMessageText.registrationCodeText.formatted(
                ApplicationNameText.applicationName,
                code.getHospital().getName(),
                code.getRole().getAlias(),
                code.toString(),
                DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(code.getExpirationDate())
        ));

        mailSender.send(message);

    }

    // todo: delete in final version

    private void allowedRecipients(String to) {
        if (!List.of(
                "pank-tanya@yandex.ru",
                "ivan.tornado2@yandex.ru"
        ).contains(to)) {
            throw new Error("Recipient \"%s\" is not allowed".formatted(to));
        }
    }

}
