package com.jay.hibernate.oneToOne_FK;


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
public class OneToOne_Test {
	
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
		Department department = new Department();
		department.setDeptName("DEPT-AA");
		
		//�u�ݭn�]�mName�BId��Ʈw����
		Manager manager = new Manager();
		manager.setMgrName("MGR-AA");
		
		//�]�m���p���Y
		department.setMgr(manager);
		manager.setDept(department);
		
		//�O�s�ާ@
		//department(PK:dept_id UK:mgr_id);manager(PK:mgr_id)
		//��ĳ���O�s�S���~���䪺���A�o�˷|���update�y�y�Adepartment�� manager�� (���ͤ@��update)�Amanager�� �Adepartment��(������update)
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
		
		// 1.�q�{���p�U�����p�ݩʨϥ��i�[��(�i�[��:�ϥΨ�~�|�h�d�߸����C)
		// 2.�ҥH�|�X�{�i�[�����`���D�A���i�[���\��->�N�|���N�z��H->���i�[���N�|���i�[�����`->���i�[�����`������session�ɡA�S�hCRUD�����A�۵M�|�X�{���`�A�]�������O�N�z��H�B�z���C
		Department dept = (Department) session.get(Department.class, 2);
		System.out.println(dept.getDeptName());
		
		session.close();
		
		Manager mgr = dept.getMgr();
		System.out.println("��class���|�X�{���`�A�h��mgr�ݩʤ~�|�X�{�i�[�����`: "+mgr.getClass());
	}
	
	@Test
	public void oneToOne_Get02() {
		//����property-ref�ݩʪ��ĪG
		
		//(dept �Ĥ@��select SQL)
		Department dept = (Department) session.get(Department.class, 2);
		System.out.println(dept.getDeptName());
		
		//(mgr �ĤG��select SQL ���oDepartment�ҳ]�m��manager)  
		//left outer join on manager.MGR_ID=department.DEPT_ID �d�ߵ��G����A�o�䪺mgr�O�qdepartment��������A���T���ӬOdepartment.MGR_ID(�����hManager.xml�]�mproperty-ref�ݩ�)
		Manager mgr = dept.getMgr();
		//�n�h�ާ@mgr���A��M�O��dept��mgr_id�h�dmgr��mgr_id�A�ۦP�~�ા�D�O������ơC
		System.out.println(mgr.getMgrName());
	}
	
	@Test
	public void oneToOne_Get03() {
		//����mgr�S��dept_id�����M�g��department���
		
		//�b�d�ߨS���~���䪺�����H�ɡA�ϥΪ����~(left outer)�d�ߡA�@�֬d�ߥX�������H�A�äw�g�i���l�ơC(Hibernate�����|�ޱ�)
		Manager mgr = (Manager) session.get(Manager.class, 1);
		System.out.println(mgr.getMgrName());
		System.out.println(mgr.getDept().getDeptName());
	}
	
}
