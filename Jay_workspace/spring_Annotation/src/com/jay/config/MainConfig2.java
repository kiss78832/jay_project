package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Conditional;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Import;
import org.springframework.context.annotation.Lazy;
import org.springframework.context.annotation.Scope;

import com.jay.bean.Import01;
import com.jay.bean.Import02;
import com.jay.bean.ImportFactoryBean;
import com.jay.bean.Person;
import com.jay.condition.LinuxCondition;
import com.jay.condition.MyImportBeanDefinitionRegistrar;
import com.jay.condition.MyImportSelector;
import com.jay.condition.WindowsCondition;

/*
 *	 容器註冊元件方式 :
 *		(1).Package掃描 + 元件標示注解(@Controller/@Service/@Repository/@Component)
 * 		(2).@Bean[導入第三方元件]，第三方元件不會幫你注解@Component等等...
 * 		(3).@Import[快速給容器中導入元件]
 * 			 (一).@Import(要導入到容器中的元件) : 容器中就會自動註冊這個元件，ID默認完整類名路徑
 * 			 (二).ImportSelector:返回需要導入的元件的完整類名陣列(參考MyImportSelector.java)
 * 			 (三).ImportBeanDefinitionRegistrar : 手動註冊Bean到容器中 (參考MyImportBeanDefinitionRegistrar.java)
 * 		(4).使用Spring提供的FactoryBean(工廠Bean)
 * 			 (一).默認獲取到是工廠Bean呼叫getObject()創建的物件
 * 			 (二).要獲取工廠Bean本身，我們需要給ID前面一個前綴符"&"，就會獲得工廠Bean本身
 */
@Import({Import01.class,Import02.class,MyImportSelector.class,MyImportBeanDefinitionRegistrar.class}) //第一種Import方式
@Conditional({WindowsCondition.class})
@Configuration
public class MainConfig2 {
	
	/*
	 *	@Scope(調整作用域) :
	 * 		(1).prototype : 多實例，每次都會創建一個新的Bean (常用)
	 * 		(2).singleton : 單實例，只會重複拿同一個Bean (常用)(默認值)
	 * 		(3).request : 同一次請求創建一個實例(不常用)
	 * 		(4).session : 同一個session創建一個實例(不常用)
	 * 
	 *	@Lazy(懶加載) :
	 *		(1)."Lazy"只會搭配 "singleton。
	 */
	
	@Scope
	@Lazy
	@Bean(name = "person")
	public Person person00() {
		//註解
		System.out.println("(MainConfig2.java)給容器中添加Person...");
		return new Person("張三",25);
	}
	
	/*
	 *	@Conditional(條件) 按照一定的條件進行判斷，滿足條件給容器中註冊Bean
	 *		(1).可自己寫一個類別來驗證，該類別記得要 extends Conditional ，再利用@Conditional就能進行驗證。
	 *		(2).流程:
	 *				使用到Bean的時候 	-> 訪問該@Bean	-> 透過@Conditional若回傳true就創建該Bean、若false就不創建該Bean。
	 *		(3).也可以配在類別上(MainConfig2)，代表條件沒過就不會創建該類別的Bean
	 */
	@Conditional({WindowsCondition.class}) 
	@Bean(name = "bill")
	public Person person01() {
		System.out.println("(MainConfig2.java)創建bill_Bean容器");
		return new Person("Bill Gates",62);
	}
	
	@Conditional(LinuxCondition.class)
	@Bean(name = "linus")
	public Person person02() {
		System.out.println("(MainConfig2.java)創建linus_Bean容器");
		return new Person("linus",48);
	}
	
	//FactoryBean
	@Bean
	public ImportFactoryBean importFactoryBean() {
		return new ImportFactoryBean();
		
	}
}
