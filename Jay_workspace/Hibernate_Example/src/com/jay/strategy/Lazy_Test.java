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
	
	//�������Ҥ~�|�����n��
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//��l��
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		
		System.out.println("�{����l�ƽե�init()");
	}
	
	@After
	public void destroy() {
		//�����ͩR�g��
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("�����ͩR�g���e�ե�destroy()");
	}
	
	/**
	 * �˥�
	 */
	@Test
	public void lazy_test() {
		
	}
	
	/**
	 * ����<class>����lazy�t���C
	 */
	@Test
	public void classLevelStrategy_test() {
		Customer customer = (Customer) session.load(Customer.class, 1);
		System.out.println(customer.getClass());
		
		System.out.println(customer.getCustomerId());
		System.out.println(customer.getCustomerName());
	}
	
	/**
	 * ����<set>����lazy�t��
	 * 		(1).1-n �� n-n �����X�ݩ��q�{�ϥ��i�[���˯������C
	 * 		(2).�i�H�q�L�ק�<set>��lazy�ݩʨӭק��q�{���˯������C��ĳ�q�{��true�A�ä���ĳ�]�m��false�C
	 * 				���]�m��"true"�ɡA�z�L�N�z���o��ơA�����Ȥ覡�n��load()�~�|�_�@�ΡC(���˨ϥ�)
	 * 		(3).�]�mlazy="false" �A �b��l��customer�N�P�ɧ�order����l�ơC(�ҥH�|����select customer�Border)
	 * 				���]�m"false"�A�|����select customer�Border������l�ơC
	 * 		(4).lazy�٥i�H�]�m"extra"�A�W�[�������˯��C(�Ө��ȷ|�ɥi�઺���𶰦X��l�ƪ��ɾ�!)
	 * 				���]�m"extra"�A�HCustomer���Ҥl�A�������@��"order"��List�A�ӳ]�m"extra"�|�קKorder�ण��l�ƴN���n��l�ơC
	 * 		(4-1).System.out.println(customer.getOrders().size())�ɡA��lazy="true"��lazy="extra"���t���C[size()�Bcontains()�BisEmpty()��kHibernate�����|��l��orders���X]
						(��l��Hibernate��SQL [true])                              (����l�ƪ�SQL[extra])
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
										
			(5).Hibernate.initialize(customer.getOrders()); �]��z�L�o�ؤ覡�����N�Ӫ����l�ơC									        
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
		//�u���ե�Customer�ѼƤ~�|�]�XHibernate SQL�A�÷|�bcustomer.SQL�|�bgetCustomerName()�e����C
		System.out.println("��load()�|�O�i�[��: �u"+customer.getClass()+"�v");
		System.out.println(customer.getCustomerName());
		
		session.close();
		System.out.println(customer.getCustomerName());
		
	}

	
}
