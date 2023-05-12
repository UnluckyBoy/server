package com.server.controller;

import com.server.model.pojo.ArticleInfo;
import com.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Controller;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
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
        List<Object> articleList=new ArrayList<>();
        Map<String,Object> resultMap=new HashMap<>();
        articleList=articleService.get_all();
        resultMap.put("articles",articleList);
        return resultMap;
    }

    /***
     * 通过title和author获取content
     * @param title
     * @param author
     * @return
     */
    @RequestMapping("get_file_content")
    public Map GetContent(@RequestParam("title") String title,@RequestParam("author") String author){
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("title",title);
        requestMap.put("author",author);

        ArticleInfo articleInfo=articleService.get_file_content(requestMap);
        if(articleInfo!=null){
            switch (articleInfo.getmFileType()){
                case 1:
                    System.out.println("文本");
                    break;
                case 5:
                    System.out.println("音频");
                    break;
                case 9:
                    System.out.println("视频");
                    break;
            }
            resultMap=CommonResult(articleInfo);
        }

        return resultMap;
    }

    public Map CommonResult(ArticleInfo temp){
        /**
         * private int mId;
         * private String mTitle;
         * private String mCover;
         * private String mDescription;
         * private String mContent;
         * private String mAuthor;
         * private int mHot;
         * private String mType;
         * private int mFileType;
         **/
        Map<String,Object> result=new HashMap<>();
        result.put("id",temp.getmId());
        result.put("title",temp.getmTitle());
        result.put("cover",temp.getmCover());
        result.put("description",temp.getmDescription());
        result.put("content",temp.getmContent());
        result.put("author",temp.getmAuthor());
        result.put("hot",temp.getmHot());
        result.put("type",temp.getmType());
        result.put("fileType",temp.getmFileType());
        result.put("creatTime",temp.getmCreateTime());

        return result;
    }
}