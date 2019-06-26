package com.example.demo.exception;

import org.springframework.web.bind.annotation.ControllerAdvice;
import org.springframework.web.bind.annotation.ExceptionHandler;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.HashMap;
import java.util.Map;

/**
 * ClassName:MyControllerAdvice
 * Description:
 */
@ControllerAdvice
public class MyControllerAdvice {

    /**
     * 拦截捕捉自定义异常 MyException.class
     * @param myex
     * @return
     */
    @ResponseBody
    @ExceptionHandler(value = MyException.class)
    public Map<String,Object> myExceptionHandler(MyException myex){
        Map<String,Object> map  = new HashMap<String,Object>();
        map.put("code",myex.getCode());
        map.put("message",myex.getMessage());
        map.put("method",myex.getMethod());
        map.put("descinfo",myex.getDescinfo());
        //发生异常进行日志记录，写入数据库或者其他处理，此处省略
        return map;
    }
}
