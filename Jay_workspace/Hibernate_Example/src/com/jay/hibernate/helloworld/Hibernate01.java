package com.jay.hibernate.helloworld;
import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;
/**
 * Configuration類別:
 * 		a.(1) 去加載hibernate.cfg.xml文件。(資料庫URL、Username、password、JDBC驅動、資料庫Dialect(方言)、連接池等等)   
 * 		  (2)加載 *.hbm.xml文件。(持久化層跟數據表的映射關係)
 * 		b.創建Configuration有兩種方式:
 * 				(1) 屬性文件(hibernate.properties)
 * 						Configuration cfg = new Configuration();
 * 				(2)XML格式(hibernate.cfg.xml)
 * 						Configuration cfg = new Configuration.configure();    configure()->若沒參數就是默認 hibernate.cfg.xml   參數可放:String、File類別。
 * SessionFactory 介面:
 * 		a.屬於線程安全 。
 * 		b.生成Session的工廠。SessionFactory非常耗資源，一般來說初始化一個就好。
 * 
 * ServiceRegistry 介面:
 * 		a.Hibernate4 新增的介面。
 * 		b.只要有關Hibernate配置或服務都必須向ServiceRegistry註冊後才生效。
 * 		c.Hibernate4.3版後改為 : ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
 * Session常用方法:
 * 		a.取得持久化層物件方法:
 * 			(1).get()
 * 			(2).load()
 * 		b.持久化物件保存、更新、刪除
 * 			(1).save()
 * 			(2).update()
 * 			(3).saveOrUpdate()
 * 			(4).delete()
 * 		c.開啟事務(交易)
 * 			(1).beginTransaction()
 * 					1.commit()->提交關聯的session實體      2.rollback()->撤銷事務(交易)操作    3.wasCommitted()->檢查事務(交易)是否提交
 * 		d.管理Session的方法:
 * 			(1).isOpen()
 * 			(2).flush()
 * 			(3).clear()
 * 			(4).evict()
 * 			(5).close()
 * */
class Hibernate01 {

	//java.lang.Exception: No tests found matching [{ExactMatcher:fDisplayName=test01], {ExactMatcher:fDisplayName=test01(com.jay.hibernate.helloworld.Hibernate01)], {LeadingIdentifierMatcher:fClassName=com.jay.hibernate.helloworld.Hibernate01,fLeadingIdentifier=test01]] from org.junit.internal.requests.ClassRequest@42110406
	//版本異常未處理


	@Test
	public void test01() {
		
		//1.創建一個 SessionFactory
		SessionFactory sessionFactory = null;
		//1-1 創建 Configuration 物件，對應Hibernate的基本配置訊息和物件相關映射訊息。默認就是:	"/hibernate.cfg.xml"
		Configuration configuration = new Configuration().configure();
		//1-2 創建一個SericeRegistry 物件 : hibernate 4.X 新增的物件。 hibernate 的任何配置和服務都需要在該隊向中註冊後才能有效。
		//sessionFactory = configuration.buildSessionFactory();  4.0之前
		
		//4.3版本不推薦使用
//		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
//																	  .buildServiceRegistry();
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration .getProperties()).build();
	    
		//1-3
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		//2.創建一個 Session對象
		Session session = sessionFactory.openSession();
		
		//3.開啟事務(交易) : 若未開啟不會報錯，到不會新增到資料庫裡面。
		Transaction transaction = session.beginTransaction();
		
		//4.執行保存操作
		News news = new News("Java","Jays",new Date(new java.util.Date().getTime()));
		session.save(news);
		
		//取得資料庫物件，可指定索引
//		News news2 = (News) session.get(News.class, 1);
//		System.out.println(news2);
		
		//5.提交事務(交易)
		transaction.commit();
		
		//6.關閉 Session
		session.close();
		
		//7.關閉 SessionFactory 物件
		sessionFactory.close();
	}
}
