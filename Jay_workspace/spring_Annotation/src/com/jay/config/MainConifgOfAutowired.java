package com.jay.config;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.context.annotation.Primary;

import com.jay.bean.Car;
import com.jay.bean.Import01;
import com.jay.dao.BookDao;

/*
 *	自動裝配(Autowired) -> Spring利用依賴注入(DI)，完成對IOC容器中各個元件的依賴關係賦值。
 *		一、@Autowired 自動注入 :
 *				a).默認優先按照類型去容器中找對應的元件 : applicationContext.getBean(BookDao.class)
 *	
 *				b).如果照到多個相同類型元件，會依照屬性名稱作為元件的ID去容器中查找。
 *					EX:Serivce.@Autowired.private BookDao "bookDao" <- [bookDao就是屬性名稱ID];
 *	
 *				c).@Qualifier("指定Bean名")，使用@Qualifier指定需要裝配元件的ID，而不是通過屬性名稱。
 *	
 *				d).Bean的流程 :
 *						先找Class(如果發現不是唯一)	 -> 屬姓名稱(有重複元件的話優先依照"屬性名稱ID")
 *						->若有@Qualifier就例外，依照@Qualifier指定的名稱
 * 	
 * 				e).默認一定要將屬性給賦值好，也就是@Autowired的Bean依定都要取的到值，若沒有會報錯。
 * 						除非再Bean上面新增 @Autowired(required = false) -> 取不到值會給null，不會報錯，EX:bookService測試 :BookService [bookDao=null]
 * 
 * 				f).@Primary，讓Spring進行自動裝配的時候，默認使用首選的bean也可以繼續使用@Qualifier指定需要裝配Bean的名子。
 * 						@Qualifier > @Primary > 默認屬性名稱ID。
 * 
 * 		二、Spring還支持使用@Resource(JSR250)和@Inject(JSR330) [Java規範，而不是Spring]
 * 				a).@Resource: 
 * 						可以和@Autowired一樣實現自動裝配，默認是按照元件屬性名稱進行裝配。
 * 							缺點 	: 不能支持@Primary功能沒有支持 @Autowired(required = false) 
 * 							優點	: 沒Spring框架依然可以活，因為是Java規範
 * 				
 * 				b).@Inject
 * 						可以和@Autowired一樣實現自動裝配。但有支持@Primary功能
 * 							缺點 : 沒有支持@Autowired(required = false)、需要導入javax.inject的Jar包
 * 							優點 : 沒Spring框架依然可以活，因為是Java規範
 * 
 * 				c).AutowiredAnnotationBeanPostProcessor : 解析完成自動裝配功能。(@Resource、@Inject、@Autowired)
 * 
 * 		三、@Autowired:建構子、參數、方法、屬性，都是從容器中獲取參數元件的值
 * 				a).標註在方法上 (MainConfigOfAutowired.java)
 * 					注意:																				@Bean
 * 						1.@Bean + 方法參數(Car car) ，參數從容器中獲取，默認不寫@Autowired+@Component效果也是一樣的，都能自動裝配。EX: public Import01 import01(Car car)
 * 				b).標註在建構子 (Boss.java)
 * 					注意:
 * 						1.@Autowired 默認優先取得無參建構子(不用加@Autowired，會自動默認)，若無參跟有參都存在，但@Autowired放在有參數建構子，會優先取得有參數建構子。
 * 						2.@Autowired 若只有一個有參數建構子，就會優先取得該建構子。(不用加@Autowired，會自動默認)
 * 				c).放在參數位置。
 * 
 * 		四、自定義元件想要使用Spring容器底層的一些元件(ApplicationContext,BeanFactory,XXX)，
 * 					自定義元件實作(implements)  XXXAware，在創建物件的時候，會調用介面規定的方法注入相關元件Aware，
 * 						把Spring底層一些元件注入到自己Bean中，利用XXXAware,功能使用XXXProcessor:
 * 									EX:ApplicationContextAware ==> ApplicationContextAwareProcessor
 * 
 * 			實作Aware介面 :
 *					BeanNameAware ：可以獲取容器中bean的名稱
 *					BeanFactoryAware:獲取當前bean factory這也可以調用容器的服務
 *					ApplicationContextAware： 當前的applicationContext， 這也可以調用容器的服務
 *					MessageSourceAware：獲得message source，這也可以獲得文本信息
 *					applicationEventPulisherAware：應用事件發佈器，可以發布事件，
 *					ResourceLoaderAware： 獲得資源加載器，可以獲得外部資源文件的內容
 *					...等等
 *			
 *			Bean生命週期:
 * 			★★★  實例化(new Object) -> 填充屬性(注入bean的依賴或者給屬性賦值[setXXX]) -> 執行Aware接口(執行所有實作Aware介面的類別)
 * 						 -> 初始化(初始化的方式有三種) -> 可用狀態(bean可以被應用程式使用) -> 銷毀(銷毀的方式有三種)
 * 
 * 			初始化的方式有三種:(1).實現InitializingBean介面  (2).@PostConstruct註解  (3).xml中通過init-method屬性指定初始化方法
 * 			銷毀的方式有三種:(1).實現DisposableBean介面  (2).@PreDestroy註解  (3).xml中通過destroy-method屬性指定銷毀方法
 * 
 * 
 * 
 * 
 * 			★★★ 當@Configuration的@ComponentScan({})內有掃到到package，那麼在加載Spring配置文件時，會自動調用實作Aware接口中的。
 * 			
 */

@Configuration
@ComponentScan({"com.jay.service","com.jay.dao","com.jay.controller","com.jay.bean"})
public class MainConifgOfAutowired {

	@Primary
	@Bean(name = "bookDao2")
	public BookDao bookDao() {
		BookDao bookDao = new BookDao();
		bookDao.setLable("2");
		return bookDao;
	}
	
	//@Bean 標註的方法創建物件的時候，方法參數的值從容器中獲取
	@Bean
	public Import01 import01(Car car) {
		Import01 import01 = new Import01();
		import01.setCar(car);
		return import01;
	}
	
}
