package com.enterprise.service;

import jakarta.mail.MessagingException;
import jakarta.mail.internet.InternetAddress;
import jakarta.mail.internet.MimeMessage;
import lombok.RequiredArgsConstructor;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Service;

@Service
@RequiredArgsConstructor
public class EmailService {

    @Autowired
    private JavaMailSender mailSender;
@Async
    public void sendEmail(String to, String subject, String body) throws MessagingException {
    MimeMessage message = mailSender.createMimeMessage();
    message.setFrom(new InternetAddress("sender@example.com"));
     message.setRecipients(MimeMessage.RecipientType.TO,to);
        message.setSubject(subject);
        message.setContent(body, "text/html; charset=utf-8");
        mailSender.send(message);
    }

}
