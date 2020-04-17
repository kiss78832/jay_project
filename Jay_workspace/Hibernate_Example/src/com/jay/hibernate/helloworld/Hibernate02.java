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
		News news = (News)session.get(News.class, 8);
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
		news.setDate_(new Date(2020-10-29));
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
	
	
	@Test
	public void Test() {
//		News news = new News("Java","Jay",new Date(new java.util.Date().getTime()));
//		session.save(news);
		
		News news = (News)session.get(News.class,8);
		
		News news2 = (News)session.get(News.class,8);
	
		System.out.println("����1: "+news);
		System.out.println("����2: "+news2);
	}
}
