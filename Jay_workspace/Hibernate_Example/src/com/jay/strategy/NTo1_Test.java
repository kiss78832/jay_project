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
	 * ����Order.xml����<many-to-one>���]�m
	 * (Customer:<set name="orders" table="ORDERS" inverse="true" lazy="true" batch-size="2" fetch="subselect">)
	 * (Order:<many-to-one name="customer" class="Customer" column="CUSTOMER_ID" lazy="false" fetch="join">)
	 * 
	 * 		(1).lazy���Ȭ�proxy�Mfalse���O�N��������ݩʱĥΩ����˯�(proxy)�M�ߧY�˯�(false)
	 * 		(2).fetch���Ȭ�join�A��ܨϥ�"�������~�s��"�覡��l��"�h��"���p��"1��"�������ݩ�
	 * 		(2-1).����lazy�ݩʡC
	 * 		(3).batch-size,���ݩʻݭn�]�m�b"1��"���ݪ�<class>������ :  <class name="Customer" table="CUSTOMER_lazy" lazy="true" batch-size="5">
	 * 		(3-1).�@��:�@����l��"1��"���o�@�q�N�z��H���ӼơC
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
