package com.jay.strategy;


import java.util.List;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class NTo1_Test {
	
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
	 * 測試Order.xml中的<many-to-one>的設置
	 * (Customer:<set name="orders" table="ORDERS" inverse="true" lazy="true" batch-size="2" fetch="subselect">)
	 * (Order:<many-to-one name="customer" class="Customer" column="CUSTOMER_ID" lazy="false" fetch="join">)
	 * 
	 * 		(1).lazy取值為proxy和false分別代表對應的屬性採用延遲檢索(proxy)和立即檢索(false)
	 * 		(2).fetch取值為join，表示使用"迫切佐外連接"方式初始化"多方"關聯的"1方"的那端屬性
	 * 		(2-1).忽略lazy屬性。
	 * 		(3).batch-size,該屬性需要設置在"1方"那端的<class>元素中 :  <class name="Customer" table="CUSTOMER_lazy" lazy="true" batch-size="5">
	 * 		(3-1).作用:一次初始化"1方"的這一段代理對象的個數。
	 */
	@Test
	public void NTo1__test() {
//		Order order = (Order)session.get(Order.class, 1);
//		System.out.println(order.getCustomer().getCustomerName());
		
		List<Order> orders = session.createQuery("FROM Order o").list();
		for(Order order : orders) {
			if(order.getCustomer() != null) {
				System.out.println(order.getCustomer().getCustomerName());
			}
		}
	}
	

}
