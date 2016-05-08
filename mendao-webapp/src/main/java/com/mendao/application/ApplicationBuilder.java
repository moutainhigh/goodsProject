package com.mendao.application;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.EnableAutoConfiguration;
import org.springframework.boot.builder.SpringApplicationBuilder;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.boot.orm.jpa.EntityScan;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.data.jpa.repository.config.EnableJpaRepositories;
import org.springframework.scheduling.annotation.EnableScheduling;

import com.mendao.framework.base.jpa.BaseRepositoryFactoryBean;
import com.mendao.util.FrontSetting;

@EnableScheduling
@EnableAutoConfiguration
@ComponentScan("com.mendao")
@EntityScan("com.mendao.*.entity")
//指定JPADAO工作目录。自定义工厂Bean以实现全局DAO扩展
@EnableJpaRepositories(basePackages = "com.mendao.*.repository",repositoryFactoryBeanClass = BaseRepositoryFactoryBean.class)
@EnableConfigurationProperties({FrontSetting.class})
public class ApplicationBuilder extends SpringBootServletInitializer{
		
	protected SpringApplicationBuilder configure(SpringApplicationBuilder application) {
		return application.sources(ApplicationBuilder.class);
	}
	
    public static void main(String[] args) {
		SpringApplication.run(ApplicationBuilder.class, args);
	} 

    
}