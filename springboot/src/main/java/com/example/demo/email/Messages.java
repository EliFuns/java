package com.example.demo.email;


public enum Messages {
    STOP(-1,"暂停服务","stop")
    ,SUCCESS(2000,"成功","success")
    ,SYSTEM_ERROR(9999,"系统错误","system error")
    ,AUTH_FAIL(4001,"无访问权限","no access")
    ,USERNAME_PASSWORD_FAIL(4002, "用户名或密码错误", "username or password error")
    ,USERNAME_EMPTY(4003, "用户名不能为空", "Username is null")
    ,PASSWORD_EMPTY(4004, "密码不能为空", "password is null")
    ,VERIFICATION_CODE_ERROR(4005,"验证码错误", "Verification code error")
    ,EMAIL_EMPTY(4006,"邮箱不能为空","Email is null")
    ,EMAIL_INVALID(4007,"邮箱不合法","email invalid")
    ,VERIFICATION_CODE_EMPTY(4008, "验证码不能为空", "verification code is null")
    ,USERNAME_EXISTS(4009, "用户名已存在", "username exists")
    ,EMAIL_EXISTS(4010,"邮箱已经存在", "email exists")
    ,EMAIL_NOT_EXISTS(4011,"邮箱不存在", "email not exists")
    ,INVITE_CODE_EXISTS(4012,"推荐码不存在", "invite code not exists")
    ,AVAILABLE_ASSETS_INSUFFICIENT(4013,"可用资产不足", "available assets insufficient")
    ,PROHIBIT_LOGIN(4014,"禁止登录", "prohibit login")


    ,CONCURRENT_EXECUTOR_ERROR(5000,"当前访问人数较多，请稍后再试","")


    ;
    private int code;
    private String de;
    private String zh;
    private String en;

    Messages(int code, String zh, String en) {
        this.de = zh;
        this.code = code;
        this.zh = zh;
        this.en = en;
    }

    public enum Lang {
        de,zh,en;
    }

    public int getCode() {
        return code;
    }

    public String getDe() {
        return de;
    }

    public String getZh() {
        return zh;
    }

    public String getEn() {
        return en;
    }
}
