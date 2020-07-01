package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.PropertySource;

import com.jay.bean.Person;

//使用@PropertySource讀取外部配置文件中的key/value，保存到運行環境變量中。   @PropertySource底下方法有 String[] value();
@PropertySource(value= {"classpath:/person.properties"})
@Configuration
public class MainConfigOfPropertyValues {
	
	@Bean
	public Person person() {
		return new Person();
	}
}
