package com.jay.HQL.dao;

import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

public class HibernateUtils {
	
	private HibernateUtils() {}
	
	/**
	 * ��ҼҦ�:
		getInstance�o�Ӥ�k�b��ҼҦ��Ϊ��Ʀh�A���F�קK�鷺�s�y�����O�A����ݭn��ҤƸ������ɭԤ~�N���ҤơA�ҥH��getInstance������ӹ�H�A
		�ܩ��L�ɭԡA�]�N�O���F²�K�Ӥw�A���F�����{�Ǧb��Ҥƹ�H���ɭԡA���ΨC������new����r�A���ʴ��Ѥ@��instance��k�A�����@����o�����N
		��l�ơA�o�˰��줣���O�t�θ귽�I��ҼҦ� �i�H���� �ƾڪ��R��A�`�٤��s�Ŷ�
	*/
	private static HibernateUtils instance = new HibernateUtils(); 
	
	public static HibernateUtils getInstance() {
		return instance;
	}
	
	private SessionFactory sessionFactory;
	
	public SessionFactory getSessionFactory() {
		if(sessionFactory == null) {
			Configuration configuration = new Configuration().configure();
			ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
					.applySettings(configuration.getProperties()).build();
			
			sessionFactory = configuration.buildSessionFactory(serviceRegistry);
		}
		return sessionFactory;
	}
	
	public Session getSession() {
		/*
		 * getCurrentSession�PopenSession���ϧO:
		 * 		(1).getCurrentSession�Ыت�session�|�M�j�w���e�u�{,��openSession���|�C
		 * 		(2).getCurrentSession�Ыت��u�{�|�b"�ưȦ^�u"��"�ƪ�����(commit)"�Z"�۰�����",��openSession�����������
		 * 		(3).getCurrentSession()->�ϥη�e��session ; openSession()->���s�إߤ@�ӷs��session
		 * 		(4).�ϥ�getCurrentSession()�� �n�bhibernate.cfg.xml��󤤶i��p�U�]�m:
		 * 				(A).���a�ưȡ]jdbc�ưȡ^: <property name="hibernate.current_session_context_class">thread</property>
		 * 				(B).�����ưȡ]jta�ưȡ^: <property name="hibernate.current_session_context_class">jta</property>
		 */
		return getSessionFactory().getCurrentSession();
	}
}
