package com.jay.hibernate.manyToOne;


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
 * 	單向多對一:在多對一 、一對多 中都是單向關聯，也就是其中一方關聯到另一方，而另一方不知道自己被關聯。 
 * 
 * 	單向 VS 雙向
 * 		只需要從一方獲取另一方的數據時就使用單向關聯，雙方都需要獲取對方數據時就使用雙向關係。
 * 		
 */

public class ManyToOne_Test {
	
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
	public void manyToOneTest_Save() {
		Customer customer = new Customer();
		customer.setCustomerName("AA");
		
		Order order1 = new Order();
		order1.setOrderName("Order-1");
		
		Order order2 = new Order();
		order2.setOrderName("Order-2");
		
		//設置關聯關係
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		/*
		 * (效能較好)
		 * 執行save()操作，先插入Customer，再插入Order,三條insert
		 *插入順序:先插入"1"的那方，再插入"多"的那方，只限insert語句。 (推薦順序)
		 */
		session.save(customer);
		session.save(order1);
		session.save(order2);
		
		/*
		 * (效能不好)
		 * 執行save()操作，先插入Order，再插入Customer,三條insert、兩條update
		 * 流程:先新增order兩筆資料，把customerId的欄位先為空值，等到customer建立後，order會自動update。
		 */
		//session.save(order1);
		//session.save(order2);
		//session.save(customer);
	}
	
	
	/**
	 * 查詢
	 */
	@Test
	public void manyToOneTest_Get() {
		//(1).只查詢"多"的表格，默認情況下，只查詢"多"的表格，而沒有關連到Customer
		Order order = (Order)session.get(Order.class,41);
		System.out.println("印出索引的order_name欄位: "+order.getOrderName());
		System.out.println("印出order類別的customer屬性，是代理對象還是已經存進session緩衝區: "+order.getCustomer().getClass().getName());
		
					//可以參考Hibernate02 [get() VS load()] LazyInitializationException異常
					//session.close();
		
		//(2).再需要用到關聯(Customer表格)時，才會送出對應的SQL(查詢的SQL)。
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomerName());
		
		//(3).查詢Customer表格時，由"多"到"一"時，若此時session已經被關閉，則默認情況下會出現LazyInitializationException異常。

		//(4).獲取Order對象時，默認情況下，關聯Customer對象是一個代理對象，因為代理對象不能隨便關，並沒有緩存再session裡面。
	
	}
	
	/**
	 *	修改
	 */
	@Test
	public void manyToOneTest_Update() {
		Order order = (Order) session.get(Order.class, 41);
		//修改Order表格 order_id = 41，所對應的customer_id的名稱
		order.getCustomer().setCustomerName("AAA");
	}
	
	/**
	 *	刪除
	 */
	@Test
	public void manyToOneTest_Delete() {
		//在不設定級聯關係情況下，且 "1" 這端的對象有 "多"的對象再引用，不能直接刪除 "1"這端的對象。(間單來說多對一，"一"那方刪除，必須連同跟"一"有關聯的都刪除。)
		Customer customer = (Customer) session.get(Customer.class, 40);
		session.delete(customer);
	}
	
	
	
}
