package com.mendao.interceptor;

import org.springframework.boot.autoconfigure.web.WebMvcAutoConfiguration.WebMvcAutoConfigurationAdapter;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;

@Configuration
public class ApplicationIntecepter extends WebMvcAutoConfigurationAdapter {

    /**
     * 配置拦截器
     * @author zhaolei
     * @param registry
     */
    public void addInterceptors(InterceptorRegistry registry) {
    	registry.addInterceptor(new SiteHandlerInterceptor()).addPathPatterns("/**");
    	registry.addInterceptor(new UserSecurityInterceptor()).addPathPatterns("/back/**", "/front/m/**");
	}
}
