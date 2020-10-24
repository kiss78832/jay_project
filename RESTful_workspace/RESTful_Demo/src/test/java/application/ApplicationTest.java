package application;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.application.model.ApplicationDTO;

/**
	subclass:映射繼承關係
		(1).缺點
			  a.使用了辨別者列(<discriminator>)
			  b.子類獨有的字串不能添加非null約束
			  c.若繼承層次交深，則資料庫表格的字串也會較多
 */
public class ApplicationTest {

	// 測試環境才會直接聲明
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init(){
		//初始化
		Configuration configuration = new Configuration().configure();
		System.out.println(configuration.getProperties());
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
//		System.out.println("測試"+serviceRegistry.getParentServiceRegistry());
		
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
	 * 樣式表
	 */
	@Test
	public void manyToMany() {

	}

	@Test
	public void add() {

		ApplicationDTO dto = new ApplicationDTO();
		
		dto.setAppNum("1");
		dto.setGroupId("1");
		dto.setMemberId("1");
		dto.setRouteId("1");
		dto.setEgcContact("1");
		dto.setEgcPhone("1");
		dto.setSatellite("1");
		dto.setRadio("1");
		dto.setAppStatus(1);
		dto.setFirstDay(java.sql.Date.valueOf("2002-01-01"));
		dto.setAppDays(4);
		dto.setDailybedProvided(9);
		dto.setMealProvided(9);
		dto.setLocations("1");
		session.save(dto);

	}

}
