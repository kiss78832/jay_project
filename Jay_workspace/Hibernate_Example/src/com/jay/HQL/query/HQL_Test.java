package com.jay.HQL.query;


import java.util.ArrayList;
import java.util.Arrays;
import java.util.LinkedHashSet;
import java.util.List;

import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;

import com.jay.HQL.entities.Department;
import com.jay.HQL.entities.Employee;

public class HQL_Test {
	
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
	 * HQL�d�� (�����ު��Ѽ�)
	 */
	@Test
	public void HQL_test() {
		
		//(1).�Ы�Query��H (�`�NFROM XXX�A�榡��ݲĤ@�Ӥj�g�A�B���O��J��Ʈw���W�١A�ӬO���骺����Employee.java)
		String hql = "FROM Employee e WHERE e.salary > ? AND e.email LIKE ? AND e.dept = ? ORDER BY e.salary";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		
		//(2).�j�w�Ѽ�
		query.setFloat(0, 6000)
			 .setString(1,"%S%")
			 .setEntity(2, dept); //�i������@�ӹ��骫��
		
		//(3).����d�� list()�^�Ǥ@�Ӷ��X
		List<Employee> emps = query.list();
		System.out.println(emps.size());
	}
	
	/**
	 * HQL�d��(�ϥΦW�ٷ�Ѽƭ�)
	 */
	@Test
	public void HQLNameparameter_test() {
		//(1).�Ы�Query��H (�`�NFROM XXX�A�榡��ݲĤ@�Ӥj�g�A�B���O��J��Ʈw���W�١A�ӬO���骺����Employee.java)
		String hql = "FROM Employee e WHERE e.salary > :sal AND e.email LIKE :email";
		Query query = session.createQuery(hql);
		
		//(2).�j�w�Ѽ�()
		query.setFloat("sal", 6000)
			 .setString("email","%%");
		
		//(3).����d��
		List<Employee> emps = query.list();
		System.out.println("�@�h�ָ��: "+emps.size()+" ��");
	}
	
	/**
	 * HQL�d��(�ϥΦW�ٷ�Ѽƭ�)
	 */
	@Test
	public void pageQuery_test() {
		//(1).�Ы�Query��H (�`�NFROM XXX�A�榡��ݲĤ@�Ӥj�g�A�B���O��J��Ʈw���W�١A�ӬO���骺����Employee.java)
		String hql = "FROM Employee";
		Query query = session.createQuery(hql);
		
		//(2).�t�m����
		int pageNo = 3; //����������
		int pageSize = 5; //�C���X��
		
		//(3).����d��
		//		a.setFirstResult()�G�]�m�q���Ӧa��}�l�d�ߡA�q�{�O0�C
		//      b.setMaxResults()�G�]�m�@���̦h�d�ߪ��ƥءC

		List<Employee> emps = query.setFirstResult((pageNo - 1) * pageSize) //(3-1) * 5 = 10�A�q��10�����U�@�Ӷ}�l�]�N�O��11�ӡC
	  							   .setMaxResults(pageSize)
								   .list();
		
		System.out.println(emps);
	}
	
	/**
	 * XML <Query>�d��(�ϥΦW�ٷ�Ѽƭ�) :�Шϥ�getNamedQuery("XXX")
	 */
	@Test
	public void xmlQuery_test() {
		Query query = session.getNamedQuery("salaryEmps");
		
		List<Employee> emps = query.setFloat("minSal", 5000)
								   .setFloat("maxSal", 10000)
								   .list(); //list()�^�Ǥ@�Ӷ��X
		System.out.println(emps.size());
	}
	
	/**
	 * �@�B(�@��SELECT�y�k��k)
	 * 		hql ���[�WSELECT :���]��"SELECT A��,B�� FROM C���O"�A��^���O�@��Object�}�C
	 *     		�S�[�WSELECT :��¥u�O"FROM XXX"�A��^���O�@��Object�A�i�H�ۥ��ഫ�������O�C
	 */
	@Test
	public void fieldQuery_test() {
		String hql = "SELECT e.email, e.salary ,e.dept FROM Employee e WHERE e.dept = :dept";
		Query query = session.createQuery(hql);
		
		Department dept = new Department();
		dept.setId(80);
		List<Object[]>result = query.setEntity("dept",dept)
									.list();
		
		//�]���O�}�C�ҥH�n�ѪR�⦸�A�ҥH��Arrays.asList()
		for(Object[] objs : result) {
			System.out.println(Arrays.asList(objs));
		}
	}
		
		/**
		 * �G�B(�����ASELECT�y�k�Ыت���A�i�H��ָѪR)
		 * 		"SELECT new Employee( e.email, e.salary ,e.dept )" �o�˪�^�N�O�@��Employee����
		 * 
		 * �`�N:�p�G�n�Φ��覡�A�O�o�n�����O�n���۹������غc�l�Anew Employee(A,B,C)�C�L�Ѽƪ��]���n�ѰO�C
		 */
		@Test
		public void fieldQuery02_test() {
			String hql = "SELECT new Employee( e.email, e.salary ,e.dept )"
					+ "FROM Employee e "
					+ "WHERE e.dept = :dept";
			
			Query query = session.createQuery(hql);
			
			Department dept = new Department();
			dept.setId(80);
			List<Employee>result = query.setEntity("dept",dept)
										.list();
			
			for(Employee emp : result) {
				System.out.println(emp.getId()+","+emp.getEmail()+","+emp.getsalary()+","+emp.getDept());
			}
		
	}
	
