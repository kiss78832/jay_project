package com.jay.config;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jay.bean.Car;

/*
 *	Bean的生命週期 : ( bean創建 ------> 初始化 ------> 銷毀過程 )
 *		
 *	容器管理Bean的生命週期  :  (生命週期幾乎都可以控制)
 *		(1).我們可以自定義初始化和銷毀方法，容器在Bean進行到當前生命週期的時候調用我們自定義的初始化和銷毀
 *		(2).建構子創建:
 *				singleton:在容器啟動的時候創建。
 *				prototype:在呼叫該Bean時創建。
 *
 *		★BeanPostProcessor.PostProcessBeforeInitialization() [初始化之前觸動]
 *
 *		(3).初始化:(A~C三種方式)
 *				建構子創建完成，並附值好  ------> 呼叫初始化方法 ...
 *
 *		★BeanPostProcessor.postProcessAfterInitialization() [銷毀之前觸動]
 *
 *		(4).銷毀:
 *				singleton:容器關閉的時候
 *				prototype:容器不會管理這個Bean，容器不會調用 destory() method
 *		
 *
 *	定義初始化與銷毀方式:
 * 		(A).方法一 :指定初始化和銷毀方法
 * 			  (a).<bean id="person" class="com.jay.bean.Person" init-method="" destroy-method=""> XML寫法
 * 			  (b).@Bean(initMethod = "init" , destroyMethod = "destory")  annotation寫法
 * 		(B).方法二 :通過Bean實作  
 * 				initializingBean (定義初始化邏輯)
 * 				DisposableBean (定義銷毀邏輯)
 * 		(C).方法三 :可以使用JSR250	[比較推薦的方式，簡單清晰]
 * 				@PostConstruct:在Bean的方法注解@PostConstruct，等同init-method，物件初始化時會呼叫該方法。
 * 				@PreDestroy:在Bean的方法注解@PreDestroy，等同destroy-method，物件銷毀時會呼叫該方法。 
 * 				注意 : 記得幫該Bean交給Spring管理，註冊@Component
 *  	(D).BeanPostProcessor[interface],bean的後置處理器     
 * 				     	在bean初始化前後進行一些處理工作 :	
 *						PostProcessBeforeInitialization :在初始化之前的工作。 
 *						postProcessAfterInitialization :在初始化之後的工作。
 *		
 *		(E).BeanPosstProcessor原理(SouceCode):
 *			populateBean(beanName, mbd, instanceWrapper); //給Bean進行賦值
 *
 *			//進行初始化，三步驟
 *			exposedObject = initializeBean(beanName, exposedObject, mbd){
 *				
 *				//initializeBean內部
 *				wrappedBean = applyBeanPostProcessorsBeforeInitialization(wrappedBean, beanName);
 *				invokeInitMethods(beanName, wrappedBean, mbd); //自定義初始化方法
 *				wrappedBean = applyBeanPostProcessorsAfterInitialization(wrappedBean, beanName);
 *			}
 *			
 *		(F).Spring底層對BeanPostProcessor的使用(大量使用) : 
 *				(1).Bean的賦值
 *				(2).注入其他元件(@Component)
 *				(3).@AutoWired
 *				(4).生命週期注解功能
 *				(5).@Async(不同步處理)
 *						......等等
 */

//示範不同方式引進Bean，不用@Bean
@Configuration
@ComponentScan("com.jay.bean")
public class MainConfigOfLifeCycle {
	
	@Bean(initMethod = "init" , destroyMethod = "destory") //指定 初始化方法ID=init, 銷毀方法ID=destory
	public Car car() {
		return new Car();
	}
	
}
