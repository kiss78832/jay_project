package com.jay.strategy;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Lazy_Test {
	
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
	public void lazy_test() {
		
	}
	
	/**
	 * 測試<class>中的lazy差異。
	 */
	@Test
	public void classLevelStrategy_test() {
		Customer customer = (Customer) session.load(Customer.class, 1);
		System.out.println(customer.getClass());
		
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerName());
	}
	
	/**
	 * 測試<set>中的lazy差異
	 * 		(1).1-n 或 n-n 的集合屬性默認使用懶加載檢索策略。
	 * 		(2).可以通過修改<set>的lazy屬性來修改默認的檢索策略。建議默認為true，並不建議設置為false。
	 * 				★設置為"true"時，透過代理取得資料，但取值方式要用load()才會起作用。(推薦使用)
	 * 		(3).設置lazy="false" ， 在初始化customer就同時把order表格初始化。(所以會產生select customer、order)
	 * 				★設置"false"，會直接select customer、order直接初始化。
	 * 		(4).lazy還可以設置"extra"，增加的延遲檢索。(該取值會盡可能的延遲集合初始化的時機!)
	 * 				★設置"extra"，以Customer為例子，內部有一個"order"的List，而設置"extra"會避免order能不初始化就不要初始化。
	 * 		(4-1).System.out.println(customer.getOrders().size())時，當lazy="true"跟lazy="extra"的差異。[size()、contains()、isEmpty()方法Hibernate都不會初始化orders集合]
						(初始化Hibernate的SQL [true])                              (未初始化的SQL[extra])
						Hibernate:				              					  Hibernate: 
							select							   			 				select
								orders0_.CUSTOMER_ID as CUSTOMER3_2_0_,						count(ORDER_ID) 
								orders0_.ORDER_ID as ORDER_ID1_9_0_,					from
								orders0_.ORDER_ID as ORDER_ID1_9_1_,						ORDERS_lazy 
								orders0_.ORDER_NAME as ORDER_NA2_9_1_,					where
								orders0_.CUSTOMER_ID as CUSTOMER3_9_1_						CUSTOMER_ID =?
							from
								ORDERS_lazy orders0_												        
							where		
								orders0_.CUSTOMER_ID=?
										
			(5).Hibernate.initialize(customer.getOrders()); 也能透過這種方式直接將該物件初始化。									        
	 */
	@Test
	public void lazy_1toMany() {
		Customer customer = (Customer) session.get(Customer.class, 1);
		
		System.out.println(customer.getCustomerName());
		
		System.out.println(customer.getOrders().size());
		
		Order order = new Order();
		order.setOrderId(1);
		System.out.println(customer.getOrders().contains(order));
	}
	@Test
	public void lazyIsTrue() {
		Customer customer = (Customer) session.load(Customer.class, 1);
		//真正調用Customer參數才會跑出Hibernate SQL，並會在customer.SQL會在getCustomerName()前執行。
		System.out.println("用load()會是懶加載: 「"+customer.getClass()+"」");
		System.out.println(customer.getCustomerName());
		
		session.close();
		System.out.println(customer.getCustomerName());
		
	}

	
}
