package com.jay.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.config.MainConfigOfLifeCycle;

public class Test_LifeCycleTest {

	@Test
	public void test01() {
		//1.�Ы�IOC�e��
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfLifeCycle.class);
		System.out.println("�e���Ыا���...");
		
		//Car ����
		//applicationContext.getBean("car");
		
		//Cat 
		
		//�����e��
		applicationContext.close();
		
		
		
		
	}
}
