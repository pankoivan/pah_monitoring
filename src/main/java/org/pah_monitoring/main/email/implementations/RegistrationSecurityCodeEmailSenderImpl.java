package org.pah_monitoring.main.email.implementations;

import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import lombok.Setter;
import org.pah_monitoring.auxiliary.constants.DateTimeFormatConstants;
import org.pah_monitoring.auxiliary.text.ApplicationNameText;
import org.pah_monitoring.auxiliary.text.EmailMessageText;
import org.pah_monitoring.main.email.interfaces.EmailSender;
import org.pah_monitoring.main.entities.main.security_codes.RegistrationSecurityCode;
import org.pah_monitoring.main.exceptions.email.EmailSendingException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.mail.javamail.MimeMessageHelper;
import org.springframework.stereotype.Component;

@RequiredArgsConstructor
@Setter(onMethod = @__(@Autowired))
@Component("codeEmailSender")
public class RegistrationSecurityCodeEmailSenderImpl implements EmailSender<RegistrationSecurityCode> {

    private final JavaMailSender mailSender;

    @Value("${spring.mail.username}")
    private final String applicationEmail;

    @Value("${my.email.enabled}")
    private final boolean enabled;

    @Override
    public void send(String recipient, RegistrationSecurityCode code) throws EmailSendingException {
        if (enabled) {
            try {
                MimeMessage message = mailSender.createMimeMessage();
                MimeMessageHelper helper = new MimeMessageHelper(message);

                helper.setFrom(applicationEmail);
                helper.setTo(recipient);
                helper.setSubject(EmailMessageText.registrationCodeTitle);

                helper.setText(EmailMessageText.registrationCodeText.formatted(
                        ApplicationNameText.applicationName,
                        code.getHospital().getName(),
                        code.getRole().getAlias(),
                        code.getCode().toString(),
                        DateTimeFormatConstants.DAY_MONTH_YEAR_AT_HOUR_MINUTE_SECOND.format(code.getExpirationDate())
                ), true);

                mailSender.send(message);
            } catch (Exception e) {
                throw new EmailSendingException("""
                        Произошла ошибка при отправке сгенерированного кода на указанную почту. Убедитесь в корректности\
                         формата почты и повторите попытку через какое-то время
                        """, e
                );
            }
        }
    }

}
