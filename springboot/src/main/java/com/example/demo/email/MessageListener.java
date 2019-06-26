package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.event.EventListener;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.mail.javamail.JavaMailSender;
import org.springframework.scheduling.annotation.Async;
import org.springframework.stereotype.Component;

import javax.mail.internet.MimeMessage;

@Component
public class MessageListener {
    @Autowired
    private JavaMailSender mailSender;

    @EventListener
    @Async
    public void simpleMailMessagEvent(SimpleMailMessagEvent event) throws Exception {
        mailSender.send((SimpleMailMessage)event.getSource());
    }

    @EventListener
    @Async
    public void mimeMailMessagEvent(MimeMailMessagEvent event) throws Exception {
        mailSender.send((MimeMessage)event.getSource());
    }
}
