package com.jay.HQL.query;

import org.hibernate.Criteria;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jay.HQL.entities.Employee;

public class QBC_Test {

	// �������Ҥ~�|�����n��
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		// ��l��
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		System.out.println("�{����l�ƽե�init()");
	}

	@After
	public void destroy() {
		// �����ͩR�g��
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("�����ͩR�g���e�ե�destroy()");
	}

	/**
	 * QBC�d�� 
	 */
	@Test
	public void QBC_test() {
			
		//(1).�Ыؤ@��Criteria��H
		Criteria criteria = session.createCriteria(Employee.class);
		
		//(2).�K�[�d�߱���
		//Criterion �i�H�q�LRestrictions���R�A��k�o��
		criteria.add(Restrictions.eq("e-mail", "SKUMAR"));
		criteria.add(Restrictions.gt("salary",5000));
		
		//(3).����d��
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee);
	}

}
