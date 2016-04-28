package com.mendao.application;

import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport;

/**
 * spring mvc 配置项
 * 
 * @author HarrisonHan
 * @version $Id: WebMvcConfig.java, v 0.1 2015年6月30日 上午9:53:08 HarrisonHan Exp $
 */
//@Configuration
public class WebMvcConfig extends WebMvcConfigurationSupport {
    
    /**
     * 指定静态资源访问
     * @see org.springframework.web.servlet.config.annotation.WebMvcConfigurationSupport#addResourceHandlers(org.springframework.web.servlet.config.annotation.ResourceHandlerRegistry)
     */
    public void addResourceHandlers(ResourceHandlerRegistry registry) {
//        registry.addResourceHandler("/css/**").addResourceLocations("classpath:/static/css/");
//        registry.addResourceHandler("/js/**").addResourceLocations("classpath:/static/js/");
//        registry.addResourceHandler("/img/**").addResourceLocations("classpath:/static/img/");
//        registry.addResourceHandler("/fonts/**").addResourceLocations("classpath:/static/fonts/");
        //registry.addResourceHandler("/").addResourceLocations("classpath:/static/");
//        registry.addResourceHandler("/attached/**").addResourceLocations("classpath:/static/");
    }
}
