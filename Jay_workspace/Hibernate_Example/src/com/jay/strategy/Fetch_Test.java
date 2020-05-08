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
	public void Fetch_test() {
		
	}
	
	/**
	 * ����<set>�]fetch���ĪG
	 * 		(1).�q�{��:[select]�A�q�L���`���覡�Ӫ�l��set�����C
	 * 		(2).�Y���Ȭ�[subselect]�l�d�ߤ覡�A�q�L���覡�Ӫ�l�ƩҦ���set���X�C
	 * 				�l�d�ߧ@��where�l�y��in������X�{�A�l�d�߬d�ߩҦ�1�����ݪ�ID�A[����lazy���ġAbatch-size�L��]�C
	 * 		(3).�Y���Ȭ�[join]�A�h�b�[��1�診�ݪ���H�ɡA�ϥ�"�������~�s��"(�ϥ�left outer join�d�ߡA�çⶰ�X"��l��")���覡�˯�"�h��"���ݪ����X�ݩʡC
	 *		(3-1).�|����lazy�ݩ�
	 * 		(3-2).HQL�d�ߩ���fetch="join"������
	 * 		(4).fetch�\��²�满�N�O�A�T�w��l��orders���X�覡
	 */
	@Test
	public void Fetch() {
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size());
		//��C��customer���X�ӡA�h�d�߸�customer_id�bOrders��榳�X����ơC
		for(Customer customer : customers) {
			if(customer.getOrders() != null) {
				System.out.println(customer.getOrders().size());
			}
		}
	}
	
	/*
	 * (�]�mfetch="subselect")�|��1���ݪ���Ƴ��d�X�ӡA�ҥH�l�d�߷|�U[select CUSTOMER_ID from customer_lazy]�d�Xcustomer�Ҧ�ID
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
	 * ����fetch="join"
	 * 		(1).��������lazy�ݩʡA���ձ���lazy="extra"�Afetch="join"�����fetch�]�m�C
	 * 		(1-1).
	 */
	@Test
	public void Fetch02() {
		Customer customer = (Customer) session.get(Customer.class, 1);
		System.out.println(customer.getOrders().size());
	}
	
	/*
	 * (���]�mfetch�Alazy="extra")										(�]�mfetch="join"�Alazy="extra")
	 * 		(1).�i�H�ݥX�Өϥ�size()�u�|��l��customer 								(1).�o�䪽������lazy�A��customer��order������l�ơA�å�join�覡�d��
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
