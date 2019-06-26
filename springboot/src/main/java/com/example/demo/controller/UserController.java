package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.enums.ResultStatus;
import com.example.demo.exception.MyException;
import com.example.demo.model.User;
import com.example.demo.service.UserService;
import com.example.demo.utils.JsonResponseEntity;
import com.example.demo.utils.Md5Utils;

import org.apache.commons.lang3.StringUtils;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;

import java.util.Hashtable;

import javax.naming.Context;

import io.swagger.annotations.ApiImplicitParam;
import io.swagger.annotations.ApiImplicitParams;
import io.swagger.annotations.ApiOperation;
import redis.clients.jedis.Jedis;

/**
 * ClassName:UserController
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Value("${LDAP.url}")
    private String URL;
    @Value("${LDAP.BASEDN}")
    private String BASEDN;
    @Value("${LDAP.LdapCtxFactory}")
    private String LdapCtxFactory;

    @Autowired
    private UserService mUserService;
//    @Autowired
//    private RedisClient mRedisClient;

    private static final Jedis jedis = new Jedis("47.95.218.163",6379);

    @PostMapping("/userId")
    public User getUsername(@RequestBody JSONObject user){
        long userId = user.getLong("userId");
        User user1 = mUserService.selectByUserId(userId);
        return user1;
    }

    //登陆
    @PostMapping("/login")
    public JsonResponseEntity login(@RequestBody JSONObject user){
        String username = user.getString("username");
        String password = user.getString("password");
        User user1 = mUserService.selectByUsername(username);
        if(user1 == null){
            return JsonResponseEntity.newJsonResult(ResultStatus.UNKNOWNERROR,null);
        }
        String hehongqian = Md5Utils.Encrypted_frontSalt(password, "hehongqian");
        logger.info("password === " + hehongqian);
        logger.info("password === " + user1.getPassword());
        if(!user1.getPassword().equalsIgnoreCase(hehongqian)){
            return JsonResponseEntity.newJsonResult(ResultStatus.NO_STAFFNo,null);
        }
        Long id = user1.getId();
        try {
            int i=0;
            while (i<1) {
//                String token = UUID.randomUUID().toString().replace("-", "");
//                logger.info("token == " + token);
//                String o = (String) mRedisClient.get(token);
//                logger.info("o ===== " + o);
//                if(o == null){
//                    logger.info("11111111111");
//                    mRedisClient.put(token,String.valueOf(id),30,TimeUnit.MINUTES);
//                    String o1 = (String) mRedisClient.get(token);
//                    logger.info("o1 === " + o1);
//                    return JsonResponseEntity.newJsonResult(ResultStatus.SUCCESS,token);
//                }
                String set = jedis.set("name", "wangwu");
                logger.info("set == " + set);
                String name = jedis.get("name");
                logger.info("name ===" + name);

                i++;
//            }
            }
            logger.info("2222222222");
            return JsonResponseEntity.newJsonResult(ResultStatus.UNKNOWNERROR,null);
        } catch(Exception e) {
            logger.info("33333333333");
            return JsonResponseEntity.newJsonResult(ResultStatus.NO_STAFFNo,null);
        }
    }

    @RequestMapping("/aa")
    public String aa(@RequestBody JSONObject user){
        String token = user.getString("token");
        return null;
    }

    @GetMapping("/ldapLogin")
    @ApiOperation(value="根据uid和password验证账号", notes="返回code:200正确,500错误")
    @ApiImplicitParams({
            @ApiImplicitParam(paramType="query", name = "password", value = "密码", required = true, dataType = "String"),
            @ApiImplicitParam(paramType="query", name = "uid", value = "uid", required = true, dataType = "String")
    })
    public JsonResponseEntity<Object> ldapLogin(@RequestParam(value = "password") String password,
                                                    @RequestParam(value = "uid") String uid){
        Hashtable env = new Hashtable<String, String>();
        env.put(Context.INITIAL_CONTEXT_FACTORY, LdapCtxFactory);
        env.put(Context.PROVIDER_URL, URL);//AD域路径和端口号
        env.put(Context.SECURITY_AUTHENTICATION, "simple");
        env.put(Context.SECURITY_PRINCIPAL, BASEDN);
        env.put(Context.SECURITY_CREDENTIALS, password);
        env.put(Context.REFERRAL, "throw");
        env.put(Context.PROVIDER_URL, URL );
        return null;
//        try {
//            DirContext ctx = new InitialDirContext(env);
//            ctx.close();
//            int i=0;
//            while (i<10){
//                String token = UUID.randomUUID().toString().replace("-", "");
//                logger.info("token == " + token);
//                if(mRedisClient.get(token) == null){
//                    mRedisClient.put(token,uid);
//                    mRedisClient.expire(token,30, TimeUnit.MINUTES);
//                    return JsonResponseEntity.newJsonResult(ResultStatus.SUCCESS,token);
//                }
//                i++;
//            }
//            return JsonResponseEntity.newJsonResult(ResultStatus.UNKNOWNERROR,null);
//        } catch (Exception e) {
//            return JsonResponseEntity.newJsonResult(ResultStatus.NO_STAFFNo,null);
//        }
    }

    @RequestMapping(value = "/exception")
    public String index() throws Exception{
        String name = "";
        if(StringUtils.isEmpty(name)){
            throw new MyException("1001","empty","/API/getUserName","在获取用户名字的时候为空");
        }
        return name;
    }
}
