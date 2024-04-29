package org.globaroman.petshopba.service.impl;

import jakarta.mail.Authenticator;
import jakarta.mail.Message;
import jakarta.mail.MessagingException;
import jakarta.mail.PasswordAuthentication;
import jakarta.mail.Session;
import jakarta.mail.Transport;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import java.util.Properties;
import lombok.extern.log4j.Log4j2;
import org.globaroman.petshopba.exception.DataProcessingException;
import org.globaroman.petshopba.service.EmailSenderService;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

@Service
@Log4j2
public class EmailSenderServiceImpl implements EmailSenderService {
    @Value("${mail.smtp.host}")
    private String smptHost;
    @Value("${mail.smtp.port}")
    private String port;
    @Value("${mail.smtp.auth}")
    private String auth;
    @Value("${mail.smtp.starttls.enable}")
    private String starttls;
    @Value("${mail.login}")
    private String userName;
    @Value("${mail.password}")
    private String password;

    @Override
    public String sendEmail(String from, String to, String subject, String body) {

        Properties properties = new Properties();
        properties.put("mail.smtp.host", smptHost);
        properties.put("mail.smtp.port", port);
        properties.put("mail.smtp.auth", auth);
        properties.put("mail.smtp.starttls.enable", starttls);

        Session session = Session.getInstance(properties, new Authenticator() {
            @Override
            protected PasswordAuthentication getPasswordAuthentication() {
                return new PasswordAuthentication(userName, password);
            }
        });

        try {
            Message message = new MimeMessage(session);
            message.setFrom(new InternetAddress(from));
            message.setRecipients(Message.RecipientType.TO, InternetAddress.parse(to));
            message.setSubject(subject);
            message.setText(body);
            Transport.send(message);
            return "Email sent successfully!";
        } catch (MessagingException e) {
            log.error("Email didn't send", e);
            throw new DataProcessingException("Email didn't send", e);
        }
    }
}
