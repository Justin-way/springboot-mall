package com.wayneyeh.springbootmall.service;

import com.wayneyeh.springbootmall.dto.UserRegisterRequest;
import com.wayneyeh.springbootmall.model.User;

public interface UserService {

    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
