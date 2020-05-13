package com.jay.HQL.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
	
	private HibernateUtils() {}
	
	/**
	 * 單例模式:
		getInstance這個方法在單例模式用的甚多，為了避免對內存造成浪費，直到需要實例化該類的時候才將其實例化，所以用getInstance來獲取該對象，
		至於其他時候，也就是為了簡便而已，為了不讓程序在實例化對象的時候，不用每次都用new關鍵字，索性提供一個instance方法，不必一執行這個類就
		初始化，這樣做到不浪費系統資源！單例模式 可以防止 數據的沖突，節省內存空間
	*/
	private static HibernateUtils instance = new HibernateUtils(); 
	
	public static HibernateUtils getInstance() {
		return instance;
	}
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}
	
	public Session getSession() {
		/*
		 * getCurrentSession與openSession的區別:
		 * 		(1).getCurrentSession創建的session會和綁定到當前線程,而openSession不會。
		 * 		(2).getCurrentSession創建的線程會在"事務回滾"或"事物提交(commit)"后"自動關閉",而openSession必須手動關閉
		 * 		(3).getCurrentSession()->使用當前的session ; openSession()->重新建立一個新的session
		 * 		(4).使用getCurrentSession()時 要在hibernate.cfg.xml文件中進行如下設置:
		 * 				(A).本地事務（jdbc事務）: <property name="hibernate.current_session_context_class">thread</property>
		 * 				(B).全局事務（jta事務）: <property name="hibernate.current_session_context_class">jta</property>
		 */
		return getSessionFactory().getCurrentSession();
	}
}
