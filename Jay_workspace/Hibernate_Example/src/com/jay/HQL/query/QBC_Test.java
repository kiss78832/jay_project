package com.jay.HQL.query;

import org.hibernate.Criteria;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.criterion.Conjunction;
import org.hibernate.criterion.Disjunction;
import org.hibernate.criterion.MatchMode;
import org.hibernate.criterion.Order;
import org.hibernate.criterion.Projections;
import org.hibernate.criterion.Restrictions;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jay.HQL.entities.Department;
import com.jay.HQL.entities.Employee;

public class QBC_Test {

	// �������Ҥ~�|�����n��
	private SessionFactory sessionFactory;
	private Session session;
	private Transaction transaction;

	@Before
	public void init() {
		// ��l��
		Configuration configuration = new Configuration().configure();
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();

		sessionFactory = configuration.buildSessionFactory(serviceRegistry);

		session = sessionFactory.openSession();
		transaction = session.beginTransaction();

		System.out.println("�{����l�ƽե�init()");
	}

	@After
	public void destroy() {
		// �����ͩR�g��
		transaction.commit();
		session.close();
		sessionFactory.close();
		System.out.println("�����ͩR�g���e�ե�destroy()");
	}

	/**
	 * QBC�d�� :
	 * 	 (1).���ǮɭԷ|��HQL�٭n�֡A�|��:�w��Employee�o�����O�ڷQ�d��email�OSKUMAR�~��O�j��5000���H�A�|�S�O�֡C
	 *   (2).QBC�W�[SQL���iŪ�ʡA�ܦh�ɭ�HQL��g���������N�����Ū��@�̪��޿�C
	 *   (3).�򥻬y�{:
	 *   		(3-1)�Ы�session -> �Ы�Transaction -> �Ы�Criteria -> [Criteria�����J] -> Criteria.list()�d�ߨæ^�Ƕ��X
	 *   			 [�ݻݭn�O�_��foreach�����] -> commit Transaction -> ����session
	 *   (4).Restrictions �� Expression �Ϊk�@�ˡAExpression�w�g�v���QRestrictions���N�C
	 *   
	 */
	@Test
	public void QBC_test() {
			
		//(1).�Ыؤ@��Criteria��H
		Criteria criteria = session.createCriteria(Employee.class);
		
		//(2).�K�[�d�߱���
		//Criterion �i�H�q�LRestrictions���R�A��k�o��
		criteria.add(Restrictions.eq("email", "SKUMAR"));
		criteria.add(Restrictions.gt("salary",5000f));
		
		//(3).����d��
		Employee employee = (Employee) criteria.uniqueResult();
		System.out.println(employee);
	}

	/**
	 * QBC�d�� (Or �� And �d��) :
	 */
	@Test
	public void QBC_andOr_test() {
		Criteria criteria = session.createCriteria(Employee.class);
		
		/*
		 (1).AND:�ϥ�Conjunction���
		 (1-1).Conjunction�����N�O�@��Criteria����
		 (1-2).�B�䤤�٥i�H�K�[Criterion����
		*/
		Conjunction conjunction = Restrictions.conjunction();
		conjunction.add(Restrictions.ilike("name", "a",MatchMode.ANYWHERE));
		Department dept = new Department();
		dept.setId(80);
		conjunction.add(Restrictions.eq("dept", dept));
		System.out.println(conjunction);
		
		/*
		 (2).Or:�ϥ�Disjunction���
		*/
		Disjunction disjunction = Restrictions.disjunction();
		disjunction.add(Restrictions.ge("salary", 6000f));
		disjunction.add(Restrictions.isNull("email"));
		
		criteria.add(disjunction);
		criteria.add(conjunction);
		
		criteria.list();
		
	}
	
	/**
	 * QBC�d�� (statistics�έp�d�߽d��) max()�̤j�Ȥ�k
	 */
	@Test
	public void statistics_test() {
		Criteria criteria = session.createCriteria(Employee.class);
		
		//�έp�d��:�ϥ�Projection�Ӫ��:�i�H��Projections���R�A��k���C
		criteria.setProjection(Projections.max("salary"));
		
		System.out.println(criteria.uniqueResult());
	}
	
	/**
	 * QBC�d�� (orderBy�d�� �����d��)
	 */
	@Test
	public void orderBy_test() {
		Criteria criteria = session.createCriteria(Employee.class);
		
		//(1).�K�[�Ƨ�
		criteria.addOrder(Order.asc("salary"));
		criteria.addOrder(Order.desc("email"));
		
		//(2).�K�[½����k
		int pageSize = 5;
		int pageNo =3;
		criteria.setFirstResult((pageNo -1) * pageSize) //setFirstResult()�]�m�_�l����
				.setMaxResults(pageSize) //setMaxResults()�C���̤j��ܼƤ�k
				.list();
	}
	
	/**
	 * �����ާ@SQL �d�� (�s�W)
	 */
	@Test
	public void nativeSQL_test() {
		String sql = "INSERT INTO DEPARTMENTS_HQL VALUES(?,?)";
		Query query = session.createSQLQuery(sql);
		
		query.setInteger(0, 280)
			 .setString(1, "ATGUIGU")
			 .executeUpdate();
	}
	
	/**
	 * HQL �d�� (�R�����a�s�W��280)
	 */
	@Test
	public void deleteHQL_test() {
		String hql = "DELETE FROM Department d WHERE d.id = :id";
		
		session.createQuery(hql).setInteger("id", 28)
								.executeUpdate();
	}
}
