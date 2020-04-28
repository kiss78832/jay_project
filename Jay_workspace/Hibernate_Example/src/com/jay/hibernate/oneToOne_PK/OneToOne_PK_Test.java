package com.jay.hibernate.oneToOne_PK;


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
 * 一對一關聯關係(A->B ; B->A):
 * 	(1).外來鍵可以存任何一邊(manger or department)，在需要存放的一端，應加<many-to-one>元素增加unique="true"
 * 	(2).另一端需要使用<one-to-one>元素，該元素使用property-ref屬性。
 */
public class OneToOne_PK_Test {
	
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
	 *	新增 
	 * */
	@Test
	public void oneToOne_Save() {
		//只需要設置Name、Id資料庫產生
		Department02 department = new Department02();
		department.setDeptName("DEPT-CC");
		
		//只需要設置Name、Id資料庫產生
		Manager02 manager = new Manager02();
		manager.setMgrName("MGR-CC");
		
		//設置關聯關係
		manager.setDept(department);
		department.setMgr(manager);
		
		//保存操作
		//因為是關連到主鍵，而主鍵唯一不得為空，所以即使先save(department)他也會優先去執行save(manager)再去執行save(department)。
		//先後順序不影響。(若是關連到FK通常department先save()會讓SQL多產生一個update，因為需關聯mgr_id，就先設置空再去update)
		session.save(manager);
		session.save(department);
	}
	
	/**
	 *	查詢
	 *	注意:測試懶加載異常，需要先去destroy()關閉commit()、close()
	 * */
	@Test
	public void oneToOne_Get() {
		//測試懶加載
		
		Department02 dept = (Department02) session.get(Department02.class, 7);
		System.out.println(dept.getDeptName());
		
		//因為你再manager02表格看不到FK，所以只能去join Department02的表格，再拿mgr_id去比對dept_id(跟FK不同)這邊是拿主鍵連接，但FK需要去配置property-ref屬性(變成mgr.mgr_id比對dept.mgr_id，這邊PK不使用)。
		Manager02 mgr = dept.getMgr();
		System.out.println("manager查詢會多一道left outer join指令: "+mgr.getMgrName());
	}
	
	@Test
	public void oneToOne() {

	}
	
}
