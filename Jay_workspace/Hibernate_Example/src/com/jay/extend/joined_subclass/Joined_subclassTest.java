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
	public void joined_subclass() {

	}

	/**
	 * 	�O�s(���J): 
	 * 		(1).���J����Ѽ�(Person.ID , Person.name , Person.age , Student.Student_id , Student.school)�A������i��椬�۬M�g�C
	 * 		(2).���l����H�ܤֻݭn���J���i��Ʈw��椤�C
	 * 		(3).�p�G�u�s�W�l��Student�AHibernate�|�۰ʷs�W�@��Person���ѼƬOnull�ȡC
	 * 		(4).�p�G�u�s�W����Person�A�N�����s�W���|�v�T��Student���C
	 */
	@Test
	public void joined_subclass_Save() {

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
	 * 		(1).�d�ߤ�������:���@��left join�d�ߡC
	 * 		(2).���l������:���@��inner join���d�ߡC
	 */
	@Test
	public void joined_subclass_Query() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Person> student = session.createQuery("FROM Student").list();
		System.out.println(student.size());
	}
	
}
