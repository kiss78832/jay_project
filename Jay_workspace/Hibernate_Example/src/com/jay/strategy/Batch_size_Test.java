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
	public void Batch_size_test() {
		
	}
	
	/**
	 * ����<set>�]batch-size()���ĪG:�]�w�@����l��<set>���X���ƶq�A���Ĵ��SELECT�y�y��ءC
	 */
	@Test
	public void Batch_size() {
		List<Customer> customers = session.createQuery("FROM Customer").list();
		
		System.out.println(customers.size());
		//��C��customer���X�ӡA�h�d�߸�customer_id�bOrders��榳�X����ơC
		for(Customer customer : customers) {
			if(customer.getOrders() != null) {
				System.out.println(customer.getOrders().size());
			}
		}
		
	}
	
	/**
	(���]batch-size[�|�C���v�@�h�d��])								(�]batch-size="5"[�妸�B�z�A�|�@���d�ߤ��ӡA�Y�������Ӥ]�i�H])
		Hibernate: 												Hibernate:
		    select													select
		        customer0_.CUSTOMER_ID as CUSTOMER1_2_,					customer0_.CUSTOMER_ID as CUSTOMER1_2_,
		        customer0_.CUSTOMER_NAME as CUSTOMER2_2_ 				customer0_.CUSTOMER_NAME as CUSTOMER2_2_ 
		    from												from
		        CUSTOMER_lazy customer0_							CUSTOMER_lazy customer0_
		4				 										4	
							��												 		  ��
			  	[�o�Ocustomer��檺��Ƥ�Ƭ� "4"]							[�o�Ocustomer��檺��Ƥ�Ƭ� "4"]		
		
		(���]batch-size��SQL =>				  				(�]batch-size="5"��SQL =>
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
