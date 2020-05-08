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

public class Batch_size_Test {
	
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
	 * 樣本
	 */
	@Test
	public void Batch_size_test() {
		
	}
	
	/**
	 * 測試<set>設batch-size()的效果:設定一次初始化<set>集合的數量，有效減少SELECT語句樹目。
	 */
	@Test
	public void Batch_size() {
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size());
		//把每個customer取出來，去查詢該customer_id在Orders表格有幾筆資料。
		for(Customer customer : customers) {
			if(customer.getOrders() != null) {
				System.out.println(customer.getOrders().size());
			}
		}
		
	}
	
	/**
	(未設batch-size[會每筆逐一去查詢])								(設batch-size="5"[批次處理，會一次查詢五個，若不足五個也可以])
		Hibernate: 												Hibernate:
		    select													select
		        customer0_.CUSTOMER_ID as CUSTOMER1_2_,					customer0_.CUSTOMER_ID as CUSTOMER1_2_,
		        customer0_.CUSTOMER_NAME as CUSTOMER2_2_ 				customer0_.CUSTOMER_NAME as CUSTOMER2_2_ 
		    from												from
		        CUSTOMER_lazy customer0_							CUSTOMER_lazy customer0_
		4				 										4	
							↑												 		  ↑
			  	[這是customer表格的資料比數為 "4"]							[這是customer表格的資料比數為 "4"]		
		
		(未設batch-size的SQL =>				  				(設batch-size="5"的SQL =>
		select * from orders_lazy where customer_id = 1;)		select * from orders_lazy where customer_id in (1,2,3,4) )	  

		Hibernate: 												Hibernate:
		    select													select
		        orders0_.CUSTOMER_ID as CUSTOMER3_2_0_,					orders0_.CUSTOMER_ID as CUSTOMER3_2_1_
		        orders0_.ORDER_ID as ORDER_ID1_9_0_,					orders0_.ORDER_ID as ORDER_ID1_9_1_,
		        orders0_.ORDER_ID as ORDER_ID1_9_1_,					orders0_.ORDER_ID as ORDER_ID1_9_0_,
		        orders0_.ORDER_NAME as ORDER_NA2_9_1_,					orders0_.ORDER_NAME as ORDER_NA2_9_0_,
		        orders0_.CUSTOMER_ID as CUSTOMER3_9_1_ 					orders0_.CUSTOMER_ID as CUSTOMER3_9_0_
		    from												from
		        ORDERS_lazy orders0_ 									ORDERS_lazy orders0_
		    where												where
		        orders0_.CUSTOMER_ID=?								orders0_.CUSTOMER_ID in (
		3																?, ?, ?, ?
		Hibernate: 													)
		    select												3
		        orders0_.CUSTOMER_ID as CUSTOMER3_2_0_,			3
		        orders0_.ORDER_ID as ORDER_ID1_9_0_,			3
		        orders0_.ORDER_ID as ORDER_ID1_9_1_,			0
		        orders0_.ORDER_NAME as ORDER_NA2_9_1_,
		        orders0_.CUSTOMER_ID as CUSTOMER3_9_1_ 
		    from
		        ORDERS_lazy orders0_ 
		    where
		        orders0_.CUSTOMER_ID=?
		3
		Hibernate: 
		    select
		        orders0_.CUSTOMER_ID as CUSTOMER3_2_0_,
		        orders0_.ORDER_ID as ORDER_ID1_9_0_,
		        orders0_.ORDER_ID as ORDER_ID1_9_1_,
		        orders0_.ORDER_NAME as ORDER_NA2_9_1_,
		        orders0_.CUSTOMER_ID as CUSTOMER3_9_1_ 
		    from
		        ORDERS_lazy orders0_ 
		    where
		        orders0_.CUSTOMER_ID=?
		3
		Hibernate: 
		    select
		        orders0_.CUSTOMER_ID as CUSTOMER3_2_0_,
		        orders0_.ORDER_ID as ORDER_ID1_9_0_,
		        orders0_.ORDER_ID as ORDER_ID1_9_1_,
		        orders0_.ORDER_NAME as ORDER_NA2_9_1_,
		        orders0_.CUSTOMER_ID as CUSTOMER3_9_1_ 
		    from
		        ORDERS_lazy orders0_ 
		    where
		        orders0_.CUSTOMER_ID=?
		0
	*/
}
