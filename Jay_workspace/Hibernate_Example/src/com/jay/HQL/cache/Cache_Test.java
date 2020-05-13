package com.jay.HQL.cache;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
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

public class Cache_Test {

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
	*	二級緩存 : 
	*		(1).緩存的範圍分為3類: 
	*			1.事務範圍（單Session即一級緩存） 
   	*				事務範圍的緩存["只能被當前事務訪問"],每個事務都有各自的緩存,緩存內的數據通常採用相互關聯的對象形式.緩存的生命週期依賴於事務的生命週期,只有當事務結束時,緩存的生命週期才會結束.事務範圍的緩存使用內存作為存儲介質,一級緩存就屬於事務範圍. 
	*			2.應用範圍（單SessionFactory即二級緩存） 
   	*				應用程序的緩存可以被應用範圍內的所有事務共享訪問.緩存的生命週期依賴於應用的生命週期,只有當應用結束時,緩存的生命週期才會結束.應用範圍的緩存可以使用內存或硬盤作為存儲介質,二級緩存就屬於應用範圍. 
	*			3.集群範圍（多SessionFactory） 
   	*				在集群環境中,緩存被一個機器或多個機器的進程共享,緩存中的數據被複製到集群環境中的每個進程節點,進程間通過遠程通信來保證緩存中的數據的一致,緩存中的 數據通常採用對象的鬆散數據形式.
   	*		(2).SessionFactory緩存又分兩類:
   	*			1.內置緩存:Hibernate自帶的，不可卸載，通常在Hibernate初始化階段，Hibernate會把映射數據跟預定SQL放在SessionFactory緩存，所映射的文件就是*.hbm.xml這個檔，該內置只是讀而已。
   	*			2.外置緩存:一個可配置的緩存套件，默認下SessionFactory不會起用這個套件，外置緩存是可以是"內存"或是"硬碟"(當內存用量大時可以使用硬碟存)。
   	*
   	*		(3).那些數據適合在二級緩存中?
   	*			適合
   	*				●很少被修改的數據。 
	*				●不是很重要的數據，允許出現偶爾並發的數據。 
	*				●不會被並發訪問的數據。 
	*				●常量數據。 
	*				●不會被第三方修改的數據 
	*				
	*			不適合放在二級緩存中： 
	*				●經常被修改的數據。 
	*				●財務數據，絕對不允許出現並發問題。 
	*				●與其他應用共享的數據。 
   	*
   	*		(4).一級緩存 vs 二級緩存 :
   	*				[一級緩存] : (a).不須配置(本身沒保護機制)  (b).session緩存內的物件，當關閉session就會自動銷毀 
   	*				[二級緩存] : (a).先初始化該表格所有數據 -> 把所有數據根據"ID"放進二級緩存中 
   	*							(b).當Hibernate根據ID訪問數據的時候，"首先到session緩存(一級)中找"，若找不到的話會"往sessionFactory緩存(二級)中找數據"(前提是有設置二級緩存)，如果再找不到就從資料庫找。
   	*							(c).刪除、更新、增加數據的時候，同時更新緩存。
	*/
	@Test
	public void Cache_test() {
		Employee employee = (Employee)session.get(Employee.class, 100);
		System.out.println(employee.getName());
		
		//提交transaction ， 關閉session
		transaction.commit();
		session.close();
		
		//重新啟動session、transaction
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Employee employee2 = (Employee)session.get(Employee.class, 100);
		System.out.println(employee2.getName());
	}
/**		(二級緩存[執行緒範圍或群集範圍]):										(一級緩存[事務範圍]):
 * 		 	該緩存是sessionFactory級別，因為sessionFactory製造session，				該緩存是session級別，當做一樣的動作時，會從session直接拿，並不會再去查詢一次，條件是要在同一條session才可以，
			所以當不同session並不會影響，二級緩存是存在更上層的sessionFacory。			層級表示圖 sessionFactory -> session -> transaction
		
		Hibernate: 															Hibernate: 
			select														   		 select
				employee0_.ID as ID1_1_0_,											employee0_.ID as ID1_1_0_,
				employee0_.NAME as NAME2_1_0_,										employee0_.NAME as NAME2_1_0_,
				mployee0_.SALARY as SALARY3_1_0_,									employee0_.SALARY as SALARY3_1_0_,
				employee0_.EMAIL as EMAIL4_1_0_,									employee0_.EMAIL as EMAIL4_1_0_,
				employee0_.DEPT_ID as DEPT_ID5_1_0_ 								employee0_.DEPT_ID as DEPT_ID5_1_0_ 
			from														   		 from
				EMPLOYEES_HQL employee0_											 EMPLOYEES_HQL employee0_ 
			where														   		 where
				employee0_.ID=?													     employee0_.ID=?
																			[King]
																			
			[King]															Hibernate: 
			[King]														    	select
																	        		employee0_.ID as ID1_1_0_,
																			        employee0_.NAME as NAME2_1_0_,
																			        employee0_.SALARY as SALARY3_1_0_,
																			        employee0_.EMAIL as EMAIL4_1_0_,
																			        employee0_.DEPT_ID as DEPT_ID5_1_0_ 
																			    from
																			        EMPLOYEES_HQL employee0_ 
																			    where
																			        employee0_.ID=?
																			[King]
*/
	
