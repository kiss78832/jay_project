package com.jay.web.config;

import java.util.Arrays;

import org.springframework.boot.web.server.WebServerFactoryCustomizer;
import org.springframework.boot.web.servlet.FilterRegistrationBean;
import org.springframework.boot.web.servlet.ServletListenerRegistrationBean;
import org.springframework.boot.web.servlet.ServletRegistrationBean;
import org.springframework.boot.web.servlet.server.ConfigurableServletWebServerFactory;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.jay.web.customizeServlet.MyFilter;
import com.jay.web.customizeServlet.MyListener;
import com.jay.web.customizeServlet.MyServlet;

@Configuration
public class MyServletConfig {

	@Bean
	public WebServerFactoryCustomizer<ConfigurableServletWebServerFactory> webServerFactoryCustomizer(){

		return new WebServerFactoryCustomizer<ConfigurableServletWebServerFactory>() {
			/* 實作自定義方法， */
			@Override
			public void customize(ConfigurableServletWebServerFactory factory) {
				factory.setPort(8088);
			}
		};
	}

	/* 註冊三大元件 ServletRegistrationBean、FilterRegistrationBean、ServletListenerRegistrationBean*/
	@Bean
	public ServletRegistrationBean<MyServlet> myServlet() {
		ServletRegistrationBean<MyServlet> registrationBean = new ServletRegistrationBean<MyServlet>(new MyServlet(),"/myServlet");
		registrationBean.setLoadOnStartup(1);
		return registrationBean;
	}

	@Bean
	public FilterRegistrationBean<MyFilter> myFilter() {
		FilterRegistrationBean<MyFilter> registrationBean = new FilterRegistrationBean<MyFilter>();
		registrationBean.setFilter(new MyFilter());
		registrationBean.setUrlPatterns(Arrays.asList("/hello","/myServlet"));
		return registrationBean;
	}

	@Bean
	public ServletListenerRegistrationBean<MyListener> myListener(){
		ServletListenerRegistrationBean<MyListener> registrationBean = new ServletListenerRegistrationBean<MyListener>(new MyListener());
		return registrationBean;
	}
}
