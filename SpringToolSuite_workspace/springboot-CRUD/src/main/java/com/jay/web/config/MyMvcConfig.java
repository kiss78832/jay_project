package com.jay.web.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.LocaleResolver;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.ViewControllerRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import com.jay.web.component.LoginHandlerInterceptor;
import com.jay.web.component.MyLocaleResolver;

@Configuration
public class MyMvcConfig implements WebMvcConfigurer{

	@Override
	public void addViewControllers(ViewControllerRegistry registry) {
		registry.addViewController("/web").setViewName("login.html");
		registry.addViewController("/index.html").setViewName("login.html");
		registry.addViewController("/main.html").setViewName("dashboard");
	}

	//註冊攔截器
	@Override
	public void addInterceptors(InterceptorRegistry registry) {
		//以前SpringMVC 要特別排除 *.css *.js ，但springBoot 針對靜態資源已經做好管理。
		//addPathPatterns -> 需要攔截哪些請求
		//excludePathPatterns -> 排除哪些請求。(如:首頁)
		registry.addInterceptor(new LoginHandlerInterceptor()).addPathPatterns("/**")
		.excludePathPatterns("/index.html","/","/user/login");
	}

	//把 MyLocaleResolver 交給 Spring 管理
	@Bean
	public LocaleResolver localeResolver() {
		return new MyLocaleResolver();
	}

}
