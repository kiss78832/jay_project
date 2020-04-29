package com.jay.hibernate.manyToMany;


import java.util.Set;

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
 * �@��@���p���Y(A->B ; B->A):
 * 	(1).�~����i�H�s����@��(manger or department)�A�b�ݭn�s�񪺤@�ݡA���[<many-to-one>�����W�[unique="true"
 * 	(2).�t�@�ݻݭn�ϥ�<one-to-one>�����A�Ӥ����ϥ�property-ref�ݩʡC
 */
public class ManyToMayTest {
	
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
 * 	�˦���
 */	
	@Test
	public void manyToMany() {
		
	}
	
	/**
	 * 	save()�O�s
	 */	
		@Test
		public void manyToMany_Save() {
			Category category01 = new Category();
			category01.setName("C-AA");
			
			Category category02 = new Category();
			category02.setName("C-BB");
			
			Item item01 = new Item();
			item01.setName("I-AA");
			
			Item item02 = new Item();
			item02.setName("I-BB");
			
			//�]�m���p���Y
			category01.getItems().add(item01);
			category01.getItems().add(item02);
			
			category02.getItems().add(item01);
			category02.getItems().add(item02);
			
			//����O�s
			session.save(category01);
			session.save(category02);
			session.save(item01);
			session.save(item02);
		}
		
		/**
		 * 	get()�d��
		 */	
			@Test
			public void manyToMany_Get() {
				Category category = (Category)session.get(Category.class, 1);
				System.out.println(category.getName());
				
				//�ݭn�s���������
				Set<Item> items = category.getItems();
				System.out.println(items.size());
			}
	
}
