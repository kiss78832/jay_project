package com.jay.HQL.cache;

import java.util.Iterator;
import java.util.List;

import org.hibernate.Criteria;
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

public class Cache_Test {

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
	*	�G�Žw�s : 
	*		(1).�w�s���d�����3��: 
	*			1.�ưȽd��]��Session�Y�@�Žw�s�^ 
   	*				�ưȽd�򪺽w�s["�u��Q��e�ưȳX��"],�C�Өưȳ����U�۪��w�s,�w�s�����ƾڳq�`�ĥάۤ����p����H�Φ�.�w�s���ͩR�g���̿��ưȪ��ͩR�g��,�u����ưȵ�����,�w�s���ͩR�g���~�|����.�ưȽd�򪺽w�s�ϥΤ��s�@���s�x����,�@�Žw�s�N�ݩ�ưȽd��. 
	*			2.���νd��]��SessionFactory�Y�G�Žw�s�^ 
   	*				���ε{�Ǫ��w�s�i�H�Q���νd�򤺪��Ҧ��ưȦ@�ɳX��.�w�s���ͩR�g���̿�����Ϊ��ͩR�g��,�u�������ε�����,�w�s���ͩR�g���~�|����.���νd�򪺽w�s�i�H�ϥΤ��s�εw�L�@���s�x����,�G�Žw�s�N�ݩ����νd��. 
	*			3.���s�d��]�hSessionFactory�^ 
   	*				�b���s���Ҥ�,�w�s�Q�@�Ӿ����Φh�Ӿ������i�{�@��,�w�s�����ƾڳQ�ƻs�춰�s���Ҥ����C�Ӷi�{�`�I,�i�{���q�L���{�q�H�ӫO�ҽw�s�����ƾڪ��@�P,�w�s���� �ƾڳq�`�ĥι�H���P���ƾڧΦ�.
   	*		(2).SessionFactory�w�s�S������:
   	*			1.���m�w�s:Hibernate�۱a���A���i�����A�q�`�bHibernate��l�ƶ��q�AHibernate�|��M�g�ƾڸ�w�wSQL��bSessionFactory�w�s�A�ҬM�g�����N�O*.hbm.xml�o���ɡA�Ӥ��m�u�OŪ�Ӥw�C
   	*			2.�~�m�w�s:�@�ӥi�t�m���w�s�M��A�q�{�USessionFactory���|�_�γo�ӮM��A�~�m�w�s�O�i�H�O"���s"�άO"�w��"(���s�ζq�j�ɥi�H�ϥεw�Цs)�C
   	*
   	*		(3).���ǼƾھA�X�b�G�Žw�s��?
   	*			�A�X
   	*				���ܤֳQ�ק諸�ƾڡC 
	*				�����O�ܭ��n���ƾڡA���\�X�{�����õo���ƾڡC 
	*				�����|�Q�õo�X�ݪ��ƾڡC 
	*				���`�q�ƾڡC 
	*				�����|�Q�ĤT��ק諸�ƾ� 
	*				
	*			���A�X��b�G�Žw�s���G 
	*				���g�`�Q�ק諸�ƾڡC 
	*				���]�ȼƾڡA���藍���\�X�{�õo���D�C 
	*				���P��L���Φ@�ɪ��ƾڡC 
   	*
   	*		(4).�@�Žw�s vs �G�Žw�s :
   	*				[�@�Žw�s] : (a).�����t�m(�����S�O�@����)  (b).session�w�s��������A������session�N�|�۰ʾP�� 
   	*				[�G�Žw�s] : (a).����l�ƸӪ��Ҧ��ƾ� -> ��Ҧ��ƾڮھ�"ID"��i�G�Žw�s�� 
   	*							(b).��Hibernate�ھ�ID�X�ݼƾڪ��ɭԡA"������session�w�s(�@��)����"�A�Y�䤣�쪺�ܷ|"��sessionFactory�w�s(�G��)����ƾ�"(�e���O���]�m�G�Žw�s)�A�p�G�A�䤣��N�q��Ʈw��C
   	*							(c).�R���B��s�B�W�[�ƾڪ��ɭԡA�P�ɧ�s�w�s�C
	*/
	@Test
	public void Cache_test() {
		Employee employee = (Employee)session.get(Employee.class, 100);
		System.out.println(employee.getName());
		
		//����transaction �A ����session
		transaction.commit();
		session.close();
		
		//���s�Ұ�session�Btransaction
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Employee employee2 = (Employee)session.get(Employee.class, 100);
		System.out.println(employee2.getName());
	}
/**		(�G�Žw�s[������d��θs���d��]):										(�@�Žw�s[�ưȽd��]):
 * 		 	�ӽw�s�OsessionFactory�ŧO�A�]��sessionFactory�s�ysession�A				�ӽw�s�Osession�ŧO�A���@�˪��ʧ@�ɡA�|�qsession�������A�ä��|�A�h�d�ߤ@���A����O�n�b�P�@��session�~�i�H�A
			�ҥH���Psession�ä��|�v�T�A�G�Žw�s�O�s�b��W�h��sessionFacory�C			�h�Ū�ܹ� sessionFactory -> session -> transaction
		
		Hibernate: 															Hibernate: 
			select														   		 select
				employee0_.ID as ID1_1_0_,											employee0_.ID as ID1_1_0_,
				employee0_.NAME as NAME2_1_0_,										employee0_.NAME as NAME2_1_0_,
				mployee0_.SALARY as SALARY3_1_0_,									employee0_.SALARY as SALARY3_1_0_,
				employee0_.EMAIL as EMAIL4_1_0_,									employee0_.EMAIL as EMAIL4_1_0_,
				employee0_.DEPT_ID as DEPT_ID5_1_0_ 								employee0_.DEPT_ID as DEPT_ID5_1_0_ 
			from														   		 from
				EMPLOYEES_HQL employee0_											 EMPLOYEES_HQL employee0_ 
			where														   		 where
				employee0_.ID=?													     employee0_.ID=?
																			[King]
																			
			[King]															Hibernate: 
			[King]														    	select
																	        		employee0_.ID as ID1_1_0_,
																			        employee0_.NAME as NAME2_1_0_,
																			        employee0_.SALARY as SALARY3_1_0_,
																			        employee0_.EMAIL as EMAIL4_1_0_,
																			        employee0_.DEPT_ID as DEPT_ID5_1_0_ 
																			    from
																			        EMPLOYEES_HQL employee0_ 
																			    where
																			        employee0_.ID=?
																			[King]
*/
	
	/**
	 * ���չﶰ�X�ϥΤG�Žw�s:(�`�N:�ݭn�t�m���X���������������[�����]�ϥΤG�Žw�s�A�_�h�|�h�ܦhSQL[EX:Department����emps���X�����O�OEmployee�A�ҥHEmployee�]�n�]�m�G�Žw�s])
	 * 		��k�@:(��hibernate.cfg.xml�]�m)
	 * 		<collection-cache usage="read-write" collection="com.jay.HQL.entities.Department.emps"/>
	 * 
	 * 		��k�G:(��*.hbm.xml�]�m)
	 *         <set name="emps" table="EMPLOYEES_HQL" inverse="true" lazy="true">
	 *	        	<cache usage="read-write"/>   <--- �w��<set>�̭��]�m�A�o�ˤ~��]�m�춰�X
	 *	            <key>
	 *	                <column name="DEPT_ID" />
	 *	            </key>
	 *	            <one-to-many class="com.jay.HQL.entities.Employee"/>
	 *	        </set>
	 */
	@Test
	public void collectionSecondCache_test() {
		Department dept = (Department) session.get(Department.class,80);
		System.out.println(dept.getName());
		System.out.println(dept.getEmps().size());
		
		//����transaction �A ����session
				transaction.commit();
				session.close();
				
		//���s�Ұ�session�Btransaction
		session = sessionFactory.openSession();
		transaction = session.beginTransaction();
		
		Department dept2 = (Department) session.get(Department.class,80);
		System.out.println(dept2.getName());
		System.out.println(dept2.getEmps().size());
	}
	
	/**
	 * HQL��QBC�d�߽w�s:
	 * 		(1).�bhibernate�t�m����n��"�}�Ҭd�߽w�s"�C<property name="cache.use_query_cache">true</property>
	 * 		(2).�ե�Query��Criteria��setCacheable(true)��k�C
	 * 		(3).�YHQL��QBC�ݭn�ϥΨ�w�s�A�аO�o�t�~�]�mxml�C
	 * 		(4).�`�N:�Y�n�t�m�d�߽w�s�A�������]�mHibernate���G�Žw�s�C
	 */
	@Test
	public void queryCache_test() {
		
		//HQL�d��
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		//�Ĥ@���d��
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		//�ĤG���d�߬ݬO�_�����LSQL
		emps = query.list();
		System.out.println(emps.size());
		
		
		//QBC�d��
		Criteria criteria = session.createCriteria(Employee.class);
		criteria.setCacheable(true);
	}
	
	/**
	 * �ɶ��W�w�s��:
	 * 		(�B�J1).T1�ɨ����"�d�߾ާ@"�A�o�쪺"�d�ߵ��G"�s�bQueryCache�ϰ�A��T1�s�W�@�Ӯɶ��W�O�C
	 *  	(�B�J2).T2�ɨ����"��s�ʧ@"�A�o�쪺"�d�ߵ��G"�s�bUpdateTimestampCache�ϰ�C
	 *  	(�B�J3).T3�ɨ�bT1��T2�᭱:
	 *  		[���Ǳ��p1 T2 �� T3�ɶ�����] ����Ӧs��QueryCache�ϰ�d�ߵ��G�A���s���Ʈw�d�߷s���G�A�A�s�bQueryCache�ϰ�C
	 *  		[���Ǳ��p2 T1 �� T3�ɶ�����] �����sQueryCache�ϰ�����d�ߵ��G�C
	 */
	@Test
	public void updateTimeStampCache_test() {
		
		Query query = session.createQuery("FROM Employee");
		query.setCacheable(true);
		//�Ĥ@���d��(T1)
		List<Employee> emps = query.list();
		System.out.println(emps.size());
		
		//�G�N����ơA�o�˸�w�s�N�|���P�B(T2)
		Employee employee = (Employee)session.get(Employee.class, 100);
		employee.setsalary(300000);
		
		//�ĤG���d�߬���|�۰ʭn�d��? �p�󪾹D��Ƥ��P�B? Hibernate�|�h���ɶ��W
		emps = query.list();
		System.out.println(emps.size());
	}
	
	/**
	 * Iterate()��k�d��
	 * 		(1).�S���S���p�U�A����ĳ�ϥΡA�������h�ӱ���U�A�t�פ~�|����֡C
	 * 		(2).�d�Ҥ�department ID=80 ; Employee.dept.id = 80 ; ��ӬۦPSQL�|��֡A�����P�|�h�X�ܦhSQL�C
	 */
	@Test
	public void queryIterate_test() {
		Department dept = (Department) session.get(Department.class,70);
		//select id=70 �L�X�m�W
		System.out.println(dept.getName());
		//select department.emps��id=70 �`�� ���I!!! �]�N�O����d�F���department.emps.id=70�U����ơA�w�s�|��id=70��������ơC
		System.out.println(dept.getEmps().size());
		
		//select Employee ���U��department.ID = 80 �A�^�ǬO�Ҧ�Employee��ƥu�ndept.id=80�N�^�Ǹ�Employee��ID
		Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
		
		Iterator<Employee> empIt = query.iterate();
		while(empIt.hasNext()) {
			//�]��"dept.getEmps().size()"�O�did=70�A�w�s�O70����ơA�ҥH�u��NQuery�� emp.id �@�Ӥ@�ӥh�d
			System.out.println(empIt.next().getName());
		}
	}
	
	@Test
	public void queryIterate02_test() {
		Department dept = (Department) session.get(Department.class,80);
		//select id=80 �L�X�m�W
		System.out.println(dept.getName());
		//select department.emps��id=80 �`�� �C ���I!!! �]�N�O����d�F���department.emps�U����ơA�w�s�|��������ơC
		System.out.println(dept.getEmps().size());
		
		//select Employee ���U��department.ID = 80 �A�^�ǬO�Ҧ�Employee��ƥu�ndept.id=80�N�^�Ǹ�Employee��ID
		Query query = session.createQuery("FROM Employee e WHERE e.dept.id = 80");
		
		//���լO�_����department��emps���w�s�A����:�S���C(��̨ä��ۦP�A��l�ƹL�{�]���P�A�@�ӬOEmployee���A�@�ӬODepartment����Employee���X)
		//List<Employee> emps = query.list();
		//System.out.println(emps.size());
		
		Iterator<Employee> empIt = query.iterate();
		while(empIt.hasNext()) {
			//�]��"dept.getEmps().size()"�O�did=80�A�w�s�O80����ơA��n������A�����h�w�s�����ݭn�A�d�C
			System.out.println(empIt.next().getName());
		}
	}
}
