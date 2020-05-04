package com.jay.extend.joined_subclass;

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


public class Joined_subclassTest {

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
	 * 樣式表
	 */
	@Test
	public void joined_subclass() {

	}

	/**
	 * 	保存(插入): 
	 * 		(1).插入完整參數(Person.ID , Person.name , Person.age , Student.Student_id , Student.school)，分成兩張表格互相映射。
	 * 		(2).對於子類對象至少需要插入到兩張資料庫表格中。
	 * 		(3).如果只新增子類Student，Hibernate會自動新增一筆Person但參數是null值。
	 * 		(4).如果只新增父類Person，就直接新增不會影響到Student表格。
	 */
	@Test
	public void joined_subclass_Save() {

		Person person = new Person();
		person.setAge(11);
		person.setName("AA");

		session.save(person);

		Student student = new Student();
		// 設置父類別Person變數
		student.setAge(22);
		student.setName("BB");
		// 設置Student變數
		student.setSchool("hibernate");
//
		session.save(student);
	}

	/**
	 * 	查詢:
	 * 		(1).查詢父類紀錄:做一個left join查詢。
	 * 		(2).對於子類紀錄:做一個inner join接查詢。
	 */
	@Test
	public void joined_subclass_Query() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Person> student = session.createQuery("FROM Student").list();
		System.out.println(student.size());
	}
	
}
