package com.example.demo.common;

import com.example.demo.common.local.LanguageThreadLocal;
import com.example.demo.email.Messages;

import org.apache.commons.lang3.StringUtils;

import java.io.Serializable;
import java.lang.reflect.Method;
import java.util.Map;
import java.util.concurrent.ConcurrentHashMap;

import io.swagger.annotations.ApiModelProperty;


public class ResultJson<T> implements Serializable {

    private static final long serialVersionUID = -4249161560059648989L;
    private static Map<String, String> map = new ConcurrentHashMap<>();

    @ApiModelProperty(value = "状态码")
    private int code;
    @ApiModelProperty(value = "返回信息")
    private String message;
    @ApiModelProperty(value = "返回数据")
    private T data;

    public int getCode() {
        return code;
    }

    public String getMessage() {
        return message;
    }

    public T getData() {
        return data;
    }

    private ResultJson() {

    }


    public static ResultJson fail() {
        return fail(Messages.SYSTEM_ERROR);
    }

    public static ResultJson fail(Messages messages) {
        if (messages == null) {
            messages = Messages.SYSTEM_ERROR;
        }
        return build(messages, null);
    }

    public static <T> ResultJson success() {
        return success(null);
    }

    public static <T> ResultJson success(T data) {
        return build(Messages.SUCCESS, data);
    }

    public static <T> ResultJson build(Messages messages, T data) {
        ResultJson json = new ResultJson();
        if (messages == null) {
            messages = Messages.SYSTEM_ERROR;
        }
        String msg = null;
        String language = getLanguage();
        String key = messages.getCode() + "_" + language;
        msg = map.get(key);
        if (StringUtils.isBlank(msg)) {
            Class<? extends Messages> clazz = messages.getClass();
            try {
                Method method = clazz.getMethod("get" + toUpperCaseFirstOne(language));
                Object invoke = method.invoke(messages);
                msg = (String) invoke;
                map.put(key, msg);
            } catch (Exception e) {
            }
        }
        json.message = msg;
        json.data = data;
        json.code = messages.getCode();
        return json;
    }

    //首字母转大写
    private static String toUpperCaseFirstOne(String s) {
        if (Character.isUpperCase(s.charAt(0)))
            return s;
        else
            return (new StringBuilder()).append(Character.toUpperCase(s.charAt(0))).append(s.substring(1)).toString();
    }

    private static String getLanguage(){
        String language = LanguageThreadLocal.get();
        if (StringUtils.isNotBlank(language)) {
            try {
                return Messages.Lang.valueOf(language).toString();
            } catch (Exception e) {
            }
        }
        return Messages.Lang.de.toString();
    }

}
