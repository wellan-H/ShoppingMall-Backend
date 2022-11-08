package com.wellan.shoppingmallbackend.service.impl;

import com.wellan.shoppingmallbackend.dao.UserDao;
import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.User;
import com.wellan.shoppingmallbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.web.server.ResponseStatusException;

@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private final static Logger logger = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user  = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user!=null){
            logger.warn("該信箱{}已被註冊。",userRegisterRequest.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        return userDao.createUser(userRegisterRequest);
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }
}
