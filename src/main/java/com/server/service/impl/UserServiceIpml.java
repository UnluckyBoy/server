package com.server.service.impl;

import com.server.model.mapper.UserMapper;
import com.server.model.pojo.UserInfo;
import com.server.service.UserService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("UserService")
public class UserServiceIpml implements UserService {

    @Autowired
    private UserMapper userMapper;

    @Override
    public UserInfo infoQuery(Map<String,Object> map) {
        return userMapper.infoQuery(map);
    }

    @Override
    public UserInfo login(Map<String,Object> map) {
        return userMapper.login(map);
    }

    @Override
    public boolean register(Map<String, Object> map) {
        return userMapper.register(map);
    }

    @Override
    public List<UserInfo> fuzzyQuery(String name) {
        return userMapper.fuzzyQuery(name);
    }

    @Override
    public boolean upgptnumber(Map<String,Object> map) throws Exception{
        return userMapper.upgptnumber(map);
    }

    @Override
    public boolean fresh_status_login(Map<String, Object> map) {
        return userMapper.fresh_status_login(map);
    }

    @Override
    public boolean fresh_status_logout(Map<String, Object> map) {
        return userMapper.fresh_status_logout(map);
    }

    @Override
    public boolean fresh_head(Map<String, Object> map) {
        return userMapper.fresh_head(map);
    }
}
