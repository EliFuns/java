package com.example.demo.exeception;


import com.example.demo.email.Messages;

public class BaseException extends RuntimeException{

    protected Messages messages;

    public BaseException(Messages messages) {
        super(messages.getDe());
        this.messages = messages;
    }

    public Messages getMessages() {
        return messages;
    }
}
