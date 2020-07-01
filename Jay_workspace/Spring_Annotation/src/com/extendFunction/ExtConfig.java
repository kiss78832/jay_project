package com.extendFunction;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;

import com.jay.bean.Car;


/*
 *	擴展原理 :
 *	● BeanFactoryPostProcessor
 *	● BeanDefinitionRegistryPostProcessor
 *	● ApplicationListener
 *
 *	BeanPostProcessor:bean後置處理器，bean創建物件初始化前後進行攔截工作的。
 *	1.在BeanFactory標準初始化之後調用，來制定和修改BeanFactory的內容。
 *	2.所有Bean定義已經保存加載到beanFactory，但是bean的實例還為創建。
 *	
 *	
 *	▼ BeanFactoryPostProcessor:beanFactory的後置處理器
 *	原理:
 *	1.IOC容器創建物件
 *	2.invokeBeanFactoryPostProcessors(beanFactory) 執行[BeanFactoryPostProcessor]
 *	     如何照到所有BeanFactoryPostProcessor並執行他們的方法
 *	  	1).直接在BeanFactory中找到所有類型是BeanFactoryPostProcessor的元件，並執行他們的方法
 * 	  	2).在初始化創建其他元件前面執行
 * 
 *	▼ BeanDefinitionRegistryPostProcessor extends BeanFactoryPostProcessor
 *	-> postProcessBeanDefinitionRegistry():在所有的Bean定義訊息將要被加載，Bean實例還未創建
 *	1.實作BeanDefinitionRegistryPostProcessor，需實作 postProcessBeanDefinitionRegistry()、繼承類的postProcessBeanFactory()
 *	2.優先於BeanFactoryPostProcessor，先做完BeanDefinitionRegistryPostProcessor再來做BeanFactoryPostProcessor。
 *	3.可以利用BeanDefinitionRegistryPostProcessor去給容器添加一些元件、Bean。
 *	原理:
 *		1.IOC創建物件
 *		2.refresh() -> invokeBeanFactoryPostProcessors(beanFactory);
 *		3.從容器中獲取到所有的BeanDefinitionRegistryPostProcessor元件
 *			1).依次觸發所有的postProcessBeanDefinitionRegistry()。
 *			2).再觸發postProcessBeanFactory()的BeanFactoryPostProcessor
 *		4.再從容器中找到BeanFactoryPostProcessor元件，然後依次觸發postProcessBeanFactory()方法
 *
 *	▼ ApplicationListener :監聽容器中發布的事件，事件驅動模型開發。
 *		public interface ApplicationListener<E extends ApplicationEvent> -> 監聽ApplicationEvent及其下面的子事件:
 *	1. 監聽四個事件繼承ApplicationContextEvent and ApplicationEvent
 *		1).ContextRefreshedEvent 容器刷新就會發布事件
 *		2).ContextClosedEvent 容器關閉就會發布事件
 *		3).ContextStartedEvent 容器開始就會發布事件
 *		4).ContextStoppedEvent 容器停止就會發布事件
 *		.......extends ApplicationContextEvent extends ApplicationEvent
 *	2.步驟 :
 *		1).寫一個監聽器(ApplicationListener實現類)來監聽某事件(ApplicationEvent及其子類)
 *		2).把監聽器加入到容器
 *		3).只要容器中有相關事件的發布，我們就能監聽到這個事件。[刷新、關閉、開始、停止]
 *		4).發布一個事件:
 *			applicationContext.publishEvent()
 *	
 *	原理: [ContextRefreshedEvent、ConfigTest$1[source=我發布的事件]、ContextClosedEvent]
 *		1.ContextRefreshedEvent事件
 *			A.容器創建物件:refresh()。
 *			B.finishRefresh(); 容器刷新完成
 *			C.publishEvent(new ContextRefreshedEvent(this));
 *		2.自己發布事件
 *
 *		內部發布的事件跟自己發布的事件都會通過[事件發布流程]。
 *				【事件發布流程】:
 *					a).把事件發送到各監聽器，獲取事件的多播器(派發器):getApplicationEventMulticaster()
 *					b).multicastEvent派發事件
 *					c).獲取到所有的ApplicationListener:
 *							for(final ApplicationListener<?> listener : getApplicationListeners(event,type))
 *								1.如果有Executor，可以支持使用Executor進行異步派發;
 *									Executor executor = getTaskExecutor();
 *								2.否則，同步的方式直接執行listener方法，invokeListener(listener,event);
 *								    拿到listener回傳onApplicationEvent方法
 * 
 */
@ComponentScan("com.extendFunction")
@Configuration
public class ExtConfig {

	@Bean
	public Car car() {
		return new Car();
	}
}
