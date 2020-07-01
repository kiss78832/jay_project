package com.connection;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

@ComponentScan("com.connection")
@Configuration
public class ConfigTest {

	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ConfigTest.class);
		
		UserService userService = applicationContext.getBean(UserService.class);
		userService.insertUser();
		
		applicationContext.close();
		
	}
	
	
}
	
