package com.server.model.mapper;

import com.server.model.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口
 */
@Mapper //告诉springboot这是一个mybatis的mapper类
@Repository //将mapper交由spring容齐管理
public interface UserMapper {
    public UserInfo regiQuery(String account);//查询
    public UserInfo login(Map<String,String> map);//登录
    public boolean register(Map<String,String> map);//注册
    public List<UserInfo> fuzzyQuery(String name);//昵称查询
    public boolean upgptnumber(Map<String,Object> map) throws Exception;//更新可使用次数
}