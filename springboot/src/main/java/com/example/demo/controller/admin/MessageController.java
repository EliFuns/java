package com.example.demo.controller.admin;

import com.example.demo.common.ResultJson;
import com.example.demo.email.Constants;
import com.example.demo.email.MailMessageService;
import com.example.demo.email.Messages;
import com.example.demo.exeception.BizException;
import com.example.demo.service.UserService;
import com.example.demo.utils.RedisClient;
import com.example.demo.utils.ValUtils;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.concurrent.TimeUnit;

import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import io.swagger.annotations.ApiParam;

/**
 * <p>
 * 消息发送 前端控制器
 * </p>
 *
 * @author HeHongQian
 * @since 2019-05-25
 */
@RestController
@RequestMapping("/msg")
@Api(value = "消息发送相关服务接口", description = "消息发送相关服务接口")
public class MessageController{
    private static final String REGISTER_TEMPLATE = "尊敬的用户您好，欢迎您来到！\n" +
            "欢迎注册IPF\n" +
            "您好，您正在注册IPF账号，注册验证码：#code#。\n" +
            "为保障账号安全性，该邮箱验证码仅在5分钟内有效。\n" +
            "安全提示:\n" +
            "不要把您的密码以及二次验证码告诉任何人！\n" +
            "不要给任何声称是IPF的客服人员打电话！\n" +
            "不要给任何声称是IPF的工作人员转账！\n" +
            "如果此活动不是您本人操作，请您尽快联系IPF客服，发送邮件至：i_hehongqian@sina.com\n" +
            "IPFS团队 \n" +
            "系统邮件，请勿回复";
    private static final String EMAIL_LOGIN_TEMPLATE = "尊敬的用户您好，欢迎登录IPF！\n" +
            "您好，您正在登录IPF账号，登录验证码：#code#。\n" +
            "为保障账号安全性，该邮箱验证码仅在5分钟内有效。\n" +
            "安全提示:\n" +
            "不要把您的密码以及二次验证码告诉任何人！\n" +
            "不要给任何声称是IPF的客服人员打电话！\n" +
            "不要给任何声称是IPF的工作人员转账！\n" +
            "如果此活动不是您本人操作，请您尽快联系IPF客服，发送邮件至：i_hehongqian@sina.com\n" +
            "IPFS团队 \n" +
            "系统邮件，请勿回复";
    @Autowired
    private MailMessageService mailMessageService;
    @Autowired
    private UserService userService;
    @Autowired
    protected RedisClient redisClient;

    @ApiOperation(
            value = "获取注册验证码（无需授权）",
            notes = "获取注册验证码（无需授权）"
    )
    @GetMapping("/mail/register")
    public ResultJson<Void> sendRegisterMail(
            @ApiParam(value = "邮箱",required = true) @RequestParam String mail) {
        if (!ValUtils.email(mail)) {
            throw new BizException(Messages.EMAIL_INVALID);
        }
        int code = (int) (1 + Math.random() * (999999 - 100000 + 1));
        mailMessageService.sendSimple("注册验证码", REGISTER_TEMPLATE.replaceAll("#code#", String.valueOf(code)), mail);
        redisClient.put(Constants.REGISTER_MAIL_CODE_PREFIX + mail, code, 5L, TimeUnit.MINUTES);
        return ResultJson.success();
    }

    @ApiOperation(
            value = "获取邮箱登录验证码（无需授权）",
            notes = "获取邮箱登录验证码（无需授权）"
    )
    @GetMapping("/mail/login")
    public ResultJson<Void> sendEmailLoginCode(
            @ApiParam(value = "邮箱",required = true) @RequestParam String mail) {
        if (!ValUtils.email(mail)) {
            throw new BizException(Messages.EMAIL_INVALID);
        }
        int code = (int) (1 + Math.random() * (999999 - 100000 + 1));
        mailMessageService.sendSimple("登录验证码", EMAIL_LOGIN_TEMPLATE.replaceAll("#code#", String.valueOf(code)), mail);
        redisClient.put(Constants.LOGIN_MAIL_CODE_PREFIX + mail, code, 5L, TimeUnit.MINUTES);
        return ResultJson.success();
    }

}

