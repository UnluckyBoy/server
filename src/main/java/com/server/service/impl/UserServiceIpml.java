package com.server.service.impl;

import com.server.model.mapper.UserMapper;
import com.server.model.pojo.UserInfo;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service("UserService")
public class UserServiceIpml implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo getUser(String account) {
        return userMapper.getUser(account);
    }

    @Override
    public UserInfo login(Map<String,String> map) {
        return userMapper.login(map);
    }

    @Override
    public UserInfo getQuery() {

        return userMapper.getQuery();
    }
}
