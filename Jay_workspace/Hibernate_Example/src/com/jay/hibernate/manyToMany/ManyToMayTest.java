package com.jay.hibernate.manyToMany;


import java.util.Set;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * 一對一關聯關係(A->B ; B->A):
 * 	(1).外來鍵可以存任何一邊(manger or department)，在需要存放的一端，應加<many-to-one>元素增加unique="true"
 * 	(2).另一端需要使用<one-to-one>元素，該元素使用property-ref屬性。
 */
public class ManyToMayTest {
	
	//測試環境才會直接聲明
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//初始化
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		
		System.out.println("程式初始化調用init()");
	}
	
	@After
	public void destroy() {
		//結束生命週期
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("結束生命週期前調用destroy()");
	}
	
/**
 * 	樣式表
 */	
	@Test
	public void manyToMany() {
		
	}
	
	/**
	 * 	save()保存
	 */	
		@Test
		public void manyToMany_Save() {
			Category category01 = new Category();
			category01.setName("C-AA");
			
			Category category02 = new Category();
			category02.setName("C-BB");
			
			Item item01 = new Item();
			item01.setName("I-AA");
			
			Item item02 = new Item();
			item02.setName("I-BB");
			
			//設置關聯關係
			category01.getItems().add(item01);
			category01.getItems().add(item02);
			
			category02.getItems().add(item01);
			category02.getItems().add(item02);
			
			//執行保存
			session.save(category01);
			session.save(category02);
			session.save(item01);
			session.save(item02);
		}
		
		/**
		 * 	get()查詢
		 */	
			@Test
			public void manyToMany_Get() {
				Category category = (Category)session.get(Category.class, 1);
				System.out.println(category.getName());
				
				//需要連接中間表格
				Set<Item> items = category.getItems();
				System.out.println(items.size());
			}
	
}
