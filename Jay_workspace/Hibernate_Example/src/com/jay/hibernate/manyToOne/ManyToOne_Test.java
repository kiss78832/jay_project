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
 * 	��V�h��@:�b�h��@ �B�@��h �����O��V���p�A�]�N�O�䤤�@�����p��t�@��A�ӥt�@�褣���D�ۤv�Q���p�C 
 * 
 * 	��V VS ���V
 * 		�u�ݭn�q�@������t�@�誺�ƾڮɴN�ϥγ�V���p�A���賣�ݭn������ƾڮɴN�ϥ����V���Y�C
 * 		
 */

public class ManyToOne_Test {
	
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
	 * �s�W
	 */
	@Test
	public void manyToOneTest_Save() {
		Customer customer = new Customer();
		customer.setCustomerName("AA");
		
		Order order1 = new Order();
		order1.setOrderName("Order-1");
		
		Order order2 = new Order();
		order2.setOrderName("Order-2");
		
		//�]�m���p���Y
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		/*
		 * (�į���n)
		 * ����save()�ާ@�A�����JCustomer�A�A���JOrder,�T��insert
		 *���J����:�����J"1"������A�A���J"�h"������A�u��insert�y�y�C (���˶���)
		 */
		session.save(customer);
		session.save(order1);
		session.save(order2);
		
		/*
		 * (�įण�n)
		 * ����save()�ާ@�A�����JOrder�A�A���JCustomer,�T��insert�B���update
		 * �y�{:���s�Worder�ⵧ��ơA��customerId���������ŭȡA����customer�إ߫�Aorder�|�۰�update�C
		 */
		//session.save(order1);
		//session.save(order2);
		//session.save(customer);
	}
	
	
	/**
	 * �d��
	 */
	@Test
	public void manyToOneTest_Get() {
		//(1).�u�d��"�h"�����A�q�{���p�U�A�u�d��"�h"�����A�ӨS�����s��Customer
		Order order = (Order)session.get(Order.class,41);
		System.out.println("�L�X���ު�order_name���: "+order.getOrderName());
		System.out.println("�L�Xorder���O��customer�ݩʡA�O�N�z��H�٬O�w�g�s�isession�w�İ�: "+order.getCustomer().getClass().getName());
		
					//�i�H�Ѧ�Hibernate02 [get() VS load()] LazyInitializationException���`
					//session.close();
		
		//(2).�A�ݭn�Ψ����p(Customer���)�ɡA�~�|�e�X������SQL(�d�ߪ�SQL)�C
		Customer customer = order.getCustomer();
		System.out.println(customer.getCustomerName());
		
		//(3).�d��Customer���ɡA��"�h"��"�@"�ɡA�Y����session�w�g�Q�����A�h�q�{���p�U�|�X�{LazyInitializationException���`�C

		//(4).���Order��H�ɡA�q�{���p�U�A���pCustomer��H�O�@�ӥN�z��H�A�]���N�z��H�����H�K���A�èS���w�s�Asession�̭��C
	
	}
	
	/**
	 *	�ק�
	 */
	@Test
	public void manyToOneTest_Update() {
		Order order = (Order) session.get(Order.class, 41);
		//�ק�Order��� order_id = 41�A�ҹ�����customer_id���W��
		order.getCustomer().setCustomerName("AAA");
	}
	
	/**
	 *	�R��
	 */
	@Test
	public void manyToOneTest_Delete() {
		//�b���]�w���p���Y���p�U�A�B "1" �o�ݪ���H�� "�h"����H�A�ޥΡA���ઽ���R�� "1"�o�ݪ���H�C(����ӻ��h��@�A"�@"����R���A�����s�P��"�@"�����p�����R���C)
		Customer customer = (Customer) session.get(Customer.class, 40);
		session.delete(customer);
	}
	
	
	
}
