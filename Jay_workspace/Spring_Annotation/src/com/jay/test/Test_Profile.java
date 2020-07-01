package com.jay.test;

import javax.sql.DataSource;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;

import com.jay.bean.Import03;
import com.jay.config.MainConfigOfProfile;

public class Test_Profile {
	
	/*
	  *	改變環境測試方法一:
	  *		a). 在Main_test環境右鍵 run configuration -> Arguments 下 VM argumerts 輸入 -Dspring.profiles.active="hibernate_DB"
	 */	
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(MainConfigOfProfile.class);
		
		String[] nameForType = applicationContext.getBeanNamesForType(DataSource.class);
		for (String string : nameForType) {
			System.out.println("(Test_profile.java)取得Bean名稱 = "+string);
		}
		
		applicationContext.close();
	}
	
	
	/*
	  *	改變環境測試方法二:
	  *		a). 可以使用無參數AnnotationConfigApplicationContext自定義內容。
	 */	
	@Test
	public void test02() {
		
		//1.創建一個applicationContext(為了測試使用無參數)
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext();
		
		//2.設置需要啟動的環境(setActiveProfiles 可以設置多個環境)
		applicationContext.getEnvironment().setActiveProfiles("hibernate_DB","System_DB");
		
		//3.註冊主配置類別
		applicationContext.register(MainConfigOfProfile.class);
		
		//4.啟動刷新方法(AnnotationConfigApplicationContext有參數裡面有寫自動刷新)
		applicationContext.refresh();
		
		String[] nameForType = applicationContext.getBeanNamesForType(DataSource.class);
		for (String string : nameForType) {
			System.out.println("(Test_profile.java)取得Bean名稱 = "+string);
		}
		
		
		//測試無設置@profile也能取到值，代表無設置等於通用每個環境。
		Import03 import03 = applicationContext.getBean(Import03.class);
		System.out.println("(Test_profile.java)無設置@profile : "+import03);
		
		applicationContext.close();
	}
}
