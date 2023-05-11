package com.server.service;
import com.server.model.pojo.ArticleInfo;
import com.server.model.pojo.UserInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service
public interface UserService {
    public UserInfo infoQuery(Map<String,Object> map);;
    public UserInfo login(Map<String,Object> map);
    public boolean register(Map<String,Object> map);
    public List<UserInfo> fuzzyQuery(String name);
    public boolean upgptnumber(Map<String,Object> map)throws Exception;
    public boolean fresh_status_login(Map<String,Object> map);//更新登录信息
    public boolean fresh_status_logout(Map<String,Object> map);//登出
    public boolean fresh_head(Map<String,Object> map);//更新头像

    //public List<ArticleInfo> get_all();//查询所有
}
