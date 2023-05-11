package com.server.model.mapper;

import com.server.model.pojo.ArticleInfo;
import org.apache.ibatis.annotations.Mapper;
import org.springframework.stereotype.Repository;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ArtMapper
 * @Author Create By matrix
 * @Date 2023/5/11 0011 18:33
 */
@Mapper
@Repository
public interface ArtMapper{
    public List<ArticleInfo> get_all();//查询所有
    public List<ArticleInfo> get_article_limit(Map<String,Object> map);//查询部分
}
