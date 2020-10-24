package com.example.jay.appConfig;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;

import com.example.jay.service.HelloService;

@Configuration //指名當前的類別是一個配置類，是用來代替Spring配置文件[beans.xml]
public class MyAppConfig {

	//將方法的返回值添加到容器中，容器中這個元件 [默認的ID就是方法名稱] <- 依照bean.xml設置的Bean為方法名稱
	@Bean
	public HelloService helloService() {
		return new HelloService();
	}
}
