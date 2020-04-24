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
	
	//�������Ҥ~�|�����n��
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;
	
	@Before
	public void init(){
		//��l��
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
		
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		
		System.out.println("�{����l�ƽե�init()");
	}
	
	@After
	public void destroy() {
		//�����ͩR�g��
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("�����ͩR�g���e�ե�destroy()");
	}
	
	/**
	 * flush()��ɽե�?��@�άO����?: �d��:[sessionFlush()�BQBCTest()]
	 * 		(1).�b����transaction.commit()�e���w�|����flush()�C
	 * 		(2).flush()�@��:
	 * 				a.�O���Ÿ�Ʈw��session�w�s��Ƭ۵��A�ҥHflush()�Y�o�{�����Ƥ���ٷ|SQL�y�y�Uupdate()�A
	 * 				     �����O���|���Acommit()�A�ҥH�٬O�ncommit���ʧ@�C
	 * 				b.flush()�@�ΰ��F����session�w�s��ƩM��Ʈw�@�ˡA�٦��e�XSQL�y�y���O�z�Lfulsh()�Ӱe�X�A
	 * 				      �bconsole�ݨ쪺SQL�N�O�z�Lflush()�L�X�Ӫ��C
	 * 		(5).�`�N:��S������transaction.commit()��flush()�ɡA�U�C�X�ت��p�]�|�۰ʰ���fulsh()�C
	 * 				a.����HQL�MQBC�d�߮ɡA�|���i��flush()�C[��]�b��d�ߥ����n�O�̷s��ơA����ʤ~�|����SQL Update()]
	 * 				b.���Ʈw��key�ȬO��sequence�ۼW�D���A�@�w�|���e�XInsert into�y�y�A�]���ۼW�D�����w�n�s�W�@���A����|���ѡC
	 * flush()��commit()�t�O:
	 * 		(1).flush�|����@�t�CSLQ�y�y�A��������ư�(���)�C
	 * 		(2).commit()��k�եΫe���|����flush()�A�M�ᴣ��ư�(���)�C
	 * 
	 * refresh():�קKSession�w�s���Ʈw���P�B�A�u�n����N�|select�@���A�F��ʤ��ʤ@�P�C��ƴN�⥼��ʤ]�|�j��select
	 * 
	 * setFlushMode()��k:�i�H�h�ާ@flush()���檺�ɶ��I�A���q�`���ӷ|�ΡA�ݭn�ϥΦA�ۦ�google�C
	 * 
	 * */
	@Test
	public void sessionFlush() {
		News news = (News)session.get(News.class,2);
		//�ק��Ʈw����8�����
		news.setAuthor("Orcale");
	}
	
	@Test
	public void QBCTest() {
		News news = (News)session.get(News.class, 8);
		//�ק��Ʈw����8�����
		news.setAuthor("OrcaleXXXXXXXXXXX");
		
		News news2 = (News)session.createCriteria(News.class).uniqueResult();
		System.out.println(news2+","+"�Y�b�d�߫e��update��SQL�y�y�N���\�A�O�o��ƭn���P�~�|�۰�update()");
	}
	
	@Test
	public void refreshTest() {
		News news = (News)session.get(News.class, 8);
		System.out.println("refresh����1: "+news);
		
		//�`�@�|select�G���A�O���̷s���A�C�@��ާ@�u�|����@��SQL�y�y�A�]���d�ߤ��e�@�˪����h�w�sSession���N�n�C
		//���p�G�Arefresh����1��h��ʭק��ơA�b�S��refresh()�����G�N�O�Q��ʭק諸��Ʀb����2�O�d���쪺�A����2�٬O���¸�ơC
		session.refresh(news);
		System.out.println("refresh����2: "+news);
	}
	
	@Test
	public void clearTest() {
		News news = (News)session.get(News.class, 8);
		//�M�zsession�w�s����ơA�ɭPnews�|select�@���Anews2�]�|�Aselect�@���A�]���w�s�Q�M�šA�����n�A�d�@���C
		session.clear();
		News news2 = (News)session.get(News.class, 8);
	}
	
	
	/**
	 * [�d��:saveTest()�BpersistTest()]
	 * save()��k
	 * 	 (1).�Ϥ@���{�ɹ�H�ܦ����[�ƹ�H
	 * 	 (2).����H���tID(���w�]�m�ۼW�D���)
	 *   (3).�bflush�w�s�ɷ|�UINSERT SQL�y�y
	 *   (4).�bsave��k���e��ID�O�L�Ī�(���w�]�m�ۼW�D���)
	 *   (5).���[�ƹ�H��ID�O����Q�ק諸!
	 * 
	 * persist()��k
	 * 	 (1).�]�|����INSERT�ާ@
	 * 	 (2).�Msave()�ϧO -> �I�spersist()��k���e�A�Y��H�w��ID�F�A�h���|�bINSERT,�|�ߥX���`�C
	 * */
	@Test
	public void saveTest() {
		News news = new News();
		news.setTitle("AAC");
		news.setAuthor("aac");
		news.setDate_(new Date());
		//����1�|�L�XID�A������save()�ɷ|�����A�̷ӦۼW�D��ȥh�Ͳ�ID�A�Y�S���h�O�i�H�h�]�mID���C
		news.setId(100);
		
		System.out.println("save()����1: "+news);
		session.save(news);
		System.out.println("save()����2: "+news);
		//news.setId(101);  �����save()�ɡA�w�ഫ�����[�ƪ��A�A����A�h�ק�C
	}
	
	@Test
	public void persistTest() {
		News news = new News();
		news.setTitle("BB");
		news.setAuthor("bb");
		news.setDate_(new Date(2020-10-29));
		//news.setId(100);  ����]�mID�A�|�߲��`�A�ݩ�persist()�S�ʡC
		
		session.persist(news);
	}
	
	/**
	 * get() VS load()
	 * 		(1).����get��k(�ߧY�˯�):�|�ߧY�[����H�C(�N��news�S���Q�ޥΡA�٬O�|�ߧY�h����SQL�y�y�C)
	 * 		           ����load��k(�����˩�):�Y���A�X�ӹ�H�A�h���|�ߧY����d�߾ާ@�A�Ӫ�^�@�ӥN�z��H�C(��session�hget()�ɪ�^������A�Y�S���Q�եΫh���|�h����SQL�A�N����System.out.println()���ѨS���h�Lnews���ܡA�h���|�h����SQL�y�y�C)
	 * 
	 * 		(2).�Y��Ʈw���S�������������ASession�]�S���Q����(ex:����ޤ��s�b)
	 * 				get():��^null�C(�޿�:�������N����������)
	 * 				load():�Y���ϥθӹ�H�������ݩʫh"�S���D"; �Y�ݭn��l�ơA�h�ߥX���`�C(��������A���G�������N�|���Ͳ��`)
	 * 
	 * 		(3).������Session�ɡA�A���ȷ|�����P�����p�C
	 * 			get()��k:�i�H���Q���h���A��session.get()�L��A��^�O�@�ӼƾڡA�ҥH����session�A���ȡA�h�٬O���o��C
	 * 			load()��k:"�i��"�|�ߥXLazyInitializationException���` : �b�ݭn��l�ƥN�z��H���e�w�g����Session�C�]��load()��^�O�@�ӥN�z��H[com.jay.hibernate.helloworld.News_$$_jvstecd_0]�C
	 * 			
	 * 		       ���Y�n���J���a�ݹϤ��A�g�k�аѦҡA�i��Hibernate 10�� 22:05��
	 * 			News news = new News();
	 * 			InputStream stream = new FileInputStream("���|");
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
	
	//���իe�A����destroy()����commit()�Bclose()�A�~�|���X LazyInitializationException ���`
	@Test
	public void loadTest() {
		//�o��news�O��^�@�ӥN�z��H
		News news = (News) session.load(News.class, 8);
		System.out.println(news.getClass().getName());
		
		session.close();
		System.out.println(news);
	}
	
	/**
	 * update():
	 *    (1).�Y��s�@�ӫ��[�ƹ�H�A���ݭn��ܪ��ե�update()��k�A�]���ե�Transaction��commit()��k�ɡA�|������session��flush��k�C
	 *    (2).��s�@��"������H"�A�ݭn�ե�session��update��k�A�i�H��@�Ӵ�����H�ܦ����[�ƹ�H
	 * 
	 * �`�N:
	 *    (1).�L�׭n��s��������H�M��Ʈw�������O�_�@�P�A���|�o�eUPDATE�y�y�A�����ɭԷ|�X���D�A���Ǻ�ť���O�bupdate()�|���@�ǨƱ��A�o�ɭԴN�|�v�T��C[�`�N�o�O�w�������H�~�|�D�ʰ���update��SQL]
	 *    		�ѨM�۰ʤUUPDATE�y�y:
	 *    				a.�b*.hbm.xml���class�`�I�]�m [select-before-update = true(�q�{false)]
	 *    				b.�]�mselect-before-update = true�A�|�q�쥻�UUPDATE�אּSELECT�y�y�C
	 *    (2).�Y�d�ҨS������session�A�����[�ƹ�H�hupdate�A���]���[�ƹ�H�P��Ʈw���e�ۦP�A�h"���|"�D�ʰ���UPDATE�y�y�C
	 *    (3).��update()��k���s��@�Ӵ�����H��:[�d��:updateTest02()]
	 *    		�p�GSession���w�s���w�g�s�b�ۦPOID�����[�ƹ�H"�|�ߥX���`"�A�]���bSession�w�s�����঳���OID�ۦP��H!
	 *    
	 * */
	@Test
	public void updateTest() {
		//�o��|��selec�@���A���[�ƪ��A
		News news = (News) session.get(News.class, 8);
		
		//��N����session
		transaction.commit();
		session.close();
		
		//�A�Ф@�ӷs��session
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		//�Y�S��update()�h����������A�A����update()�~���ܦ����[�ƹ�H
		news.setAuthor("����03");
		session.update(news);
	}
	
	@Test
	public void updateTest02() {
		//�Ыؤ@�ӯ��ެ�8��news�A�o���session_A�A�d�bsession�w�s��
		News news = (News) session.get(News.class, 8);
		
		//��N����session_A�A��news�ܦ��������A
		transaction.commit();
		session.close();
		
		//�A�Ф@�ӷs��session_B
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		//session_B�Ф@�ӫ��[�ƹ�H�AID��8�A��o�䳣�٤��|�X���`
		News news2 = (News) session.get(News.class, 8);
		
		//�o��|�X���`�A�]����������Aupdate��|�ܦ����[�ƪ��A�A����[�ƪ��A�w�g���@��ID = 8����H�A�۵Mnews�ܦ����[�ƪ��A�|�X���`�C
		session.update(news);
	}
	
	
	/**
	 * saveOrUpdate()
	 * 	  (1).�YOID���O���šA���w�@�Ӧb��Ʈw�S����ID�A�|�X�{�b���`�A�ä��|�D�ʥh����INSERT
	 * 			�S�Ҫ��p:�b*.hbm.xml�bID����� <id> �s�W�@���ݩ�[unsaved-value="xxx"]�ݩʭȪ���H�A�]�Q�{���@�Ӵ�����H�C
	 * 	  (2).unsaved-value="id"�]�m���ݩʡA�|�@���ϥ�insert�y�y�A���]�s�����⦸�|�X�{���`�A�]��ID��������~��insert�|�Ĭ�C
	 * 	  (3).News��H�Y�O 1.�{�ɹ�H -> save()   2.������H ->update()
	 * 
	 * 
	 * */
	@Test
	public void saveOrUpdateTest() {
		//�Ynews�S�����wID�N�Osequence�۰ʲ��͡A�h�|����INSERT�A�O�{�ɹ�H�N�|INSERT�A�o��id�ثe�Onull���A
		News news = new News("ZZA","zz",new Date(2020-10-10));
		
		//1.�o����wID��Ʈw������ơA�N�|�h����UPDATE�y�y�C
		//2.���p�G�A�]�m�O"sequence"�A�Y�ɧA�]�mid�O��Ʈw�S�����A�A�]�P�ɳ]�munsaved-value="id"(�S�]�|�X���`�A�]��null���A�~�|�۰ʷs�W�A
		      //�Yid�O��Ʈw�̭��S�����S���Onull�N�|�X���`)�A�Y�Ϸs�W�]�O����"sequence���Ƨ�id�h�s�W�C
		news.setId(101);
		session.saveOrUpdate(news);
		
	}
	
	/**
	 * delete()
	 * 	  (1).����R���ާ@�A�u��OID�M��Ʈw�䤤�@�������������A�N�|�ǳư���delete�ާ@�A�ä��O���W�R���A�|�bflush()���R���C
	 * 	  (2).�YOID�b��Ʈw���S�������������A�h�|�ߥX���`�C
	 * 
	 * �`�N:
	 *    (1).�R����A�٨S�������Q�U���^���A�ҥH�b�hsave�O���Ī��C���O�p�G�R��id=1�A�A�s�W�@��id=1�A�|�X�{���`�A�]���̭���id=1�٨S�����R���C
	 *        �ѨM��k:�b"hibernate.cfg.xml"�]�m<property name="use_identifier_rollback">true</property>�A���|���ӹ�H�d��id�A�|�]�m��null
	 * */
	@Test
	public void deleteTest() {
		//�Ĥ@�اR���覡�A��«��wid�A�O�o�n������Ʈw�O�_�s�bid
		News news = new News();
		news.setId(1);
		
		//�ĤG�اR���覡�A��i�@�ӫ��[�ƹ�H�A�@�ˤ]�O�i�H�R��
		News news2 = (News)session.get(News.class, 112);
		
		session.delete(news);
		System.out.println("�Q�R���٬O�s�bid: "+news);
		//�`�N�o��R����id�٬O�d�s�ۤ]�٨S����delete�y�y�A�٬O�i�H�w��news�i��ާ@�A���D�hhibernate.cfg.xml�]�m<property name="use_identifier_rollback">true</property>
		news = new News("test","test",new Date(9999-99-99));
		session.save(news);
	
	}
	
	/**
	 * evict():
	 * 	 (1).�r�q:�v�X
	 * 	 (2).�qsession�w�s������w��"���[�ƹ�H"���ܦ�"�������A"�C
	 * */
	@Test
	public void evictTest() {
		News news1 = (News)session.get(News.class, 1);
		News news2 = (News)session.get(News.class, 2);
		
		news1.setTitle("AAA");
		news2.setTitle("BB");
		
		//�u�|���\���news2
		session.evict(news1);
	}
	
	
	/**
	 * 
	 * doWork()
	 * �i�H�q�Ʃ��h�ާ@�A�L�X�ӬOJDBC Connection����
	 * �t��C3P0�b����|�qC3P0����Connection�N�z[com.mchange.v2.c3p0.impl.NewProxyConnection@7502291e]
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
