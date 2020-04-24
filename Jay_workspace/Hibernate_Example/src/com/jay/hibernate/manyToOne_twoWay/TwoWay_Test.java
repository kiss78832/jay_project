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
 * ���V�@��h�d��(���V�N��N�O�bOrder���O��Customer���O�A�bCustomer����Order���O�A��Order�O�h��ҥH�ζ��X�ACustomer�O"�@��"�ҥH���ΡC)
 */
public class TwoWay_Test {
	
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
	public void twoWay_Save() {
		Customer customer = new Customer();
		customer.setCustomerName("twoWay01");
		
		Order order1 = new Order();
		order1.setOrderName("Order-30");
		
		Order order2 = new Order();
		order2.setOrderName("Order-40");
		
		//�]�m���p���Y
		order1.setCustomer(customer);
		order2.setCustomer(customer);
		
		/*
		 * ���V�ާ@:����save()�ާ@�A3��INSERT 2��UPDATE
		 * �]��"�@��"���ݩM"�h��"���ݦ����ۺ��@���p���Y�A�ҥH�|�h�XUPDATE
		 */
		session.save(customer);
		session.save(order1);
		session.save(order2);
		
		customer.getOrders().add(order1);
		customer.getOrders().add(order2);

	}
	
	
	/**
	 * �d��
	 */
	@Test
	public void twoWay_Get() {
		//(1).��"�h��"���ݪ����X�ϥΩ���[��
		Customer customer = (Customer)session.get(Customer.class, 52);
		System.out.println(customer.getCustomerName());
		//(2).��^��"�h��"���ݪ����X(PersistentSet)�OHibernate���m���X�����APersistentSet�������㦳����[���M�s��N�z��H���\��C
		//    �O�o�bCustomer��orders�����X�����ݭn�`�N�A�аѦ�Customer.jara
		System.out.println("�L�Xcustomer���O�N�z��H�A�ӬOHibernate���X��H(PersistentSet): "+customer.getOrders().getClass().getName());
		
		//(3).�i��|�ߥXLazyInitializationException�A��N�z��H�@�ˡA����session��A�h�d�߳��|�X�{���`�C
		//session.close();
		//System.out.println(customer.getOrders().size());
		
		//(4).�A�ݭn�ϥζ��X���������ɭԶi���l�ơC
	}

	
	/**
	 *	�ק�
	 */
	@Test
	public void twoWay_Update() {
		Customer customer = (Customer)session.get(Customer.class, 52);
		//����orders���X�A�o���X�N�Oorders�Ҧ���ơA�]��Set�O�����Ӷ��ǡA�ҥH�ק諸���@�w�O�Ĥ@�ӡA���Iid�n��customer�ۦP�A�~�|�q�ۦP���p����ƭק�C
		customer.getOrders().iterator().next().setOrderName("Test");
	}
	
	/**
	 *	�R��
	 */
	@Test
	public void twoWay_Delete() {
		//�b���]�w���p���Y���p�U�A�B "1" �o�ݪ���H�� "�h"����H�A�ޥΡA���ઽ���R�� "1"�o�ݪ���H�C(����ӻ��h��@�A"�@"����R���A�����s�P��"�@"�����p�����R���C)
		Customer customer = (Customer) session.get(Customer.class, 40);
		session.delete(customer);
	}
	
	
	
}
