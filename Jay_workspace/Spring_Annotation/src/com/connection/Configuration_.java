package com.connection;

import javax.sql.DataSource;

import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.ComponentScan;
import org.springframework.context.annotation.Configuration;
import org.springframework.jdbc.core.JdbcTemplate;
import org.springframework.jdbc.datasource.DataSourceTransactionManager;
import org.springframework.transaction.PlatformTransactionManager;
import org.springframework.transaction.annotation.EnableTransactionManagement;

import com.mchange.v2.c3p0.ComboPooledDataSource;

/*
 * 	聲明事務:
 * 
 * 	環境搭建:
 * 		(1).導入相關依賴(資料庫數據、驅動、spring-jdbc模板)
 * 		(2).配置資料庫數據、建立JdbcTemplate(Spring提供的簡化資料庫操作工具)操作數據
 * 		(3).給方法上標註@Transactional表示當前是一個事務(交易)方法(單純註解@Transactional是無法開啟事務(交易))
 * 		(4).@EnableTransactionManagement 開啟基於註解的事務(交易)管理功能。
 *        		@EnableXXX -> 只要是Enable開頭都是開啟某某功能
 *      (5).配置事務管理器來控制事務(DataSourceTransactionManager[JDBC所使用]);
 *      		PlatformTransactionManager:是一個底層事務控制的介面，Hibernate、JPA、JTA、JDBC...等等都實作底層PlatformTransactionManager
 *      
 *      	總結 : 配置事務(交易)管理步驟  1.方法標註@Transactional -> 2.開啟事務管理功能@EnableTransactionManagement -> 
 *      							3.配置事務管理，將資料庫配置傳入@Bean PlatformTransactionManager transactionManager()
 *      			
 *	原理:
 *		(1).@EnableTransactionManagement 
 * 				1).利用@Import(TransactionManagementConfigurationSelector.class)給容器導入元件
 * 				2).Selector 判斷兩種狀況，元件是proxy還是aspectj類型。EnableTransactionManagement預設為PROXY[AdviceMode mode() default AdviceMode.PROXY;]
 * 				3).PROXY會導入兩個元件
 * 					A.[AutoProxyRegistrar]
 * 						a).給容器中註冊InfrastructureAdvisorAutoProxyCreator元件 :
 * 							利用後置處理器機制在物件創建以後，返回一個代理對象(增強器)，代理對象執行方法利用攔截器進行調用。
 * 							 
 * 					B.[ProxyTransactionManagementConfiguration]
 * 						a).給容器中註冊事務增強器
 * 							● 事務增強器要用事務註解訊息 : AnnotationTransactionAttributeSource 解析事務註解
 *							● 事務攔截器 : transactionInterceptor 保存了事務屬性訊息、事務管理器，他是一個實作MethodInterceptor(方法攔截器)，
 *										在目標方法執行的時候[執行攔截器]、[事務攔截器]。
 *							●[事務攔截器]步驟:
 *								1.先獲取事務相關屬性
 *								2.在獲取PlatformTransactionManager，如果事先沒有添加指定任何TransactionManager
 *								       最終會從容器中按照類型取得一個PlatformTransactionManager
 *								3.執行目標方法:
 *									如果異常:獲取事務管理器(TransactionManager)，利用事務管理執行rollback()。
 *									如果正常:獲取事務管理器，利用事務管理執行commit()
 *
 *
 *				 							
 * 
 */

@EnableTransactionManagement
@ComponentScan("com.connection")
@Configuration
public class Configuration_ {

	@Bean
	public DataSource dataSource() throws Exception{
		ComboPooledDataSource dataSource = new ComboPooledDataSource();
		
		dataSource.setUser("SPRING_1");
		dataSource.setPassword("zzz30163");
		dataSource.setDriverClass("oracle.jdbc.driver.OracleDriver");
		dataSource.setJdbcUrl("jdbc:oracle:thin:@localhost:1521:xe");
		
		return dataSource;
	}
	
	@Bean
	public JdbcTemplate jdbcTemplate() throws Exception {
		//Spring對Configuration類惠特殊處理，給容器中加元件的方法，多次調用都只是從容器中找元件。new JdbcTemplate(dataSource()) 內的dataSource()不會再次創新。
		JdbcTemplate jdbcTemplate = new JdbcTemplate(dataSource());
		return jdbcTemplate;
	}
	
	//註冊事務管理器在容器中
	@Bean
	public PlatformTransactionManager transactionManager() throws Exception {
		return new DataSourceTransactionManager(dataSource());
	}
}
