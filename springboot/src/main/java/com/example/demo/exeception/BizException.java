package com.example.demo.exeception;

import com.example.demo.email.Messages;

public class BizException extends BaseException{
    public BizException(Messages messages) {
        super(messages);
    }
}
