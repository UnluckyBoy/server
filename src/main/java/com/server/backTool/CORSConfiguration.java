package com.server.backTool;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.CorsRegistry;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

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
        //和页面有关的静态目录都放在项目的static目录下
        registry.addResourceHandler("/static/**").addResourceLocations("classpath:/static/");
        //其中getImage表示访问的前缀。"file:D:/BackResource/"是文件真实的存储路径
        registry.addResourceHandler("/getImage/**")
                .addResourceLocations("file:E:/MatrixProject/BackResource/");
    }
}
