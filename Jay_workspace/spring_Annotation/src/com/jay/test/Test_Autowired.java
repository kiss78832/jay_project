package com.jay.test;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.bean.Boss;
import com.jay.bean.Car;
import com.jay.bean.Import01;
import com.jay.config.MainConifgOfAutowired;
import com.jay.service.BookService;

public class Test_Autowired {
	
	private void printBeans() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConifgOfAutowired.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();
		for (String name : definitionNames) {
			System.out.println(name);
		}
	}

	//測試import02，看是否有成功注入Spring管理
	@Test
	public void test() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConifgOfAutowired.class);
		System.out.println(applicationContext);
		applicationContext.close();
	}
		
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConifgOfAutowired.class);

		BookService bookService = applicationContext.getBean(BookService.class);
		System.out.println("(Test_Autowired.java)bookService測試 :"+bookService);
		
//		BookDao bean = applicationContext.getBean(BookDao.class);
//		System.out.println("bean測試 :"+bean);
		
		Boss boss = applicationContext.getBean(Boss.class);
		Car car = applicationContext.getBean(Car.class);
		System.out.println("(Test_Autowired.java)Boss的Car是否跟容器中的Car一樣? boss = "+boss+", Car = "+car);
		
		Import01 import01 = applicationContext.getBean(Import01.class);
		System.out.println("(Test_Autowired.java)測試Import01的Car是否也是一樣 = "+import01);
		
		applicationContext.close();
	}
	
}
