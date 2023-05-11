package com.server.service.impl;

import com.server.model.mapper.ArtMapper;
import com.server.model.pojo.ArticleInfo;
import com.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;

/**
 * @ClassName ArticleServiceIpml
 * @Author Create By matrix
 * @Date 2023/5/11 0011 18:35
 */
@Service("ArticleService")
public class ArticleServiceIpml implements ArticleService {
    @Autowired
    private ArtMapper artMapper;
    @Override
    public List get_all() {
        return artMapper.get_all();
    }

    @Override
    public List<ArticleInfo> get_article_limit(Map<String, Object> map) {
        return artMapper.get_article_limit(map);
    }
}
