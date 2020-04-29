package com.jay.extend.subclass;

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

/**
	subclass:映射繼承關係
		(1).缺點
			  a.使用了辨別者列(<discriminator>)
			  b.子類獨有的字串不能添加非null約束
			  c.若繼承層次交深，則資料庫表格的字串也會較多
 */
public class SubclassTest {

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
	public void manyToMany() {

	}

	/**
	 * 	保存(插入): (1).對於子類對象只需要把紀錄插入到一張資料庫表格中 (2).辨別者列有Hibernate自動維護
	 * 
	 */
	@Test
	public void manyToMany_Save() {

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

		session.save(student);
	}

	/**
	 * 	查詢:
	 * 		(1).查詢父類紀錄，只需要查詢一張資料庫表格
	 * 		(2).對於子類紀錄，也只需查詢一張資料庫表格
	 */
	@Test
	public void manyToMany_Query() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Person> student = session.createQuery("FROM Student").list();
		System.out.println(student.size());
	}

}
