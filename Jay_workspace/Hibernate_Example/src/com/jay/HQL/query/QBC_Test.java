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

	// 測試環境才會直接聲明
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		// 初始化
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		System.out.println("程式初始化調用init()");
	}

	@After
	public void destroy() {
		// 結束生命週期
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("結束生命週期前調用destroy()");
	}

	/**
	 * QBC查詢 
	 */
	@Test
	public void QBC_test() {
			
		//(1).創建一個Criteria對象
		Criteria criteria = session.createCriteria(Employee.class);
		
		//(2).添加查詢條件
		//Criterion 可以通過Restrictions的靜態方法得到
		criteria.add(Restrictions.eq("e-mail", "SKUMAR"));
		criteria.add(Restrictions.gt("salary",5000));
		
		//(3).執行查詢
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee);
	}

}
