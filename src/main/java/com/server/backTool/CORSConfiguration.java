package com.server.backTool;

import org.springframework.boot.system.ApplicationHome;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

import java.io.File;

/**
 * 配置类:
 * 解决跨域问题配置类Cors
 * 用于读取图片
 */
@Configuration
public class CORSConfiguration extends WebMvcConfigurationSupport {

    /**
     * 解决跨域问题配置类Cors
     * @param registry
     */
    @Override
    public void addCorsMappings(CorsRegistry registry) {
        registry.addMapping("/**")
                .allowedMethods("*")
                .allowedOrigins("*")
                .allowedHeaders("*");
        super.addCorsMappings(registry);
    }

    /**
     * 用于读取图片
     * @param registry
     */
    @Override
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
        String dirPath=System.getProperty("user.dir")+"/BackResource/";

//        //获取jar包所在目录
//        ApplicationHome h = new ApplicationHome(getClass());
//        File jarF = h.getSource();
//        //在jar包所在目录下生成一个upload文件夹用来存储上传的图片
//        String dirPath = jarF.getParentFile().toString()+"/BackResource/";
//        System.out.println(dirPath);
//        File fileMkdir = new File(dirPath);
//        if (!fileMkdir.exists()){
//            fileMkdir.mkdir();
//        }
        System.out.println(dirPath);

        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //其中getImage表示图片资源访问的前缀。"file:E:/MatrixProject/BackResource/"是服务器文件真实的存储路径
        registry.addResourceHandler("/getImage/**")
                .addResourceLocations("file:"+dirPath);//file:E:/MatrixProject/BackResource/
    }
}
