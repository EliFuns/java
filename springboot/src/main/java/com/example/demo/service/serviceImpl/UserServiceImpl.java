package com.example.demo.service.serviceImpl;

import com.example.demo.dao.mapper.UserMapper;
import com.example.demo.model.User;
import com.example.demo.service.UserService;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

/**
 * ClassName:UserServiceImpl
 * Description:
 */
@Service
public class UserServiceImpl implements UserService{
    @Autowired
    private UserMapper mUserMapper;
    @Override
    public User selectByUserId(long userId) {
        User user = mUserMapper.selectByPrimaryKey(userId);
        return user;
    }
}
