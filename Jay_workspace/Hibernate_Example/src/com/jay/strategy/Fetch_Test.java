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

public class Fetch_Test {
	
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
	public void Fetch_test() {
		
	}
	
	/**
	 * 測試<set>設fetch的效果
	 * 		(1).默認值:[select]，通過正常的方式來初始化set元素。
	 * 		(2).若取值為[subselect]子查詢方式，通過此方式來初始化所有的set集合。
	 * 				子查詢作為where子句的in的條件出現，子查詢查詢所有1的那端的ID，[此時lazy有效，batch-size無效]。
	 * 		(3).若取值為[join]，則在加載1方那端的對象時，使用"迫切佐外連接"(使用left outer join查詢，並把集合"初始化")的方式檢索"多方"那端的集合屬性。
	 *		(3-1).會忽略lazy屬性
	 * 		(3-2).HQL查詢忽略fetch="join"的取值
	 * 		(4).fetch功能簡單說就是，確定初始化orders集合方式
	 */
	@Test
	public void Fetch() {
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size());
		//把每個customer取出來，去查詢該customer_id在Orders表格有幾筆資料。
		for(Customer customer : customers) {
			if(customer.getOrders() != null) {
				System.out.println(customer.getOrders().size());
			}
		}
	}
	
	/*
	 * (設置fetch="subselect")會把1那端的資料都查出來，所以子查詢會下[select CUSTOMER_ID from customer_lazy]查出customer所有ID
	 	Hibernate: 
		    select
		        customer0_.CUSTOMER_ID as CUSTOMER1_2_,
		        customer0_.CUSTOMER_NAME as CUSTOMER2_2_ 
		    from
		        CUSTOMER_lazy customer0_
		4
		Hibernate: 
		    select
		        orders0_.CUSTOMER_ID as CUSTOMER3_2_1_,
		        orders0_.ORDER_ID as ORDER_ID1_9_1_,
		        orders0_.ORDER_ID as ORDER_ID1_9_0_,
		        orders0_.ORDER_NAME as ORDER_NA2_9_0_,
		        orders0_.CUSTOMER_ID as CUSTOMER3_9_0_ 
		    from
		        ORDERS_lazy orders0_ 
		    where
		        orders0_.CUSTOMER_ID in (
		            select
		                customer0_.CUSTOMER_ID 
		            from
		                CUSTOMER_lazy customer0_
		        )
		3
		3
		3
		0
	 */
	
	/**
	 * 測試fetch="join"
	 * 		(1).解釋忽略lazy屬性，測試條件lazy="extra"，fetch="join"跟取消fetch設置。
	 * 		(1-1).
	 */
	@Test
	public void Fetch02() {
		Customer customer = (Customer) session.get(Customer.class, 1);
		System.out.println(customer.getOrders().size());
	}
	
	/*
	 * (未設置fetch，lazy="extra")										(設置fetch="join"，lazy="extra")
	 * 		(1).可以看出來使用size()只會初始化customer 								(1).這邊直接忽略lazy，把customer跟order直接初始化，並用join方式查詢
		Hibernate:														 Hibernate: 
			select											   				 select
				customer0_.CUSTOMER_ID as CUSTOMER1_2_0_,						customer0_.CUSTOMER_ID as CUSTOMER1_2_0_,
				customer0_.CUSTOMER_NAME as CUSTOMER2_2_0_ 						customer0_.CUSTOMER_NAME as CUSTOMER2_2_0_,
			from													      		orders1_.CUSTOMER_ID as CUSTOMER3_2_1_,
				CUSTOMER_lazy customer0_										orders1_.ORDER_ID as ORDER_ID1_9_1_,
			where													       		orders1_.ORDER_ID as ORDER_ID1_9_2_,
				customer0_.CUSTOMER_ID=?										orders1_.ORDER_NAME as ORDER_NA2_9_2_,
																        		orders1_.CUSTOMER_ID as CUSTOMER3_9_2_ 
		Hibernate:													   		 from
			select												        		CUSTOMER_lazy customer0_ 
				count(ORDER_ID) 											    left outer join
			from												        		ORDERS_lazy orders1_ 
				ORDERS_lazy											            on customer0_.CUSTOMER_ID=orders1_.CUSTOMER_ID 
			where												    		 where
				CUSTOMER_ID =?											        customer0_.CUSTOMER_ID=?
		3													 			 3 
	 */
}
