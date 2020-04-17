package com.jay.hibernate.helloworld;


import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

public class Hibernate02 {
	
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
	 * flush()何時調用?其作用是什麼?: 範例:[sessionFlush()、QBCTest()]
	 * 		(1).在執行transaction.commit()前必定會執行flush()。
	 * 		(2).flush()作用:
	 * 				a.是平衡資料庫跟session緩存資料相等，所以flush()若發現兩邊資料不對稱會SQL語句下update()，
	 * 				     但切記不會幫你commit()，所以還是要commit的動作。
	 * 				b.flush()作用除了平衡session緩存資料和資料庫一樣，還有送出SQL語句都是透過fulsh()來送出，
	 * 				      在console看到的SQL就是透過flush()印出來的。
	 * 		(5).注意:當沒有執行transaction.commit()或flush()時，下列幾種狀況也會自動執行fulsh()。
	 * 				a.執行HQL和QBC查詢時，會先進行flush()。[原因在於查詢必須要是最新資料，有更動才會執行SQL Update()]
	 * 				b.當資料庫的key值是用sequence自增主見，一定會先送出Insert into語句，因為自增主見必定要新增一筆，後續會講解。
	 * flush()跟commit()差別:
	 * 		(1).flush會執行一系列SLQ語句，但不提交事務(交易)。
	 * 		(2).commit()方法調用前必會執行flush()，然後提交事務(交易)。
	 * 
	 * refresh():避免Session緩存跟資料庫不同步，只要執行就會select一次，達到百分百一致。資料就算未更動也會強制select
	 * 
	 * setFlushMode()方法:可以去操作flush()執行的時間點，但通常不太會用，需要使用再自行google。
	 * 
	 * */
	@Test
	public void sessionFlush() {
		News news = (News)session.get(News.class, 8);
		//修改資料庫索引8的資料
		news.setAuthor("Orcale");
	}
	
	@Test
	public void QBCTest() {
		News news = (News)session.get(News.class, 8);
		//修改資料庫索引8的資料
		news.setAuthor("OrcaleXXXXXXXXXXX");
		
		News news2 = (News)session.createCriteria(News.class).uniqueResult();
		System.out.println(news2+","+"若在查詢前有update的SQL語句代表成功，記得資料要不同才會自動update()");
	}
	
	@Test
	public void refreshTest() {
		News news = (News)session.get(News.class, 8);
		System.out.println("refresh測試1: "+news);
		
		//總共會select二次，保持最新狀態。一般操作只會執行一次SQL語句，因為查詢內容一樣直接去緩存Session取就好。
		//但如果再refresh測試1後去手動修改資料，在沒有refresh()的結果就是被手動修改的資料在測試2是查不到的，測試2還是拿舊資料。
		session.refresh(news);
		System.out.println("refresh測試2: "+news);
	}
	
	@Test
	public void clearTest() {
		News news = (News)session.get(News.class, 8);
		//清理session緩存的資料，導致news會select一次，news2也會再select一次，因為緩存被清空，必須要再查一次。
		session.clear();
		News news2 = (News)session.get(News.class, 8);
	}
	
	
	/**
	 * [範例:saveTest()、persistTest()]
	 * save()方法
	 * 	 (1).使一個臨時對象變成持久化對象
	 * 	 (2).為對象分配ID(限定設置自增主鍵值)
	 *   (3).在flush緩存時會下INSERT SQL語句
	 *   (4).在save方法之前的ID是無效的(限定設置自增主鍵值)
	 *   (5).持久化對象的ID是不能被修改的!
	 * 
	 * persist()方法
	 * 	 (1).也會執行INSERT操作
	 * 	 (2).和save()區別 -> 呼叫persist()方法之前，若對象已有ID了，則不會在INSERT,會拋出異常。
	 * */
	
	@Test
	public void saveTest() {
		News news = new News();
		news.setTitle("AAC");
		news.setAuthor("aac");
		news.setDate_(new Date(2020-10-29));
		//測試1會印出ID，但執行save()時會忽略，依照自增主鍵值去生產ID，若沒有則是可以去設置ID的。
		news.setId(100);
		
		System.out.println("save()測試1: "+news);
		session.save(news);
		System.out.println("save()測試2: "+news);
		//news.setId(101);  當執行save()時，已轉換成持久化狀態，不能再去修改。
	}
	
	@Test
	public void persistTest() {
		News news = new News();
		news.setTitle("BB");
		news.setAuthor("bb");
		news.setDate_(new Date(2020-10-29));
		//news.setId(100);  不能設置ID，會拋異常，屬於persist()特性。
		
		session.persist(news);
	}
	
	
	@Test
	public void Test() {
//		News news = new News("Java","Jay",new Date(new java.util.Date().getTime()));
//		session.save(news);
		
		News news = (News)session.get(News.class,8);
		
		News news2 = (News)session.get(News.class,8);
	
		System.out.println("測試1: "+news);
		System.out.println("測試2: "+news2);
	}
}
