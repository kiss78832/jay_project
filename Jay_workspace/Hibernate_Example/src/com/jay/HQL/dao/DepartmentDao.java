package com.jay.HQL.dao;

import org.hibernate.Session;

import com.jay.HQL.entities.Department;

public class DepartmentDao {
	
	/**
	 *	若需要傳入一個session物件，意味著上一層(Service)需要獲得Session物件，
	 *	這說明上一層需要和Hibernate 的 API 緊密耦合。[所以不推薦此種方式]
	 */
	public void save(Session session,Department dept) {
		session.save(dept);
	}
	
	/**
	 *	
	 */
	public void save(Department dept) {
		//內部獲取Session物件
		//獲取合當前執行緒綁定的Session物件
		//1.不需要從外部傳入Session物件
		//2.多個DAO方法也可以使用"一個事務"
		
		Session session = HibernateUtils.getInstance().getSession();
		System.out.println(session.hashCode());
//		session.save(dept);
	}
}
