package com.jay.extend.union_subclass;

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


public class union_subclassTest {

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
	public void union_subclass() {

	}

	/**
	 * 	保存(插入): 
	 * 		(1).Person欄位(ID、NAME、AGE)，Student欄位(ID、NAME、AGE、SCHOOL)，直接把Person表格中的欄位創建在Student表格中。
	 * 		(2).
	 */
	@Test
	public void union_subclass_Save() {

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
	 * 		(1).查詢父類紀錄:把父表格跟子表格資料匯總一起，再做查詢，性能差一些。
	 * 		(2).對於子類紀錄:只需要查詢一張表格(Student)。
	 */
	@Test
	public void union_subclass_Query() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Person> student = session.createQuery("FROM Student").list();
		System.out.println(student.size());
	}

	/**
	 * update() 更新
	 */
	@Test
	public void union_subclass_update() {
		String hql = "UPDATE Person p SET p.age = 30";
		session.createQuery(hql).executeUpdate();
	}
}
