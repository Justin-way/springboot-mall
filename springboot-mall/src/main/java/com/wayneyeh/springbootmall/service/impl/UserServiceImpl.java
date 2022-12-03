package com.wayneyeh.springbootmall.service.impl;

import com.wayneyeh.springbootmall.dao.UserDao;
import com.wayneyeh.springbootmall.dto.UserRegisterRequest;
import com.wayneyeh.springbootmall.model.User;
import com.wayneyeh.springbootmall.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

@Component
public class UserServiceImpl implements UserService {

    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
