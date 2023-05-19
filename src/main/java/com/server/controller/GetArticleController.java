package com.server.controller;

import com.fasterxml.jackson.databind.ObjectMapper;
import com.google.gson.Gson;
import com.google.gson.JsonElement;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import com.server.backTool.FileTool;
import com.server.backTool.ImageFileIOTool;
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
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import javax.servlet.http.HttpServletResponse;
import java.io.*;
import java.nio.charset.Charset;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
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
    private static String default_cover="default.png";

    private static String defaultType="趣闻";
    private static int defaultFileType=1;

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

    @RequestMapping("/up_article_content")
    public Map upArticleContent(@RequestBody String fileContent){
        Map<String,Object> resultMap=new HashMap<>();
        Map<String,Object> requestMap=new HashMap<>();
        System.out.println(TimeTool.GetTime(true)+"\t获取的内容:"+fileContent);

        Gson gson = new Gson();// 创建Gson对象
        JsonElement jsonElement = JsonParser.parseString(fileContent);// 将JSON字符串解析为JsonElement对象
        JsonObject jsonObject = jsonElement.getAsJsonObject();// 将JsonElement对象转换为JsonObject
//        String test=Json2String(jsonObject,"author");
//        String test2=Json2String(jsonObject,"title");
//        System.out.println(test+test2);
        LocalDateTime createTime = LocalDateTime.parse(TimeTool.GetTime(true), DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss"));
        String content=Json2String(jsonObject,"content");

        requestMap.put("title",Json2String(jsonObject,"title"));
        requestMap.put("cover",article_root_Path+article_cover_Path+Json2String(jsonObject,"cover"));
        requestMap.put("description",Json2String(jsonObject,"description"));

        String add_articleName=article_root_Path+article_content_Path+Json2String(jsonObject,"author")+"_"
                +Json2String(jsonObject,"title")+"_"+createTime.toString().replaceAll(":","-")+".txt";//Json2String(jsonObject,"content")
        requestMap.put("content",add_articleName);
        requestMap.put("author",Json2String(jsonObject,"author"));
        requestMap.put("hot",0);
        requestMap.put("type",defaultType);
        requestMap.put("filetype",defaultFileType);
        requestMap.put("date",createTime);

        System.out.println(requestMap.toString());
        String file=system_Path+add_articleName;

        boolean insert_key=articleService.up_Article_content(requestMap);
        if(insert_key){
            boolean write=FileTool.WriteContent(file,content);
            if(write){
                resultMap.put("result","success");
                System.out.println(TimeTool.GetTime(true)+"\t写入成功:"+file);
            }else{
                resultMap.put("result","error");
            }
        }else{
            resultMap.put("result","error");
        }
        return resultMap;
    }

    @RequestMapping("/article_image")
    public Map upArticleImage(@RequestParam("files") List<MultipartFile> files){
        Map<String,Object> resultMap=new HashMap<>();
        for(int i=0;i<files.size();i++){
            System.out.println("传输的文件:"+files.get(i).getOriginalFilename());
        }
        boolean save= ImageFileIOTool.writeImages(system_Path, article_root_Path+article_cover_Path,files);
        if(save){
            resultMap.put("result","success");
        }else{
            resultMap.put("result","error");
        }
        return resultMap;
    }

    @RequestMapping("/media")
    public void streamVideo(HttpServletResponse response,@RequestParam("mediaName")String mediaName) throws IOException {
        String videoPath = system_Path+article_root_Path+article_content_Path+mediaName; //根据视频文件名构建路径
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

    /***
     * 将json键值对取出
     * @param jsonObject
     * @param key
     * @return
     */
    public String Json2String(JsonObject jsonObject,String key){
        String result=null;
        result=jsonObject.get(key).isJsonPrimitive()?jsonObject.get(key).getAsString():jsonObject.get(key).toString();
        return result;
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