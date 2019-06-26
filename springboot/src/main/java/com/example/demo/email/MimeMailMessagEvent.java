package com.example.demo.email;

import org.springframework.context.ApplicationEvent;
import org.springframework.mail.javamail.MimeMailMessage;

public class MimeMailMessagEvent extends ApplicationEvent {
    private static final long serialVersionUID = -6424588529678254971L;

    public MimeMailMessagEvent(MimeMailMessage message) {
        super(message);
    }
}
