package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.ComponentScan.Filter;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.FilterType;
import org.springframework.stereotype.Controller;

import com.jay.bean.Person;
import com.jay.service.BookService;

//配置類別(.java) = 配置文件(xml)

@Configuration //此註解告訴Spring這是一個配置類別
@ComponentScan(value = "com.jay",includeFilters = {
		/*
		 * 在配置類別中註冊一個掃描器，@ComponentScan(value = 指定要掃描的package,[excludeFilters(排除指定的package),includeFilters(包含指定package)]
		 * 		(1).原始碼:Filter[] excludeFilters() default {} -> 所以是一個Filter[]陣列，可以用大括號{}處理多個邏輯。
		 * 		(2).@Filter(type = FilterType.(你要排除的類型),classes = {該類型的名稱})
		 * 		(3).若要用"includeFilters"，記得把@ComponentScan底下useDefaultFilters預設"true"改"false"。
		 * 		(4).Java8 以後可以直接使用多個@ComponentScan去做設定，Java8以前請用@ComponentScans(底下可以寫多個@ComponentScan)。
		 * 		(5).FilterType常用類型:
		 * 				ANNOTATION = 按照註解 (常用)
		 * 				ASSIGNABLE_TYPE = 依照指定的類別 (常用)
		 * 				ASPECTJ = 使用ASPECTJ表達式
		 * 				REGEX = 正則表達式
		 * 				CUSTOM = 自定義規則 (必須寫FilterType實作類別)
		 */
		@Filter(type = FilterType.ANNOTATION,classes = {Controller.class}),
		@Filter(type = FilterType.ASSIGNABLE_TYPE,classes = {BookService.class})
		
		//@Filter(type = FilterType.CUSTOM,classes = {MyTypeFilter.class}) //測試自定義Filter
},useDefaultFilters = false) 
public class MainConfig {
	
	/*
	 *	@Bean :
	 *		(1).給容器註冊一個Bean，類型(Person)為返回值類型，id默認是方法名稱(person)，此Bean都是單例的，不管呼叫多少次都是同一個實體。
	 *		(2).若需要幫@Bean命名，@Bean(name = "自定義名稱")
	 */
	@Bean 
	public Person person() {
		return new Person("lisi",20);
	}
}
