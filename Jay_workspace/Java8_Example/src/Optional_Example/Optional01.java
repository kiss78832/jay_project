package Optional_Example;

import java.util.Optional;

import org.junit.Test;

import Lambda_Example.Employee;
import Lambda_Example.Employee.Status;

/*
 *	��¦ Optional API
 */
public class Optional01 {
	/*
	 * Optional.of(T t): �Ыؤ@��Optional ����C										Test01()
	 * Optional.empty(): �Ыؤ@�ӪŪ�Optional ����C									Test02()
	 * Optional.ofNullable(T t): �Y t ����null�A�Ы�Optional ����A�_�h�ЫتŹ���C			Test03()
	 * isPresnt(): �P�_�O�_�]�t�ȡC													Test04()
	 * orElse(T t): �p�G�եι�H�]�t�ȡA��^�ӭȡA�_�h��^t�C								Test05()
	 * orElseGet(Supplier s): �p�G�եι�H�]�t�ȡA��^�ӭȡA�_�h��^s�Ψ����ȡC					Test06()
	 * map(Function f): �p�G�ȹ��B�z�A�ê�^�B�z�᪺Optional�A�_�h��^Optional.empty()�C	Test07()
	 * flatMap(Function mapper): �Pmap�����A�n�D��^�ȥ����OOptional�C					Test08()
	 * 
	 */
	
	@Test
	public void test01() {
		/*
		 * Optional.of(T t) �d�� 
		 * 		(a).�Ыؤ@��Optional ����
		 * 		(b).Optional.of()�n�B�N�O�i�H���A��֪��DNullPointerException �b����X�{���`�A���X�{�N�O�@�w�bOpional.of(A����)���Y�C
		 */
		Optional<Employee> op = Optional.of(new Employee());
		
		Employee emp = op.get();
		System.out.println(emp);
		
		//	�G�N����
		Optional<Employee> op2 = Optional.of(null);
		System.out.println(op2);
	}
	
	@Test
	public void test02() {
		/*
		 * Optional.empty() �d��  
		 * 		(a).�u���X�{�bNullPointerException���O(2)���a��A�]��������ȡA��(1)�O�S�����C
		 * 		(b).�Ыؤ@�ӪŪ�Optional ����C
		 */		
		
		//(1)
		Optional<Employee> op = Optional.empty();
		//(2)
		System.out.println(op.get());
	}	
	
	@Test
	public void test03() {
		/*
		 *  Optional.ofNullable(T t) �d��
		 *  	(a).�Y t ����null�A�Ы�Optional ����A�_�h�ЫتŹ���C
		 * 		(b).���~�T�������i�D�A�S��value�A�yjava.util.NoSuchElementException: No value present�z
		 * 		(c).ofNullable �N�Oempty()��of()���X�A�i��SouceCode�A�Ь� d �C
		 * 		(d).return value == null ? empty() : of(value); �p�G����ŴNempty()�A�S���Nof(value)�Ыع���C
		 */
		Optional<Employee> op = Optional.ofNullable(null);
		System.out.println(op.get());
	}
	
	@Test
	public void test04() {
		/*
		 * isPresnt() �d��
		 * 		(a).�P�_�O�_�]�t�ȡC
		 */
		Optional<Employee> op = Optional.ofNullable(new Employee());
		//	 �P�_�O�_���ȡA���ȴN�L�X�ӡC
		if(op.isPresent()) {
			System.out.println(op.get());
		}
	}
	
	@Test
	public void test05() {
		/*
		 * orElse(T t) �d��
		 * 		(a).�p�G�եΪ���]�t�ȡA��^�ӭȡA�_�h��^t������C
		 * 		(b).�n��
		 */
		Optional<Employee> op = Optional.ofNullable(null);
		
		Employee emp = op.orElse(new Employee("�N��op����O�ŭȡA�ڬO�w�]��",99,99,Status.BUSY));
		System.out.println(emp);
	}
	
	@Test
	public void test06() {
		/*
		 * orElseGet(Supplier s) �d��
		 * 		(a).�p�G�եι�H�]�t�ȡA��^�ӭȡA�_�h��^s�Ψ�����
		 * 		(b).�۸�orElse()�t�O�b�i�H����h�Ʊ��bSupplier���A�i�H�A�h���P�_�����ާ@�C
		 */
		Optional<Employee> op = Optional.ofNullable(null);
		Employee emp = op.orElseGet(()-> new Employee("���h�Q��supplier�X�R�Ьdgoogle",99,99,Status.BUSY));	
		System.out.println(emp);
	}
	
	@Test
	public void test07() {
		/*
		 * map(Function f) �d��
		 * 		(a).�p�G�ȹ��B�z�A�ê�^�B�z�᪺Optional�A�_�h��^Optional.empty()�C
		 * 		(b).�Y�ե�map()���󦳭Ȫ��ܡA�N�ϥ�map(Function)��getName()�A�S���h��^�Ū�Optional����C
		 */
		Optional<Employee> op = Optional.ofNullable(new Employee("�ڦ��ȳQ�եν����ڹ�@Function",99,99,Status.BUSY));
		Optional<String> name = op.map((e)->e.getName());	
		System.out.println(name.get());
	}
	
	@Test
	public void test08() {
		/*
		 * flatMap(Function mapper) �d��
		 * 		(a). �Pmap�����A�n�D��^�ȥ����OOptional�C
		 * 		(b). �Pmap�۸��U�AflatMap������Function�]��Optional�]�ˡA����Function�]���šC
		 */
		Optional<Employee> op = Optional.ofNullable(new Employee("�ڦ��ȳQ�եν����ڹ�@Function�å�Optional�]�˧�",99,99,Status.BUSY));
		Optional<String> name = op.flatMap((e)->Optional.of(e.getName()));	
		System.out.println(name.get());
	}
}
