package org.pah_monitoring.main.services.auxiliary.implementations;

import org.pah_monitoring.main.services.auxiliary.interfaces.EmailService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.stereotype.Component;

@Component
public class EmailServiceImpl implements EmailService {

    @Autowired
    private JavaMailSender sender;

    public void send(String to, String subject, String text) {

        if (!to.equals("pank-tanya@yandex.ru") && !to.equals("ivan.tornado2@yandex.ru")) {
            throw new AssertionError();
        }

        SimpleMailMessage message = new SimpleMailMessage();

        message.setFrom("panivan98@yandex.ru");
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);

        System.out.println(message);

        sender.send(message);

    }

}
