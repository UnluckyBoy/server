package com.server.model.mapper;

import com.server.model.pojo.ArticleInfo;
import com.server.model.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * Mapper接口
 */
@Mapper //mybatis的mapper类
@Repository //将mapper交由spring容齐管理
public interface UserMapper {
    public UserInfo infoQuery(Map<String,Object> map);//查询
    public UserInfo login(Map<String,Object> map);//登录
    public boolean register(Map<String,Object> map);//注册
    public List<UserInfo> fuzzyQuery(String name);//昵称查询
    public boolean upgptnumber(Map<String,Object> map) throws Exception;//更新可使用次数
    public boolean fresh_status_login(Map<String,Object> map);//更新登录信息
    public boolean fresh_status_logout(Map<String,Object> map);
    public boolean fresh_head(Map<String,Object> map);//更新头像

    //public List<ArticleInfo> get_all();//查询所有
}