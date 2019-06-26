package com.example.demo.email;

import org.springframework.context.ApplicationEvent;
import org.springframework.mail.SimpleMailMessage;

public class SimpleMailMessagEvent extends ApplicationEvent {
    private static final long serialVersionUID = -6424588529678254971L;

    public SimpleMailMessagEvent(SimpleMailMessage message) {
        super(message);
    }
}
