package com.wellan.shoppingmallbackend.service.impl;

import com.wellan.shoppingmallbackend.dao.UserDao;
import com.wellan.shoppingmallbackend.dto.UserLoginRegister;
import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.User;
import com.wellan.shoppingmallbackend.service.UserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.stereotype.Component;
import org.springframework.util.DigestUtils;
import org.springframework.web.server.ResponseStatusException;
@Component
public class UserServiceImpl implements UserService {
    @Autowired
    private UserDao userDao;
    private final static Logger log = LoggerFactory.getLogger(UserServiceImpl.class);
    @Override
    public Integer register(UserRegisterRequest userRegisterRequest) {
        User user = userDao.getUserByEmail(userRegisterRequest.getEmail());
        if (user!=null){
            log.warn("該email:{} 已被註冊");
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(userRegisterRequest.getPassword().getBytes());
        userRegisterRequest.setPassword(hashedPassword);
        Integer userId = userDao.createUser(userRegisterRequest);
        return userId;
    }

    @Override
    public User getUserById(Integer userId) {
        return userDao.getUserById(userId);
    }


    @Override
    public User login(UserLoginRegister userLoginRegister) {
        User user = userDao.getUserByEmail(userLoginRegister.getEmail());
        if(user==null){
            log.warn("此郵件：{} 未被註冊",userLoginRegister.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
        String hashedPassword = DigestUtils.md5DigestAsHex(userLoginRegister.getPassword().getBytes());
        if (user.getPassword().equals(hashedPassword)){
            return user;
        }else {
            log.warn("此帳戶：{} 的密碼錯誤。",userLoginRegister.getEmail());
            throw new ResponseStatusException(HttpStatus.BAD_REQUEST);
        }
    }
}
