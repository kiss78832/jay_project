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
		 *	測試是否拿到同一條session，利用session.hashCode()，用session.isOpen()判斷。
		 *	如果你的session是透過getCurrentSession()取得，當一個執行緒結束就會自動關閉session
		 */
		@Test
		public void Dao_test() {
			
			//獲取Session
			//開啟事務
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
		 *	處理批次資料有四種方式: 
		 *		(1).通過Session(當有多筆資料，就會去一直執行save()、update()，這樣一級緩存會爆掉，唯一能處理辦法就是定量清理，但速度還是不快) 
		 *		(2).通過HQL (只有支持 :INSERT INTO (SELECT)子查詢的方式，不支持:INSERT INTO ...VALUES方式插入，所以hql不能進行批量插入)
		 *		(3).通過StateiessSession (StateiessSession跟session類似，差別在StateiessSession沒有緩存，但最後還是翻成JDBC，所以直接用JDBC最好)
		 *		(4).通過JDBC API(批次推薦使用，速度較快)
		 */
		@Test
		public void batch_test() {
			session.doWork(new Work() {

				@Override
				public void execute(Connection arg0) throws SQLException {
					//通過JDBC 原生的API進行操作，效率最高，速度最快
				}
			});
		}
}
