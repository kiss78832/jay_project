package com.jay.test;

import java.util.Map;

import org.junit.Test;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import org.springframework.core.env.ConfigurableEnvironment;

import com.jay.bean.Import01;
import com.jay.bean.ImportFactoryBean;
import com.jay.bean.Person;
import com.jay.config.MainConfig;
import com.jay.config.MainConfig2;

public class Test_MainTest {

	public static void main(String[] args) {
		/*
		 * 使用XML註冊寫法 ApplicationContext applicationContext = new
		 * ClassPathXmlApplicationContext("beans.xml"); Person bean =
		 * (Person)applicationContext.getBean("person"); System.out.println(bean);
		 */

		// 使用annotation方式寫法
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig.class);
		Person bean = applicationContext.getBean(Person.class);
		System.out.println(bean);

		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			System.out.println(name);
		}
	}

	@SuppressWarnings("resource")
	@Test
	public void test01() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();

		for (String name : definitionNames) {
			// 印出被Spring控制的組件
			System.out.println("被Spring控制的元件 : " + name);
		}
	}

	@Test
	public void test02() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		String[] definitionNames = applicationContext.getBeanDefinitionNames();

		for (String name : definitionNames) {
			// 印出被Spring控制的組件
			System.out.println("被Spring控制的元件 : " + name);
		}

		//測試標示 @Bean 的類別默認都是"單例"
		Object bean01 = applicationContext.getBean("person");
		Object bean02 = applicationContext.getBean("person");
		System.out.println("是否為單實例 : "+ bean01.hashCode()+ " , "+ bean02.hashCode() );
		
	}
	
	/*
	 *	測試@Bean為singleton(單例)時 : 
	 *		(1).IOC容器啟動時會調用方法創建物件(Person)放進IOC容器中，以後每次獲取就是"從容器中拿"(就是map.get()概念)。
	 *
	 *	測試@Bean為 prototype(多例) 時，當使用到該Bean的時候才會創建該實例。
	 *		(1).IOC容器啟動並不會去調用方法創建物件在容器中，每次獲取的時候才會調用方法創建物件。
	 */
	@Test
	public void test03() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		System.out.println("測試 - IOC容器創建");
		//prototype會連續創建兩個Person，而singleton只會創建一個。
		Object bean01 = applicationContext.getBean("linus"); //創建容器
		Object bean02 = applicationContext.getBean("person"); //創建容器
		}
	
	/*
	 *	@Lazy懶加載 :
	 * 		(1)."Lazy" 只會搭配 "singleton" 。
	 * 		(2)."Lazy" -> 本身是代理事務處理，所以當需要Bean的時候才會創建。  singleton -> 重複使用第一次創建的物件。
	 */
	@Test
	public void test04() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		System.out.println("測試 - IOC容器創建");
		Object bean01 = applicationContext.getBean("person"); //創建容器
		Object bean02 = applicationContext.getBean("person"); //創建容器
	}
	
	/*
	 *	applicationContext.getBeanNamesForType() -> 取得Person類別的@Bean的名稱
	 *	applicationContext.getBeansOfType() -> 取得Person類別實體，返回Map集合(key=ID,value=Person實體)
	 *	applicationContext.getEnvironment() -> 可以查看環境變量的值。
	 *
	 */
	@Test
	public void test05() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		String[] namesForType = applicationContext.getBeanNamesForType(Person.class);
		for (String name : namesForType) {
			System.out.println(name);
		}
		
		Map<String,Person> persons = applicationContext.getBeansOfType(Person.class);
		System.out.println(persons);
		
		ConfigurableEnvironment environment = applicationContext.getEnvironment();
		String property = environment.getProperty("os.name");
		System.out.println("查看作業系統 : "+property);
	}
	
	
	/*
	 *	Import範例測試用
	 *	測試注解在MainConfig2類別的@Import是否有成功
	 */
	@Test
	public void test06() {
		AnnotationConfigApplicationContext applicationContext = new AnnotationConfigApplicationContext(
				MainConfig2.class);
		
		Import01 import01 = applicationContext.getBean(Import01.class);
		System.out.println("確認物件是否存在 : "+import01);
		
		//呼叫ImportFactoryBean 範例
		Object bean01 = applicationContext.getBean("importFactoryBean");//按照ID獲取Bean
		Object bean02 = applicationContext.getBean("importFactoryBean");
		System.out.println("Bean的類型 : " + bean01.getClass()); //FactoryBean獲取的是呼叫getObject()創建物件
		System.out.println("Bean是否同一個 ? "+bean01.hashCode() +","+ bean02.hashCode());
		//呼叫ImportFactoryBean 範例02
		Object bean03 = applicationContext.getBean("&importFactoryBean"); //若+"&"前綴符得到ImportFactoryBean的類別，並不是getObject，是工廠Bean
		System.out.println("Bean03的類型"+bean03.getClass());
		
		String[] applicationName = applicationContext.getBeanDefinitionNames();
		for (String name : applicationName) {
			System.out.println("被Spring控管元件 : "+name);
		}
		
	}
	
}
