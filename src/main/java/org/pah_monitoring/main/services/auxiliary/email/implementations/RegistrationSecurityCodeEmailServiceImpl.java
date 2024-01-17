package org.pah_monitoring.main.services.auxiliary.email.implementations;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.auxiliary.text.ApplicationNameText;
import org.pah_monitoring.auxiliary.text.EmailMessageText;
import org.pah_monitoring.main.entities.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.services.auxiliary.email.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

import java.util.List;

@NoArgsConstructor
@AllArgsConstructor
@Getter
@Setter
@Component("codeEmailSender")
public class RegistrationSecurityCodeEmailServiceImpl implements EmailService<RegistrationSecurityCode> {

    @Autowired
    private JavaMailSender mailSender;

    @Autowired
    @Value("${spring.mail.username}")
    private String applicationEmail;

    @Override
    public void send(String recipient, RegistrationSecurityCode code, boolean enabled) {
        if (enabled) {
            allowedRecipients(recipient); // todo: delete in final version

            SimpleMailMessage message = new SimpleMailMessage();

            message.setFrom(applicationEmail);

            message.setTo(recipient);

            message.setSubject(
                    EmailMessageText.registrationCodeTitle.formatted(ApplicationNameText.applicationName)
            );

            message.setText(EmailMessageText.registrationCodeText.formatted(
                    ApplicationNameText.applicationName,
                    code.getHospital().getName(),
                    code.getRole().getAlias(),
                    code.getCode().toString(),
                    DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(code.getExpirationDate())
            ));

            mailSender.send(message);
        }
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