		/**
		 * groupBy()�d��
		 * 		�D��:(1)��X�̤j�~���̤p�~��h��? (2)�ڷQ���D�C�ӳ����̧C�~�� > 8000 �������X�ӳ���?
		 * 		70�B90�B110 ���F��
		 */
		@Test
		public void groupBy_test() {
			String hql = "SELECT min(e.salary),max(e.salary)"
					+ "FROM Employee e GROUP BY e.dept HAVING min(salary) > :minSal";
			
			Query query = session.createQuery(hql)
								 .setFloat("minSal", 8000);
			
			List<Object []>result = query.list();
			for(Object[] objs : result) {
				System.out.println(Arrays.asList(objs));
			}
		}
		
		/**
		 * Left Join Fetch �����s���d��(�������~�s�u: �S�I�O�G�p�G�������������󪺡A�]��^������������)�U�����Ҥl�O �q 1 �� �h
		 * 		(1).list()��^�Ӫ����X���s����骫�󪺤ޥΡA�C��Department��������p��Eployee���X���Q��l�ơA�s��Ҧ����p��Employee�����骫��C(��^FROM�᭱�������O)
		 * 		(2).��Left JOIN FETCH -> ������l��Employee�A��Employee����Ƥ@���˶iDepartment��emps���X���A�ҥH�u�n��^Department����N�n�A�ҥH�~���ݬO�}�C����]�C
		 * 		(3).distinct -> SQL �����ƪ����O
		 * 		(4).�d�ߵ��G���|���ܦh���Ƹ�ơA�i�H�Q�Ψ�ؤ�k�ư� (A)�bSQL���SELECT DISTINCT�覡 (B)�Ⱶ�^�Ӫ��ѼƩ�iHashSet���X�̭��C
		 * 		(5).�`�N:�����s���O��^"����"�Ӥ��O"����}�C"�A���s��(�S��fetch)�N�O��^"����}�C"�C
		 * 		(6).�]���h�Ffetch����r�A�|�Nemp���ƾڧ�X�ӡA��R��Dept���X����emps���C
		 * 		()
		 */
		@Test
		public void leftJoinFetch_test() {
//			String hql = "FROM Department d INNER JOIN FETCH d.emps";
			//��kA
			String hql = "SELECT DISTINCT d FROM Department d Left JOIN FETCH d.emps";
			Query query = session.createQuery(hql);
			
			List<Department> depts = query.list();
			//��kB
//			depts = new ArrayList<>(new LinkedHashSet(depts));
			System.out.println(depts.size());
			
			for(Department dept : depts) {
				System.out.println(dept.getName()+" - "+dept.getEmps().size()+" , "+dept.getId());
			}
		}
		
		/**
		 * Left Join ���~�s���d��
		 * 		(1).list()��^���O����}�C�C(�C�Ӹ�Ƴ����P�A�P�����@�˦����u���P�C)
		 * 		(2).�ھڰt�m���ӨM�wEmployee���X�˯������C(���������s���@�ˡA������ӳ���l��)
		 * 		(3).�p�G�Ʊ�list()��^�����X���]�tDepartment����A�i�H�AHQL�d�߻y�y���ϥ�SELECT�C
		 * 		(4).Left JOIN -> 
		 * 		(5).Outer Join �O inclusive, �s�����]�e�ʡA�]���b Left Outer Join ���d�ߵ��G�|�]�t�Ҧ� Left ��ƪ����
		 */
		@Test
		public void leftJoin_test() {
			/**
			 * ���ϥ�SELECT DISTINCT (��[���u��department.ID�Bdepartment.NAME]�A�ϥ�distinct���O�N��z�˭��Ƹ�ơA���p�G�h�@��emps����L��줧���S�|�X�{���Ƹ�ơC�]��emps����L���ä��O���ƪ����)
			 */
//			String hql = "SELECT DISTINCT d FROM Department d LEFT JOIN d.emps";
//			Query query = session.createQuery(hql);
//			
//			List<Department> depts = query.list();
//			System.out.println(depts.size());
//			
//			for(Department dept : depts) {
//				//�o��o�{Department��emps���X�èS���Q��l�ơA�ҥH�C���p��emps��size���n����@��SQL
//				System.out.println(dept.getName()+" , "+dept.getEmps().size());			
//			}
			
			/**
			 * ����SELECT DISTINCT
			 */
			String hql = "FROM Department d LEFT JOIN d.emps";
			Query query = session.createQuery(hql);
			
			//���X�s�񪺬OObject�A��Object���ˤJ���L�D�ODepartment�MEmployee��H�C
			List<Object[]> result = query.list();
			
			//�L�k��׭��ơA�]��Object��Department�MEmployee����A�ҥH������¬dDepartment��������N�n�A�A�Q��DISTINCT�y�k�h�����ơC
			result = new ArrayList<>(new LinkedHashSet<>(result));
			System.out.println(result);
			
			for(Object[] objs : result) {
				System.out.println(Arrays.asList(objs));
			}
		}
}
