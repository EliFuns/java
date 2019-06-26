package com.example.demo.controller;

import com.alibaba.fastjson.JSONObject;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * ClassName:UserController
 * Description:
 */
@RestController
@RequestMapping("/user")
public class UserController {
    private static final Logger logger = LogManager.getLogger(UserController.class);
    @Autowired
    private UserService mUserService;

    @PostMapping("/userId")
    public User getUsername(@RequestBody JSONObject user){
        long userId = user.getLong("userId");
        User user1 = mUserService.selectByUserId(userId);
        return user1;
    }
}
