package com.jay.hibernate.helloworld;
import java.sql.Date;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.hibernate.service.ServiceRegistryBuilder;
import org.junit.Test;
/**
 * Configuration���O:
 * 		a.(1) �h�[��hibernate.cfg.xml���C(��ƮwURL�BUsername�Bpassword�BJDBC�X�ʡB��ƮwDialect(�訥)�B�s��������)   
 * 		  (2)�[�� *.hbm.xml���C(���[�Ƽh��ƾڪ��M�g���Y)
 * 		b.�Ы�Configuration����ؤ覡:
 * 				(1) �ݩʤ��(hibernate.properties)
 * 						Configuration cfg = new Configuration();
 * 				(2)XML�榡(hibernate.cfg.xml)
 * 						Configuration cfg = new Configuration.configure();    configure()->�Y�S�ѼƴN�O�q�{ hibernate.cfg.xml   �Ѽƥi��:String�BFile���O�C
 * SessionFactory ����:
 * 		a.�ݩ�u�{�w�� �C
 * 		b.�ͦ�Session���u�t�CSessionFactory�D�`�Ӹ귽�A�@��ӻ���l�Ƥ@�ӴN�n�C
 * 
 * ServiceRegistry ����:
 * 		a.Hibernate4 �s�W�������C
 * 		b.�u�n����Hibernate�t�m�ΪA�ȳ������VServiceRegistry���U��~�ͮġC
 * 		c.Hibernate4.3����אּ : ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration.getProperties()).build();
 * Session�`�Τ�k:
 * 		a.���o���[�Ƽh�����k:
 * 			(1).get()
 * 			(2).load()
 * 		b.���[�ƪ���O�s�B��s�B�R��
 * 			(1).save()
 * 			(2).update()
 * 			(3).saveOrUpdate()
 * 			(4).delete()
 * 		c.�}�Ҩư�(���)
 * 			(1).beginTransaction()
 * 					1.commit()->�������p��session����      2.rollback()->�M�P�ư�(���)�ާ@    3.wasCommitted()->�ˬd�ư�(���)�O�_����
 * 		d.�޲zSession����k:
 * 			(1).isOpen()
 * 			(2).flush()
 * 			(3).clear()
 * 			(4).evict()
 * 			(5).close()
 * */
class Hibernate01 {

	//java.lang.Exception: No tests found matching [{ExactMatcher:fDisplayName=test01], {ExactMatcher:fDisplayName=test01(com.jay.hibernate.helloworld.Hibernate01)], {LeadingIdentifierMatcher:fClassName=com.jay.hibernate.helloworld.Hibernate01,fLeadingIdentifier=test01]] from org.junit.internal.requests.ClassRequest@42110406
	//�������`���B�z


	@Test
	public void test01() {
		
		//1.�Ыؤ@�� SessionFactory
		SessionFactory sessionFactory = null;
		//1-1 �Ы� Configuration ����A����Hibernate���򥻰t�m�T���M��������M�g�T���C�q�{�N�O:	"/hibernate.cfg.xml"
		Configuration configuration = new Configuration().configure();
		//1-2 �Ыؤ@��SericeRegistry ���� : hibernate 4.X �s�W������C hibernate ������t�m�M�A�ȳ��ݭn�b�Ӷ��V�����U��~�঳�ġC
		//sessionFactory = configuration.buildSessionFactory();  4.0���e
		
		//4.3���������˨ϥ�
//		ServiceRegistry serviceRegistry = new ServiceRegistryBuilder().applySettings(configuration.getProperties())
//																	  .buildServiceRegistry();
		
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder().applySettings(configuration .getProperties()).build();
	    
		//1-3
		sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		
		//2.�Ыؤ@�� Session��H
		Session session = sessionFactory.openSession();
		
		//3.�}�Ҩư�(���) : �Y���}�Ҥ��|�����A�줣�|�s�W���Ʈw�̭��C
		Transaction transaction = session.beginTransaction();
		
		//4.����O�s�ާ@
		News news = new News("Java","Jays",new Date(new java.util.Date().getTime()));
		session.save(news);
		
		//���o��Ʈw����A�i���w����
//		News news2 = (News) session.get(News.class, 1);
//		System.out.println(news2);
		
		//5.����ư�(���)
		transaction.commit();
		
		//6.���� Session
		session.close();
		
		//7.���� SessionFactory ����
		sessionFactory.close();
	}
}
