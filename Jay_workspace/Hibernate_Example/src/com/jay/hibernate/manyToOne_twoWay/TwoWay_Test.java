package com.jay.hibernate.manyToOne_twoWay;


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
 * 雙向一對多範例(雙向意思就是在Order類別有Customer類別，在Customer內有Order類別，而Order是多方所以用集合，Customer是"一方"所以不用。)
 */
public class TwoWay_Test {
	
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
	 * 新增
	 */
	@Test
	public void twoWay_Save() {
		Customer customer = new Customer();
		customer.setCustomerName("twoWay01");
		
		Order order1 = new Order();
		order1.setOrderName("Order-30");
		
		Order order2 = new Order();
		order2.setOrderName("Order-40");
		
		//設置關聯關係
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		/*
		 * 雙向操作:執行save()操作，3條INSERT 2條UPDATE
		 * 因為"一方"那端和"多方"那端有互相維護關聯關係，所以會多出UPDATE
		 */
		session.save(customer);
		session.save(order1);
		session.save(order2);
		
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);

	}
	
	
	/**
	 * 查詢
	 */
	@Test
	public void twoWay_Get() {
		//(1).對"多方"那端的集合使用延遲加載
		Customer customer = (Customer)session.get(Customer.class, 52);
		System.out.println(customer.getCustomerName());
		//(2).返回的"多方"那端的集合(PersistentSet)是Hibernate內置集合類型，PersistentSet該類型具有延遲加載和存放代理對象的功能。
		//    記得在Customer的orders的集合類型需要注意，請參考Customer.jara
		System.out.println("印出customer不是代理對象，而是Hibernate集合對象(PersistentSet): "+customer.getOrders().getClass().getName());
		
		//(3).可能會拋出LazyInitializationException，跟代理對象一樣，關閉session後再去查詢都會出現異常。
		//session.close();
		//System.out.println(customer.getOrders().size());
		
		//(4).再需要使用集合中元素的時候進行初始化。
	}

	
	/**
	 *	修改
	 */
	@Test
	public void twoWay_Update() {
		Customer customer = (Customer)session.get(Customer.class, 52);
		//拿取orders集合，這集合就是orders所有資料，因為Set是不按照順序，所以修改的不一定是第一個，重點id要跟customer相同，才會從相同關聯的資料修改。
		customer.getOrders().iterator().next().setOrderName("Test");
	}
	
	/**
	 *	刪除
	 */
	@Test
	public void twoWay_Delete() {
		//在不設定級聯關係情況下，且 "1" 這端的對象有 "多"的對象再引用，不能直接刪除 "1"這端的對象。(間單來說多對一，"一"那方刪除，必須連同跟"一"有關聯的都刪除。)
		Customer customer = (Customer) session.get(Customer.class, 40);
		session.delete(customer);
	}
	
	
	
}
