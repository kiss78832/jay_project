package com.jay.HQL.query;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jay.HQL.entities.Department;
import com.jay.HQL.entities.Employee;

public class HQL_Test {
	
	//測試環境才會直接聲明
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//初始化
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		
		System.out.println("程式初始化調用init()");
	}
	
	@After
	public void destroy() {
		//結束生命週期
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("結束生命週期前調用destroy()");
	}
	
	/**
	 * HQL查詢 (基於索引的參數)
	 */
	@Test
	public void HQL_test() {
		
		//(1).創建Query對象 (注意FROM XXX，格式比需第一個大寫，且不是輸入資料庫表格名稱，而是實體的物件Employee.java)
		String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? ORDER BY e.salary";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		
		//(2).綁定參數
		query.setFloat(0, 6000)
			 .setString(1,"%S%")
			 .setEntity(2, dept); //可直接塞一個實體物件
		
		//(3).執行查詢 list()回傳一個集合
		List<Employee> emps = query.list();
		System.out.println(emps.size());
	}
	
	/**
	 * HQL查詢(使用名稱當參數值)
	 */
	@Test
	public void HQLNameparameter_test() {
		//(1).創建Query對象 (注意FROM XXX，格式比需第一個大寫，且不是輸入資料庫表格名稱，而是實體的物件Employee.java)
		String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
		Query query = session.createQuery(hql);
		
		//(2).綁定參數()
		query.setFloat("sal", 6000)
			 .setString("email","%%");
		
		//(3).執行查詢
		List<Employee> emps = query.list();
		System.out.println("共多少資料: "+emps.size()+" 筆");
	}
	
	/**
	 * HQL查詢(使用名稱當參數值)
	 */
	@Test
	public void pageQuery_test() {
		//(1).創建Query對象 (注意FROM XXX，格式比需第一個大寫，且不是輸入資料庫表格名稱，而是實體的物件Employee.java)
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		
		//(2).配置分頁
		int pageNo = 3; //分成的頁數
		int pageSize = 5; //每頁幾筆
		
		//(3).執行查詢
		//		a.setFirstResult()：設置從哪個地方開始查詢，默認是0。
		//      b.setMaxResults()：設置一次最多查詢的數目。

		List<Employee> emps = query.setFirstResult((pageNo - 1) * pageSize) //(3-1) * 5 = 10，從第10筆的下一個開始也就是第11個。
	  							   .setMaxResults(pageSize)
								   .list();
		
		System.out.println(emps);
	}
	
	/**
	 * XML <Query>查詢(使用名稱當參數值) :請使用getNamedQuery("XXX")
	 */
	@Test
	public void xmlQuery_test() {
		Query query = session.getNamedQuery("salaryEmps");
		
		List<Employee> emps = query.setFloat("minSal", 5000)
								   .setFloat("maxSal", 10000)
								   .list(); //list()回傳一個集合
		System.out.println(emps.size());
	}
	
	/**
	 * 一、(一般SELECT語法方法)
	 * 		hql 有加上SELECT :假設為"SELECT A欄,B欄 FROM C類別"，返回的是一個Object陣列
	 *     		沒加上SELECT :單純只是"FROM XXX"，返回的是一個Object，可以自由轉換任何類別。
	 */
	@Test
	public void fieldQuery_test() {
		String hql = "SELECT e.email, e.salary ,e.dept FROM Employee e WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		List<Object[]>result = query.setEntity("dept",dept)
									.list();
		
		//因為是陣列所以要解析兩次，所以用Arrays.asList()
		for(Object[] objs : result) {
			System.out.println(Arrays.asList(objs));
		}
	}
		
		/**
		 * 二、(直接再SELECT語法創建物件，可以減少解析)
		 * 		"SELECT new Employee( e.email, e.salary ,e.dept )" 這樣返回就是一個Employee物件
		 * 
		 * 注意:如果要用此方式，記得要該類別要有相對應的建構子，new Employee(A,B,C)。無參數的也不要忘記。
		 */
		@Test
		public void fieldQuery02_test() {
			String hql = "SELECT new Employee( e.email, e.salary ,e.dept )"
					+ "FROM Employee e "
					+ "WHERE e.dept = :dept";
			
			Query query = session.createQuery(hql);
			
			Department dept = new Department();
			dept.setId(80);
			List<Employee>result = query.setEntity("dept",dept)
										.list();
			
			for(Employee emp : result) {
				System.out.println(emp.getId()+","+emp.getEmail()+","+emp.getsalary()+","+emp.getDept());
			}
		
	}
	
		/**
		 * groupBy()範例
		 * 		題目:(1)找出最大薪資跟最小薪資多少? (2)我想知道每個部門最低薪資 > 8000 的有哪幾個部門?
		 * 		70、90、110 有達標
		 */
		@Test
		public void groupBy_test() {
			String hql = "SELECT min(e.salary),max(e.salary)"
					+ "FROM Employee e GROUP BY e.dept HAVING min(salary) > :minSal";
			
			Query query = session.createQuery(hql)
								 .setFloat("minSal", 8000);
			
			List<Object []>result = query.list();
			for(Object[] objs : result) {
				System.out.println(Arrays.asList(objs));
			}
		}
		
		/**
		 * Left Join Fetch 迫切連接範例(迫切左外連線: 特點是：如果左表有不滿足條件的，也返回左表不滿足條件)下面的例子是 從 1 對 多
		 * 		(1).list()返回來的集合中存放實體物件的引用，每個Department物件跟關聯的Eployee集合都被初始化，存放所有關聯的Employee的實體物件。(返回FROM後面那個類別)
		 * 		(2).★Left JOIN FETCH -> 直接初始化Employee，把Employee的資料一次倒進Department的emps集合中，所以只要返回Department物件就好，所以才不需是陣列的原因。
		 * 		(3).distinct -> SQL 不重複的指令
		 * 		(4).查詢結果中會有很多重複資料，可以利用兩種方法排除 (A)在SQL改用SELECT DISTINCT方式 (B)把接回來的參數放進HashSet集合裡面。
		 * 		(5).注意:迫切連接是返回"物件"而不是"物件陣列"，內連結(沒有fetch)就是返回"物件陣列"。
		 * 		(6).因為多了fetch關鍵字，會將emp的數據抓出來，填充到Dept集合中的emps中。
		 * 		()
		 */
		@Test
		public void leftJoinFetch_test() {
//			String hql = "FROM Department d INNER JOIN FETCH d.emps";
			//方法A
			String hql = "SELECT DISTINCT d FROM Department d Left JOIN FETCH d.emps";
			Query query = session.createQuery(hql);
			
			List<Department> depts = query.list();
			//方法B
//			depts = new ArrayList<>(new LinkedHashSet(depts));
			System.out.println(depts.size());
			
			for(Department dept : depts) {
				System.out.println(dept.getName()+" - "+dept.getEmps().size()+" , "+dept.getId());
			}
		}
		
		/**
		 * Left Join 左外連接範例
		 * 		(1).list()返回的是物件陣列。(每個資料都不同，同部門一樣但員工不同。)
		 * 		(2).根據配置文件來決定Employee集合檢索策略。(不像迫切連結一樣，直接兩個都初始化)
		 * 		(3).如果希望list()返回的集合中包含Department物件，可以再HQL查詢語句中使用SELECT。
		 * 		(4).Left JOIN -> 
		 * 		(5).Outer Join 是 inclusive, 叫它做包容性，因此在 Left Outer Join 的查詢結果會包含所有 Left 資料表的資料
		 */
		@Test
		public void leftJoin_test() {
			/**
			 * 有使用SELECT DISTINCT (★[欄位只有department.ID、department.NAME]再使用distinct指令就能篩檢重複資料，但如果多一個emps的其他欄位之類又會出現重複資料。因為emps的其他欄位並不是重複的資料)
			 */
//			String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
//			Query query = session.createQuery(hql);
//			
//			List<Department> depts = query.list();
//			System.out.println(depts.size());
//			
//			for(Department dept : depts) {
//				//這邊發現Department的emps集合並沒有被初始化，所以每次計算emps的size都要執行一次SQL
//				System.out.println(dept.getName()+" , "+dept.getEmps().size());			
//			}
			
			/**
			 * 未用SELECT DISTINCT
			 */
			String hql = "FROM Department d LEFT JOIN d.emps";
			Query query = session.createQuery(hql);
			
			//集合存放的是Object，而Object中裝入的無非是Department和Employee對象。
			List<Object[]> result = query.list();
			
			//無法抵擋重複，因為Object有Department和Employee物件，所以必須單純查Department的兩個欄位就好，再利用DISTINCT語法去除重複。
			result = new ArrayList<>(new LinkedHashSet<>(result));
			System.out.println(result);
			
			for(Object[] objs : result) {
				System.out.println(Arrays.asList(objs));
			}
		}
}
