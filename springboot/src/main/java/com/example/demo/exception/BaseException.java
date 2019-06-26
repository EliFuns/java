package com.example.demo.exception;


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
