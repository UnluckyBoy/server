package com.server.service;
import com.server.model.pojo.UserInfo;
import org.springframework.stereotype.Service;

@Service
public interface UserService {
    public UserInfo getUser(String account);
    public UserInfo getQuery();
}
