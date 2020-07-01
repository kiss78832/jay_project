package com.jay.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.bean.Import03;
import com.jay.config.MainConfigOfProfile;

public class Test_Profile {
	
	/*
	  *	�������Ҵ��դ�k�@:
	  *		a). �bMain_test���ҥk�� run configuration -> Arguments �U VM argumerts ��J -Dspring.profiles.active="hibernate_DB"
	 */	
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
		
		String[] nameForType = applicationContext.getBeanNamesForType(DataSource.class);
		for (String string : nameForType) {
			System.out.println("(Test_profile.java)���oBean�W�� = "+string);
		}
		
		applicationContext.close();
	}
	
	
	/*
	  *	�������Ҵ��դ�k�G:
	  *		a). �i�H�ϥεL�Ѽ�AnnotationConfigApplicationContext�۩w�q���e�C
	 */	
	@Test
	public void test02() {
		
		//1.�Ыؤ@��applicationContext(���F���ըϥεL�Ѽ�)
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		
		//2.�]�m�ݭn�Ұʪ�����(setActiveProfiles �i�H�]�m�h������)
		applicationContext.getEnvironment().setActiveProfiles("hibernate_DB","System_DB");
		
		//3.���U�D�t�m���O
		applicationContext.register(MainConfigOfProfile.class);
		
		//4.�Ұʨ�s��k(AnnotationConfigApplicationContext���ѼƸ̭����g�۰ʨ�s)
		applicationContext.refresh();
		
		String[] nameForType = applicationContext.getBeanNamesForType(DataSource.class);
		for (String string : nameForType) {
			System.out.println("(Test_profile.java)���oBean�W�� = "+string);
		}
		
		
		//���յL�]�m@profile�]�����ȡA�N��L�]�m����q�ΨC�����ҡC
		Import03 import03 = applicationContext.getBean(Import03.class);
		System.out.println("(Test_profile.java)�L�]�m@profile : "+import03);
		
		applicationContext.close();
	}
}
