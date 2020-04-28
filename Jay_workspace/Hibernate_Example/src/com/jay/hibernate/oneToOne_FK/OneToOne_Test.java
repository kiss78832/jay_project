package com.jay.hibernate.oneToOne_FK;


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
public class OneToOne_Test {
	
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
		Department department = new Department();
		department.setDeptName("DEPT-AA");
		
		//只需要設置Name、Id資料庫產生
		Manager manager = new Manager();
		manager.setMgrName("MGR-AA");
		
		//設置關聯關係
		department.setMgr(manager);
		manager.setDept(department);
		
		//保存操作
		//department(PK:dept_id UK:mgr_id);manager(PK:mgr_id)
		//建議先保存沒有外來鍵的欄位，這樣會減少update語句，department先 manager後 (產生一個update)，manager先 ，department後(不產生update)
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
		
		// 1.默認情況下對關聯屬性使用懶加載(懶加載:使用到才會去查詢該欄位。)
		// 2.所以會出現懶加載異常問題，有懶加載功能->就會有代理對象->有懶加載就會有懶加載異常->而懶加載異常當關閉session時，又去CRUD該欄位，自然會出現異常，因為該欄位是代理對象處理的。
		Department dept = (Department) session.get(Department.class, 2);
		System.out.println(dept.getDeptName());
		
		session.close();
		
		Manager mgr = dept.getMgr();
		System.out.println("找class不會出現異常，去找mgr屬性才會出現懶加載異常: "+mgr.getClass());
	}
	
	@Test
	public void oneToOne_Get02() {
		//測試property-ref屬性的效果
		
		//(dept 第一次select SQL)
		Department dept = (Department) session.get(Department.class, 2);
		System.out.println(dept.getDeptName());
		
		//(mgr 第二次select SQL 取得Department所設置的manager)  
		//left outer join on manager.MGR_ID=department.DEPT_ID 查詢結果不對，這邊的mgr是從department那邊取的，正確應該是department.MGR_ID(必須去Manager.xml設置property-ref屬性)
		Manager mgr = dept.getMgr();
		//要去操作mgr表格，當然是拿dept的mgr_id去查mgr的mgr_id，相同才能知道是哪筆資料。
		System.out.println(mgr.getMgrName());
	}
	
	@Test
	public void oneToOne_Get03() {
		//測試mgr沒有dept_id欄位怎麼映射到department表格
		
		//在查詢沒有外來鍵的實體對象時，使用的左外(left outer)查詢，一併查詢出其相關對象，並已經進行初始化。(Hibernate內部會操控)
		Manager mgr = (Manager) session.get(Manager.class, 1);
		System.out.println(mgr.getMgrName());
		System.out.println(mgr.getDept().getDeptName());
	}
	
}
