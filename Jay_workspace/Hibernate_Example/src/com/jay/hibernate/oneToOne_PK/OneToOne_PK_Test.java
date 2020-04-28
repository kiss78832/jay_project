package com.jay.hibernate.oneToOne_PK;


import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;


/**
 * �@��@���p���Y(A->B ; B->A):
 * 	(1).�~����i�H�s����@��(manger or department)�A�b�ݭn�s�񪺤@�ݡA���[<many-to-one>�����W�[unique="true"
 * 	(2).�t�@�ݻݭn�ϥ�<one-to-one>�����A�Ӥ����ϥ�property-ref�ݩʡC
 */
public class OneToOne_PK_Test {
	
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
	 *	�s�W 
	 * */
	@Test
	public void oneToOne_Save() {
		//�u�ݭn�]�mName�BId��Ʈw����
		Department02 department = new Department02();
		department.setDeptName("DEPT-CC");
		
		//�u�ݭn�]�mName�BId��Ʈw����
		Manager02 manager = new Manager02();
		manager.setMgrName("MGR-CC");
		
		//�]�m���p���Y
		manager.setDept(department);
		department.setMgr(manager);
		
		//�O�s�ާ@
		//�]���O���s��D��A�ӥD��ߤ@���o���šA�ҥH�Y�ϥ�save(department)�L�]�|�u���h����save(manager)�A�h����save(department)�C
		//���ᶶ�Ǥ��v�T�C(�Y�O���s��FK�q�`department��save()�|��SQL�h���ͤ@��update�A�]�������pmgr_id�A�N���]�m�ŦA�hupdate)
		session.save(manager);
		session.save(department);
	}
	
	/**
	 *	�d��
	 *	�`�N:�����i�[�����`�A�ݭn���hdestroy()����commit()�Bclose()
	 * */
	@Test
	public void oneToOne_Get() {
		//�����i�[��
		
		Department02 dept = (Department02) session.get(Department02.class, 7);
		System.out.println(dept.getDeptName());
		
		//�]���A�Amanager02���ݤ���FK�A�ҥH�u��hjoin Department02�����A�A��mgr_id�h���dept_id(��FK���P)�o��O���D��s���A��FK�ݭn�h�t�mproperty-ref�ݩ�(�ܦ�mgr.mgr_id���dept.mgr_id�A�o��PK���ϥ�)�C
		Manager02 mgr = dept.getMgr();
		System.out.println("manager�d�߷|�h�@�Dleft outer join���O: "+mgr.getMgrName());
	}
	
	@Test
	public void oneToOne() {

	}
	
}
