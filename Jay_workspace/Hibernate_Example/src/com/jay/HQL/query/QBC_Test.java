package com.jay.HQL.query;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jay.HQL.entities.Department;
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
	 * QBC查詢 :
	 * 	 (1).有些時候會比HQL還要快，舉例:針對Employee這個類別我想查詢email是SKUMAR薪資是大於5000的人，會特別快。
	 *   (2).QBC增加SQL的可讀性，很多時候HQL當寫的較複雜就比較難讀原作者的邏輯。
	 *   (3).基本流程:
	 *   		(3-1)創建session -> 創建Transaction -> 創建Criteria -> [Criteria條件塞入] -> Criteria.list()查詢並回傳集合
	 *   			 [看需要是否用foreach取資料] -> commit Transaction -> 關閉session
	 *   (4).Restrictions 跟 Expression 用法一樣，Expression已經逐漸被Restrictions取代。
	 *   
	 */
	@Test
	public void QBC_test() {
			
		//(1).創建一個Criteria對象
		Criteria criteria = session.createCriteria(Employee.class);
		
		//(2).添加查詢條件
		//Criterion 可以通過Restrictions的靜態方法得到
		criteria.add(Restrictions.eq("email", "SKUMAR"));
		criteria.add(Restrictions.gt("salary",5000f));
		
		//(3).執行查詢
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee);
	}

	/**
	 * QBC查詢 (Or 跟 And 範例) :
	 */
	@Test
	public void QBC_andOr_test() {
		Criteria criteria = session.createCriteria(Employee.class);
		
		/*
		 (1).AND:使用Conjunction表示
		 (1-1).Conjunction本身就是一個Criteria物件
		 (1-2).且其中還可以添加Criterion物件
		*/
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.ilike("name", "a",MatchMode.ANYWHERE));
		Department dept = new Department();
		dept.setId(80);
		conjunction.add(Restrictions.eq("dept", dept));
		System.out.println(conjunction);
		
		/*
		 (2).Or:使用Disjunction表示
		*/
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.ge("salary", 6000f));
		disjunction.add(Restrictions.isNull("email"));
		
		criteria.add(disjunction);
		criteria.add(conjunction);
		
		criteria.list();
		
	}
	
	/**
	 * QBC查詢 (statistics統計查詢範例) max()最大值方法
	 */
	@Test
	public void statistics_test() {
		Criteria criteria = session.createCriteria(Employee.class);
		
		//統計查詢:使用Projection來表示:可以由Projections的靜態方法辦到。
		criteria.setProjection(Projections.max("salary"));
		
		System.out.println(criteria.uniqueResult());
	}
	
	/**
	 * QBC查詢 (orderBy範例 分頁範例)
	 */
	@Test
	public void orderBy_test() {
		Criteria criteria = session.createCriteria(Employee.class);
		
		//(1).添加排序
		criteria.addOrder(Order.asc("salary"));
		criteria.addOrder(Order.desc("email"));
		
		//(2).添加翻頁方法
		int pageSize = 5;
		int pageNo =3;
		criteria.setFirstResult((pageNo -1) * pageSize) //setFirstResult()設置起始索引
				.setMaxResults(pageSize) //setMaxResults()每頁最大顯示數方法
				.list();
	}
	
	/**
	 * 本機操作SQL 範例 (新增)
	 */
	@Test
	public void nativeSQL_test() {
		String sql = "INSERT INTO DEPARTMENTS_HQL VALUES(?,?)";
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(0, 280)
			 .setString(1, "ATGUIGU")
			 .executeUpdate();
	}
	
	/**
	 * HQL 範例 (刪除本地新增的280)
	 */
	@Test
	public void deleteHQL_test() {
		String hql = "DELETE FROM Department d WHERE d.id = :id";
		
		session.createQuery(hql).setInteger("id", 28)
								.executeUpdate();
	}
}
