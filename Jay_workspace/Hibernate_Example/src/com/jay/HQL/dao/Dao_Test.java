package com.jay.HQL.dao;

import java.sql.Connection;
import java.sql.SQLException;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.jdbc.Work;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jay.HQL.entities.Department;

public class Dao_Test {
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
		 *	���լO�_����P�@��session�A�Q��session.hashCode()�A��session.isOpen()�P�_�C
		 *	�p�G�A��session�O�z�LgetCurrentSession()���o�A��@�Ӱ���������N�|�۰�����session
		 */
		@Test
		public void Dao_test() {
			
			//���Session
			//�}�Ҩư�
			Session session = HibernateUtils.getInstance().getSession();
			System.out.println("--->"+session.hashCode());
			Transaction transaction = session.beginTransaction();
			
			
			DepartmentDao departmentDao = new DepartmentDao();
			
			Department dept = new Department();
			dept.setName("Jay");
			
			departmentDao.save(dept);
			departmentDao.save(dept);
			departmentDao.save(dept);
			
			transaction.commit();
			System.out.println(session.isOpen());
		}
		
		/**
		 *	�B�z�妸��Ʀ��|�ؤ覡: 
		 *		(1).�q�LSession(���h����ơA�N�|�h�@������save()�Bupdate()�A�o�ˤ@�Žw�s�|�z���A�ߤ@��B�z��k�N�O�w�q�M�z�A���t���٬O����) 
		 *		(2).�q�LHQL (�u����� :INSERT INTO (SELECT)�l�d�ߪ��覡�A�����:INSERT INTO ...VALUES�覡���J�A�ҥHhql����i���q���J)
		 *		(3).�q�LStateiessSession (StateiessSession��session�����A�t�O�bStateiessSession�S���w�s�A���̫��٬O½��JDBC�A�ҥH������JDBC�̦n)
		 *		(4).�q�LJDBC API(�妸���˨ϥΡA�t�׸���)
		 */
		@Test
		public void batch_test() {
			session.doWork(new Work() {

				@Override
				public void execute(Connection arg0) throws SQLException {
					//�q�LJDBC ��ͪ�API�i��ާ@�A�Ĳv�̰��A�t�׳̧�
				}
			});
		}
}
