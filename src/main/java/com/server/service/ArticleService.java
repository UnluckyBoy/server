package com.server.service;

import com.server.model.pojo.ArticleInfo;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleService
 * @Author Create By matrix
 * @Date 2023/5/11 0011 18:35
 */
@Service
public interface ArticleService {
    public List get_all();//查询所有
    public List<ArticleInfo> get_article_limit(Map<String,Object> map);//查询部分
}