	/**
	 * 測試對集合使用二級緩存:(注意:需要配置集合的元素對應的持久化類也使用二級緩存，否則會多很多SQL[EX:Department內的emps集合的類別是Employee，所以Employee也要設置二級緩存])
	 * 		方法一:(到hibernate.cfg.xml設置)
	 * 		<collection-cache usage="read-write" collection="com.jay.HQL.entities.Department.emps"/>
	 * 
	 * 		方法二:(到*.hbm.xml設置)
	 *         <set name="emps" table="EMPLOYEES_HQL" inverse="true" lazy="true">
	 *	        	<cache usage="read-write"/>   <--- 針對<set>裡面設置，這樣才能設置到集合
	 *	            <key>
	 *	                <column name="DEPT_ID" />
	 *	            </key>
	 *	            <one-to-many class="com.jay.HQL.entities.Employee"/>
	 *	        </set>
	 */
	@Test
	public void collectionSecondCache_test() {
		Department dept = (Department) session.get(Department.class,80);
		System.out.println(dept.getName());
		System.out.println(dept.getEmps().size());
		
		//提交transaction ， 關閉session
				transaction.commit();
				session.close();
				
		//重新啟動session、transaction
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Department dept2 = (Department) session.get(Department.class,80);
		System.out.println(dept2.getName());
		System.out.println(dept2.getEmps().size());
	}
	
	/**
	 * HQL跟QBC查詢緩存:
	 * 		(1).在hibernate配置文件中聲明"開啟查詢緩存"。<property name="cache.use_query_cache">true</property>
	 * 		(2).調用Query或Criteria的setCacheable(true)方法。
	 * 		(3).若HQL跟QBC需要使用到緩存，請記得另外設置xml。
	 * 		(4).注意:若要配置查詢緩存，必須先設置Hibernate的二級緩存。
	 */
	@Test
	public void queryCache_test() {
		
		//HQL範例
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		//第一次查詢
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		//第二次查詢看是否有重印SQL
		emps = query.list();
		System.out.println(emps.size());
		
		
		//QBC範例
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.setCacheable(true);
	}
	
	/**
	 * 時間戳緩存區:
	 * 		(步驟1).T1時刻執行"查詢操作"，得到的"查詢結果"存在QueryCache區域，為T1新增一個時間戳記。
	 *  	(步驟2).T2時刻執行"更新動作"，得到的"查詢結果"存在UpdateTimestampCache區域。
	 *  	(步驟3).T3時刻在T1跟T2後面:
	 *  		[順序情況1 T2 跟 T3時間接近] 丟棄原來存放的QueryCache區域查詢結果，重新到資料庫查詢新結果，再存在QueryCache區域。
	 *  		[順序情況2 T1 跟 T3時間接近] 直接存QueryCache區域獲取查詢結果。
	 */
	@Test
	public void updateTimeStampCache_test() {
		
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		//第一次查詢(T1)
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		
		//故意更改資料，這樣跟緩存就會不同步(T2)
		Employee employee = (Employee)session.get(Employee.class, 100);
		employee.setsalary(300000);
		
		//第二次查詢為何會自動要查詢? 如何知道資料不同步? Hibernate會去比對時間戳
		emps = query.list();
		System.out.println(emps.size());
	}
	
	/**
	 * Iterate()方法查詢
	 * 		(1).沒有特殊情況下，不建議使用，須滿足多個條件下，速度才會比較快。
	 * 		(2).範例中department ID=80 ; Employee.dept.id = 80 ; 兩個相同SQL會減少，但不同會多出很多SQL。
	 */
	@Test
	public void queryIterate_test() {
		Department dept = (Department) session.get(Department.class,70);
		//select id=70 印出姓名
		System.out.println(dept.getName());
		//select department.emps的id=70 總數 重點!!! 也就是等於查了整個department.emps.id=70下的資料，緩存會有id=70的全部資料。
		System.out.println(dept.getEmps().size());
		
		//select Employee 底下的department.ID = 80 ，回傳是所有Employee資料只要dept.id=80就回傳該Employee的ID
		Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
		
		Iterator<Employee> empIt = query.iterate();
		while(empIt.hasNext()) {
			//因為"dept.getEmps().size()"是查id=70，緩存是70的資料，所以只能將Query的 emp.id 一個一個去查
			System.out.println(empIt.next().getName());
		}
	}
	
	@Test
	public void queryIterate02_test() {
		Department dept = (Department) session.get(Department.class,80);
		//select id=80 印出姓名
		System.out.println(dept.getName());
		//select department.emps的id=80 總數 。 重點!!! 也就是等於查了整個department.emps下的資料，緩存會有全部資料。
		System.out.println(dept.getEmps().size());
		
		//select Employee 底下的department.ID = 80 ，回傳是所有Employee資料只要dept.id=80就回傳該Employee的ID
		Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
		
		//測試是否有取department的emps的緩存，答案:沒有。(兩者並不相同，初始化過程也不同，一個是Employee表格，一個是Department中的Employee集合)
		//List<Employee> emps = query.list();
		//System.out.println(emps.size());
		
		Iterator<Employee> empIt = query.iterate();
		while(empIt.hasNext()) {
			//因為"dept.getEmps().size()"是查id=80，緩存是80的資料，剛好對應到，直接去緩存拿不需要再查。
			System.out.println(empIt.next().getName());
		}
	}
}
