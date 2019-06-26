package com.example.demo.email;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.ApplicationContext;
import org.springframework.mail.SimpleMailMessage;
import org.springframework.stereotype.Service;

@Service
public class MailMessageService {
    @Value("${spring.mail.username}")
    private String username;
    @Autowired
    private ApplicationContext applicationContext;

    /**
     * 发送简单邮件
     * @param subject 主题
     * @param text 内容
     * @param to 收件人
     */
    public void sendSimple(String subject, String text, String... to){
        SimpleMailMessage message = new SimpleMailMessage();
        message.setFrom(username);
        message.setTo(to);
        message.setSubject(subject);
        message.setText(text);
        applicationContext.publishEvent(new SimpleMailMessagEvent(message));
    }

}
