package com.jay.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.config.MainConfigOfLifeCycle;

public class Test_LifeCycleTest {

	@Test
	public void test01() {
		//1.創建IOC容器
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
		System.out.println("容器創建完成...");
		
		//Car 測試
		//applicationContext.getBean("car");
		
		//Cat 
		
		//關閉容器
		applicationContext.close();
		
		
		
		
	}
}
