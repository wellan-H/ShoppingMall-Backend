package com.wellan.shoppingmallbackend.controller;

import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.User;
import com.wellan.shoppingmallbackend.service.UserService;
import org.slf4j.Logger;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.transaction.annotation.Transactional;
import org.springframework.web.bind.annotation.*;

import javax.validation.Valid;

@RestController
public class UserController {
//    @Autowired
//    Logger logger;
    @Autowired
    private UserService userService;
    @Transactional
    @PostMapping("/users/register")
    public ResponseEntity<User> register(@RequestBody @Valid UserRegisterRequest userRegisterRequest){
        Integer userId= userService.register(userRegisterRequest);
        User userCreate = userService.getUserById(userId);
        return ResponseEntity.status(HttpStatus.CREATED).body(userCreate);
    }
}
