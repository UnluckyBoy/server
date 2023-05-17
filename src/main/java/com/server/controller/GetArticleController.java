package com.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.server.backTool.FileTool;
import com.server.backTool.TimeTool;
import com.server.model.pojo.ArticleInfo;
import com.server.service.ArticleService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.core.io.ClassPathResource;
import org.springframework.core.io.Resource;
import org.springframework.core.io.ResourceLoader;
import org.springframework.http.MediaType;
import org.springframework.stereotype.Controller;
import org.springframework.util.StreamUtils;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.ResponseBody;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
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
    private static String article_root_Path="article/";
    private static String article_cover_Path="cover/";
    private static String article_content_Path="content/";

    @Autowired
    private ArticleService articleService;

    private ResourceLoader resourceLoader = null;

    @Autowired
    public GetArticleController(ResourceLoader resourceLoader) {
        this.resourceLoader = resourceLoader;
    }


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
    public Map GetContent(@RequestParam("title") String title,@RequestParam("author") String author) {
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> requestMap=new HashMap<>();
        requestMap.put("title",title);
        requestMap.put("author",author);

        ArticleInfo articleInfo=articleService.get_file_content(requestMap);
        if(articleInfo!=null){
            switch (articleInfo.getmFileType()){
                case 1:
                    System.out.println("文本");
                    String temp=FileTool.ReadFile(system_Path+articleInfo.getmContent());
                    System.out.println("文本内容:"+temp);
                    resultMap.put("article_content",temp);
                    break;
                case 5:
                    System.out.println("音频");
                    break;
                case 9:
                    System.out.println("视频");
                    break;
            }
            //resultMap=CommonResult(articleInfo);
        }

        return resultMap;
    }

    @RequestMapping("/get_video")
    //@GetMapping(value = "/videoName",params ="/{videoName}")
    public void streamVideo(HttpServletResponse response,String videoName) throws IOException {
        String videoPath = system_Path+article_root_Path+article_content_Path+videoName; //根据视频文件名构建路径
        File vide_file=new File(videoPath);
        System.out.println(TimeTool.GetTime(true)+"\t获取的文件"+vide_file+"\n是否文件:"+vide_file.exists());

        response.setContentType(MediaType.APPLICATION_OCTET_STREAM_VALUE);
        InputStream inputStream = new FileInputStream(vide_file);
        OutputStream outputStream = response.getOutputStream();

        byte[] buffer = new byte[1024];
        int bytesRead;
        while ((bytesRead = inputStream.read(buffer)) != -1) {
            outputStream.write(buffer, 0, bytesRead);
        }

        outputStream.flush();
        inputStream.close();
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