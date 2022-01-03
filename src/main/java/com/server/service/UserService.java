package com.server.service;
import com.server.model.pojo.UserInfo;
import org.springframework.stereotype.Service;

import java.util.Map;

@Service
public interface UserService {
    public UserInfo getUser(String account);
    public UserInfo login(Map<String,String> map);
    public UserInfo getQuery();
}
