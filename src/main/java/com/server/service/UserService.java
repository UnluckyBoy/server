package com.server.service;
import com.server.model.pojo.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public UserInfo regiQuery(String account);
    public UserInfo login(Map<String,String> map);
    public boolean register(Map<String,String> map);
    public List<UserInfo> fuzzyQuery(String name);
}
