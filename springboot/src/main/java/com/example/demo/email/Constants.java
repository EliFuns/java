package com.example.demo.email;

import java.math.BigDecimal;

public interface Constants {
    String X_AUTH_TOKEN = "X-AUTH-TOKEN";
    // token过期时间单位分钟
    long TOKEN_EXPIRATION_TIMEOUT = 120L;
    // 邮箱注册验证码前缀
    String REGISTER_MAIL_CODE_PREFIX = "REGISTER_MAIL_CODE_";
    // 邮箱登录验证码前缀
    String LOGIN_MAIL_CODE_PREFIX = "LOGIN_MAIL_CODE_";
    // 国际化参数
    String LANGUAGE = "lang";
    // token存储前缀
    String TOKEN_PREFIX = "token_";
    // 用户与token关系
    String USER_TOKEN_PREFIX = "user_token_";
    // 小于这个值则忽略不计算
    BigDecimal IGNORE_EXEC = new BigDecimal(0.000001);

}
