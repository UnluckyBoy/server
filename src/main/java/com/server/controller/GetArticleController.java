package com.server.controller;

import com.server.model.pojo.ArticleInfo;
import com.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.ResponseBody;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @ClassName GetArticleController
 * @Author Create By matrix
 * @Date 2023/5/11 0011 18:06
 */
@Controller
@ResponseBody
@RequestMapping("/article")
public class GetArticleController {
    private static String system_Path=System.getProperty("user.dir")+"/BackResource/backserver/";
    private static String article_info_Path="article/";
    private static String article_cover_Path="cover/";
    private static String article_content_Path="content/";

    @Autowired
    private ArticleService articleService;


    @RequestMapping( "/get_all")
    public Map GetAll(){
        List<ArticleInfo> articleList=new ArrayList<>();
        Map<String,Object> resultMap=new HashMap<>();
        articleList=articleService.get_all();
        resultMap.put("articles",articleList);
        return resultMap;
    }
}