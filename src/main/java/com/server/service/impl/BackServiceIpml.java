package com.server.service.impl;

import com.server.model.mapper.ServerMapper;
import com.server.model.pojo.ArticleInfo;
import com.server.model.pojo.UserInfo;
import com.server.service.BackService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

@Service("UserService")
public class BackServiceIpml implements BackService {

    @Autowired
    private ServerMapper serverMapper;

    @Override
    public UserInfo infoQuery(Map<String,Object> map) {
        return serverMapper.infoQuery(map);
    }

    @Override
    public UserInfo login(Map<String,Object> map) {
        return serverMapper.login(map);
    }

    @Override
    public boolean register(Map<String, Object> map) {
        return serverMapper.register(map);
    }

    @Override
    public List<UserInfo> fuzzyQuery(String name) {
        return serverMapper.fuzzyQuery(name);
    }

    @Override
    public boolean upgptnumber(Map<String,Object> map) throws Exception{
        return serverMapper.upgptnumber(map);
    }

    @Override
    public boolean fresh_status_login(Map<String, Object> map) {
        return serverMapper.fresh_status_login(map);
    }

    @Override
    public boolean fresh_status_logout(Map<String, Object> map) {
        return serverMapper.fresh_status_logout(map);
    }

    @Override
    public boolean fresh_head(Map<String, Object> map) {
        return serverMapper.fresh_head(map);
    }

    @Override
    public List<ArticleInfo> get_all() {
        return serverMapper.get_all();
    }
}
