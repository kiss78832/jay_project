package com.jay.HQL.dao;

import org.hibernate.Session;

import com.jay.HQL.entities.Department;

public class DepartmentDao {
	
	/**
	 *	�Y�ݭn�ǤJ�@��session����A�N���ۤW�@�h(Service)�ݭn��oSession����A
	 *	�o�����W�@�h�ݭn�MHibernate �� API ��K���X�C[�ҥH�����˦��ؤ覡]
	 */
	public void save(Session session,Department dept) {
		session.save(dept);
	}
	
	/**
	 *	
	 */
	public void save(Department dept) {
		//�������Session����
		//����X��e������j�w��Session����
		//1.���ݭn�q�~���ǤJSession����
		//2.�h��DAO��k�]�i�H�ϥ�"�@�Өư�"
		
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println(session.hashCode());
//		session.save(dept);
	}
}
