package com.jay.hibernate.helloworld;


import java.sql.Connection;
import java.sql.SQLException;
import java.util.Date;

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
		News news = (News)session.get(News.class,2);
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
		news.setDate_(new Date());
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
	
	/**
	 * get() VS load()
	 * 		(1).執行get方法(立即檢索):會立即加載對象。(就算news沒有被引用，還是會立即去執行SQL語句。)
	 * 		           執行load方法(延遲檢所):若不適合該對象，則不會立即執行查詢操作，而返回一個代理對象。(當session去get()時返回的物件，若沒有被調用則不會去執行SQL，就像把System.out.println()註解沒有去印news的話，則不會去執行SQL語句。)
	 * 
	 * 		(2).若資料庫中沒有對應的紀錄，Session也沒有被關閉(ex:當索引不存在)
	 * 				get():返回null。(邏輯:不能執行就直接說不行)
	 * 				load():若不使用該對象的任何屬性則"沒問題"; 若需要初始化，則拋出異常。(先答應後，結果不能執行就會產生異常)
	 * 
	 * 		(3).當關閉Session時，再取值會有不同的狀況。
	 * 			get()方法:可以順利的去的，當session.get()過後，返回是一個數據，所以關閉session再取值，則還是取得到。
	 * 			load()方法:"可能"會拋出LazyInitializationException異常 : 在需要初始化代理對象之前已經關閉Session。因為load()返回是一個代理對象[com.jay.hibernate.helloworld.News_$$_jvstecd_0]。
	 * 			
	 * 		       ★若要載入本地端圖片，寫法請參考，可看Hibernate 10章 22:05分
	 * 			News news = new News();
	 * 			InputStream stream = new FileInputStream("路徑");
	 * 			Blob image = Hibernate.getLobCreator(session).createBlob(stream,stream.available());
	 * 			
	 * 			news.setImage(image);
	 * 			session.save(news);
	 * 			
	 * */
	@Test
	public void getTest() {
		News news = (News)session.get(News.class, 8);
		
		session.close();
		System.out.println(news);
	}
	
	//測試前，先把destroy()取消commit()、close()，才會報出 LazyInitializationException 異常
	@Test
	public void loadTest() {
		//這邊news是返回一個代理對象
		News news = (News) session.load(News.class, 8);
		System.out.println(news.getClass().getName());
		
		session.close();
		System.out.println(news);
	}
	
	/**
	 * update():
	 *    (1).若更新一個持久化對象，不需要顯示的調用update()方法，因為調用Transaction的commit()方法時，會先執行session的flush方法。
	 *    (2).更新一個"游離對象"，需要調用session的update方法，可以把一個游離對象變成持久化對象
	 * 
	 * 注意:
	 *    (1).無論要更新的游離對象和資料庫的紀錄是否一致，都會發送UPDATE語句，但有時候會出問題，有些監聽器是在update()會做一些事情，這時候就會影響到。[注意這是針對游離對象才會主動執行update的SQL]
	 *    		解決自動下UPDATE語句:
	 *    				a.在*.hbm.xml文件的class節點設置 [select-before-update = true(默認false)]
	 *    				b.設置select-before-update = true，會從原本下UPDATE改為SELECT語句。
	 *    (2).若範例沒有關閉session，拿持久化對象去update，假設持久化對象與資料庫內容相同，則"不會"主動執行UPDATE語句。
	 *    (3).當update()方法關連到一個游離對象時:[範例:updateTest02()]
	 *    		如果Session的緩存中已經存在相同OID的持久化對象"會拋出異常"，因為在Session緩存中不能有兩個OID相同對象!
	 *    
	 * */
	@Test
	public void updateTest() {
		//這邊會先selec一次，持久化狀態
		News news = (News) session.get(News.class, 8);
		
		//刻意關掉session
		transaction.commit();
		session.close();
		
		//再創一個新的session
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		//若沒有update()則等於游離狀態，執行update()才能變成持久化對象
		news.setAuthor("測試03");
		session.update(news);
	}
	
	@Test
	public void updateTest02() {
		//創建一個索引為8的news，這邊稱session_A，留在session緩存中
		News news = (News) session.get(News.class, 8);
		
		//刻意關掉session_A，讓news變成游離狀態
		transaction.commit();
		session.close();
		
		//再創一個新的session_B
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		//session_B創一個持久化對象，ID為8，到這邊都還不會出異常
		News news2 = (News) session.get(News.class, 8);
		
		//這邊會出異常，因為把游離狀態update後會變成持久化狀態，當持久化狀態已經有一個ID = 8的對象，自然news變成持久化狀態會出異常。
		session.update(news);
	}
	
	
	/**
	 * saveOrUpdate()
	 * 	  (1).若OID不是為空，指定一個在資料庫沒有的ID，會出現在異常，並不會主動去執行INSERT
	 * 			特例狀況:在*.hbm.xml在ID的欄位 <id> 新增一個屬性[unsaved-value="xxx"]屬性值的對象，也被認為一個游離對象。
	 * 	  (2).unsaved-value="id"設置的屬性，會一直使用insert語句，假設連續執行兩次會出現異常，因為ID有資料還繼續insert會衝突。
	 * 	  (3).News對象若是 1.臨時對象 -> save()   2.游離對象 ->update()
	 * 
	 * 
	 * */
	@Test
	public void saveOrUpdateTest() {
		//若news沒有指定ID就是sequence自動產生，則會執行INSERT，是臨時對象就會INSERT，這邊id目前是null狀態
		News news = new News("ZZA","zz",new Date(2020-10-10));
		
		//1.這邊指定ID資料庫有的資料，就會去執行UPDATE語句。
		//2.但如果你設置是"sequence"，即時你設置id是資料庫沒有的，你也同時設置unsaved-value="id"(沒設會出異常，因為null狀態才會自動新增，
		      //若id是資料庫裡面沒有的又不是null就會出異常)，即使新增也是按照"sequence的排序id去新增。
		news.setId(101);
		session.saveOrUpdate(news);
		
	}
	
	/**
	 * delete()
	 * 	  (1).執行刪除操作，只有OID和資料庫其中一筆紀錄有對應，就會準備執行delete操作，並不是馬上刪除，會在flush()內刪除。
	 * 	  (2).若OID在資料庫中沒有對應的紀錄，則會拋出異常。
	 * 
	 * 注意:
	 *    (1).刪除後，還沒有完全被垃圾回收，所以在去save是有效的。但是如果刪除id=1，再新增一個id=1，會出現異常，因為裡面的id=1還沒完全刪除。
	 *        解決辦法:在"hibernate.cfg.xml"設置<property name="use_identifier_rollback">true</property>，不會讓該對象留著id，會設置為null
	 * */
	@Test
	public void deleteTest() {
		//第一種刪除方式，單純指定id，記得要對應資料庫是否存在id
		News news = new News();
		news.setId(1);
		
		//第二種刪除方式，塞進一個持久化對象，一樣也是可以刪除
		News news2 = (News)session.get(News.class, 112);
		
		session.delete(news);
		System.out.println("被刪除還是存在id: "+news);
		//注意這邊刪除後id還是留存著也還沒執行delete語句，還是可以針對news進行操作，除非去hibernate.cfg.xml設置<property name="use_identifier_rollback">true</property>
		news = new News("test","test",new Date(9999-99-99));
		session.save(news);
	
	}
	
	/**
	 * evict():
	 * 	 (1).字義:逐出
	 * 	 (2).從session緩存中把指定的"持久化對象"踢除變成"游離狀態"。
	 * */
	@Test
	public void evictTest() {
		News news1 = (News)session.get(News.class, 1);
		News news2 = (News)session.get(News.class, 2);
		
		news1.setTitle("AAA");
		news2.setTitle("BB");
		
		//只會成功更改news2
		session.evict(news1);
	}
	
	
	/**
	 * 
	 * doWork()
	 * 可以從事底層操作，印出來是JDBC Connection物件
	 * 配完C3P0在執行會從C3P0拿到Connection代理[com.mchange.v2.c3p0.impl.NewProxyConnection@7502291e]
	 * */
	@Test
	public void doWorkTest() {
		
		session.doWork(new Work() {

			@Override
			public void execute(Connection conn) throws SQLException {
				System.out.println(conn);
			}
		});
	}
	
	//
	@Test
	public void componentTest() {
		Worker worker = new Worker();
		Pay pay = new Pay();
		
		pay.setMonthlyPay(1000);
		pay.setVocationWithPay(5);
		pay.setYearPay(8000000);
		
		worker.setName("Test");
		worker.setPay(pay);
		
		session.save(worker);
	}
	
}
