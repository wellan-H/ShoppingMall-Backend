package com.wellan.shoppingmallbackend.dao.impl;

import com.wellan.shoppingmallbackend.dao.UserDao;
import com.wellan.shoppingmallbackend.dto.UserRegisterRequest;
import com.wellan.shoppingmallbackend.model.Product;
import com.wellan.shoppingmallbackend.model.User;
import com.wellan.shoppingmallbackend.rowmapper.ProductRowMapper;
import com.wellan.shoppingmallbackend.rowmapper.UserRowMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.jdbc.core.namedparam.MapSqlParameterSource;
import org.springframework.jdbc.core.namedparam.NamedParameterJdbcTemplate;
import org.springframework.jdbc.support.GeneratedKeyHolder;
import org.springframework.jdbc.support.KeyHolder;
import org.springframework.stereotype.Component;

import java.util.*;

@Component
public class UserDaoImpl implements UserDao {
    @Autowired
    private NamedParameterJdbcTemplate namedParameterJdbctemplate;

    @Override
    public Integer createUser(UserRegisterRequest userRegisterRequest) {
        String sql = "INSERT INTO user(email, password, created_date, last_modified_date) " +
                "VALUES (:email, :password, :createdDate, :lastModifiedDate) ";
        Map<String , Object> map = new HashMap<>();
        map.put("email",userRegisterRequest.getEmail());
        map.put("password",userRegisterRequest.getPassword());
        Date now = new Date();
        map.put("createdDate", now);
        map.put("lastModifiedDate", now);
        KeyHolder keyHolder = new GeneratedKeyHolder();
        namedParameterJdbctemplate.update(sql, new MapSqlParameterSource(map), keyHolder);
        int id = keyHolder.getKey().intValue();
        return id;

    }

    @Override
    public User getUserById(Integer userId) {
        String sql = "SELECT user_id, email, password, created_date, last_modified_date  " +
                "FROM user WHERE user_id=:userId";
        Map<String,Object> map = new HashMap<>();
        map.put("userId", userId);
        List<User> userList = namedParameterJdbctemplate.query(sql, map, new UserRowMapper());
        if (userList.size()>0){
            return userList.get(0);
        }else   return null;
    }

    @Override
    public User getUserByEmail(String email) {
        String sql = "SELECT user_id, email, password, " +
                "created_date, last_modified_date FROM user WHERE email=:email  ";
        Map<String,Object> map = new HashMap<>();
        map.put("email", email);
        List<User> userList = namedParameterJdbctemplate.query(sql, map, new UserRowMapper());
        if (userList.size()>0){
            return userList.get(0);
        }else   return null;
    }
}
