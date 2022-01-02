package com.server.model.mapper;

import com.server.model.pojo.UserInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

/**
 * Mapper接口
 */
@Mapper //告诉springboot这是一个mybatis的mapper类
@Repository //将mapper交由spring容齐管理
public interface UserMapper {
    public UserInfo getUser(String account);
    public UserInfo getQuery();
}