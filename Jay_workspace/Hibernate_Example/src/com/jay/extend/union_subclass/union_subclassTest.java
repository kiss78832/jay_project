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
	 * �˦���
	 */
	@Test
	public void union_subclass() {

	}

	/**
	 * 	�O�s(���J): 
	 * 		(1).Person���(ID�BNAME�BAGE)�AStudent���(ID�BNAME�BAGE�BSCHOOL)�A������Person��椤�����ЫئbStudent��椤�C
	 * 		(2).
	 */
	@Test
	public void union_subclass_Save() {

		Person person = new Person();
		person.setAge(11);
		person.setName("AA");

		session.save(person);

		Student student = new Student();
		// �]�m�����OPerson�ܼ�
		student.setAge(22);
		student.setName("BB");
		// �]�mStudent�ܼ�
		student.setSchool("hibernate");
//
		session.save(student);
	}

	/**
	 * 	�d��:
	 * 		(1).�d�ߤ�������:�������l����ƶ��`�@�_�A�A���d�ߡA�ʯ�t�@�ǡC
	 * 		(2).���l������:�u�ݭn�d�ߤ@�i���(Student)�C
	 */
	@Test
	public void union_subclass_Query() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Person> student = session.createQuery("FROM Student").list();
		System.out.println(student.size());
	}

	/**
	 * update() ��s
	 */
	@Test
	public void union_subclass_update() {
		String hql = "UPDATE Person p SET p.age = 30";
		session.createQuery(hql).executeUpdate();
	}
}
