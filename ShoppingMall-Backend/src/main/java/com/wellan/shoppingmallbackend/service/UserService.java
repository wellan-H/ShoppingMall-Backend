package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.dto.UserLoginRequest;
import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRequest userLoginRequest);
}
