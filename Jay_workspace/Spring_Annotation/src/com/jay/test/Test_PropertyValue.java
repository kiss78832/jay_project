package com.jay.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.bean.Person;
import com.jay.config.MainConfigOfPropertyValues;

public class Test_PropertyValue {
	
	private void printBeans() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}
	
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfPropertyValues.class);
		
		System.out.println("==============================");
		
		Person person = (Person) applicationContext.getBean("person");
		System.out.println(person);
		applicationContext.close();
		
		
	}
}
