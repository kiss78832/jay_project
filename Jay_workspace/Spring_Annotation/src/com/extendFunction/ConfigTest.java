package com.extendFunction;

import org.junit.Test;
import org.springframework.context.ApplicationEvent;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

public class ConfigTest {

	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(ExtConfig.class);
		
		applicationContext.publishEvent(new ApplicationEvent(new String("�ڵo�����ƥ�")) {
		});
		
		applicationContext.close();
	}
	
	
}
	
