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
	subclass:�M�g�~�����Y
		(1).���I
			  a.�ϥΤF��O�̦C(<discriminator>)
			  b.�l���W�����r�ꤣ��K�[�Dnull����
			  c.�Y�~�Ӽh����`�A�h��Ʈw��檺�r��]�|���h
 */
public class SubclassTest {

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
	public void manyToMany() {

	}

	/**
	 * 	�O�s(���J): (1).���l����H�u�ݭn��������J��@�i��Ʈw��椤 (2).��O�̦C��Hibernate�۰ʺ��@
	 * 
	 */
	@Test
	public void manyToMany_Save() {

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

		session.save(student);
	}

	/**
	 * 	�d��:
	 * 		(1).�d�ߤ��������A�u�ݭn�d�ߤ@�i��Ʈw���
	 * 		(2).���l�������A�]�u�ݬd�ߤ@�i��Ʈw���
	 */
	@Test
	public void manyToMany_Query() {
		List<Person> persons = session.createQuery("FROM Person").list();
		System.out.println(persons.size());
		
		List<Person> student = session.createQuery("FROM Student").list();
		System.out.println(student.size());
	}

}
