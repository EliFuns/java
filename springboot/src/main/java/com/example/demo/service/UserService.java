package com.example.demo.service;

import com.example.demo.model.User;

/**
 * ClassName:UserService
 * Description:
 */
public interface UserService {
    User selectByUserId(long userId);

    User selectByUsername(String username);
}
