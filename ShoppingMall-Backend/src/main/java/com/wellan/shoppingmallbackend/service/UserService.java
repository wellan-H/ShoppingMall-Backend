package com.wellan.shoppingmallbackend.service;

import com.wellan.shoppingmallbackend.dto.UserLoginRegister;
import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.User;

public interface UserService {
    Integer register(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);

    User login(UserLoginRegister userLoginRegister);
}
