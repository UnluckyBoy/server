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
    public ArticleInfo query_id(int id);//通过id查询article
    public ArticleInfo get_file_content(Map<String,Object> map);//通过title和author查询

    public boolean up_Article_content(Map<String,Object> map);
}
