package com.wellan.shoppingmallbackend.dao;

import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.User;

public interface UserDao {
    Integer createUser(UserRegisterRequest userRegisterRequest);

    User getUserById(Integer userId);
}
