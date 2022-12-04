package com.wayneyeh.springbootmall.service.impl;

import com.wayneyeh.springbootmall.dao.UserDao;
import com.wayneyeh.springbootmall.dto.UserLoginRequest;
import com.wayneyeh.springbootmall.dto.UserRegisterRequest;
import com.wayneyeh.springbootmall.model.User;
import com.wayneyeh.springbootmall.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {

    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Autowired
    private UserDao userDao;

    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        // check email
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());

        if (user != null) {
            logger.warn("this email {} has been registered", userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }

        //use md5
        String hashedpassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedpassword);


        //create account
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }

    @Override
    public User login(UserLoginRequest userLoginRequest) {


        //check it exist
        User user = userDao.getUserByEmail(userLoginRequest.getEmail());
        if (user == null) {
            logger.warn("this email{} has not been register", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }

        //use md5


        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRequest.getPassword().getBytes());

        //check password
        if (user.getPassword().equals(hashedPassword)) {
            return user;
        } else {
            logger.warn("this email{} password is not correct", userLoginRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);

        }
    }
}
