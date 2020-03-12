package Steam_Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

import Lambda_Example.Employee;
/*
 *  ���d�Ҭ������ާ@
 */
public class Stream01 {
	/*
	 *  �@�BStream���T�Ӿާ@�B�J: 
	 * 		(1).�Ы�Stream (2).�����ާ@ (3).�פ�ާ@
	 * 
	 *  �G�B�z��P���� (Test01~Test04)
	 *  	filter -> ����Lambda�A�qstream���L�o�Y�Ǥ����C
	 *  	limit -> �I�_�y�A���w�Q�ާ@���������W�L�]�w���ƶq�C
	 *  	skip(n) -> ���L�����A�i�H���Ln�Ӥ�����A����A�Y��������n�ӡA�|��^�@�Ӫ�stream�A�Plimit�i�H���۷f�t�C
	 *  	distinct -> �z��A�q�Lstream�Ҳ��ͪ����� hashCode()�M equals()�h�����Ƥ����C
	 *  
	 *  �T�B�M�g (Test05)
	 *  	map -> ����Lambda�A�N�����ഫ����L�Φ��A�����@�Ө�Ƨ@���ѼơA�Ө�Ʒ|�Q���Ψ�C�Ӥ����A�ñN��M�g���@�ӷs�������C
	 *  	flatMap -> �����@�Ө�Ƨ@���ѼơA�N��stream �����C�@�ӳ������t�@��stream�A�M���Ҧ�stream �s�����@��stream�C
	 *  
	 *  �|�B�Ƨ� (Test06~Test07)
	 *  	sorted() -> �۵M�Ƨ�(Comparable)�A����compareTo(T o)�ƧǡC
	 *  		�Ҧp:String ���O �ypublic final class String implements Comparable<String>, CharSequence�z
	 *  	sorted(Scomparator com) -> �q�s�Ƨ�(Comparator)�A�̷�compare(T o1, T o2)�ƧǡC
	 *  	
	 *  �`�N:
	 *  	1.���������N�@�ΡA�����浹Stream API�����C�~�����N:�ۤv�g�@��Iterator...
	 */
	List<Employee> list = Arrays.asList(
			new Employee("����1",60,1000),
			new Employee("����2",50,2000),
			new Employee("����3",40,3000),
			new Employee("����4",30,4000),
			new Employee("����5",20,5000),
			new Employee("����6",19,6000),
			new Employee("����7",18,7000),
			new Employee("����8",17,8000),
			new Employee("����8",17,8000)
			);
	
	@Test
	public void test01() {
		/*
		 * filter() �d��
		 */
		//�����ާ@
		Stream<Employee> stream = list.stream()
								  .filter((e) -> {
									System.out.println("Stream API test �����ާ@");
									return e.getAge() > 35;
								  });
		
		//�פ�ާ@   �`�N:�Y�S���פ�ާ@�A�N���stream�����|����A"Stream API test �����ާ@"�����|����C
		stream.forEach(System.out::println);
	}
	
	@Test
	public void test02() {
		/*
		 * limit() �d��
		 * ��X��������N���|�b���U����A�i�H�h�[��L�X"�u��!"���p�A(1or2)���|���_���N�A(3)�䤣��ŦX�ĤT�ӱ���N���N�쵲���C �P&& || �u���B�ⷧ�������A�@���F�N���|�A�h�P�_�C
		 */
		
		list.stream()
			.filter((e)->{
				System.out.println("�u��!");
				//�Y�u��(e)->e.getSalary()>3000 �i�H�ٲ�return�A�]��Stream<T> filter(Predicate<? super T> predicate);
				//����Predicate����kboolean test(T t)�A�ݭn�^�Ǥ@��boolean�ȡC
				return e.getSalary()<3000;
			})
			.limit(3) //��X��������N���|�b���U����A�i�H�h�[��L�X"�u��!"���p�A(1or2)���|���_���N�A(3)�䤣��ŦX�ĤT�ӱ���N���N�쵲���C �P&& || �u���B�ⷧ�������A�@���F�N���|�A�h�P�_�C
			.forEach(System.out::println);
	}
	
	@Test
	public void test03() {
		/*
		 * skip(n) �d��
		 */
		list.stream()
		.filter((e)-> e.getSalary()<3000)
		.skip(1)
		.forEach(System.out::println);
		
	}
	
	@Test
	public void test04() {
		/*
		 * distinct() �d��
		 * hashCode()�M equals()�h�����Ƥ����C�i�H��Employee�h��equals�BhashCode���ѡA�N��L�o���ƪ��C�d��:����8
		 */
		list.stream()
		.filter((e)-> e.getSalary()>100)
		.distinct()
		.forEach(System.out::println);
	}
	
	@Test
	public void test05() {
		/*
		 * map(Function) �d��  
		 * �y�{: �ϥέ��N�覡�A��"aaa"����toUpperCase() -> ��"bbb"����toUpperCase() -> ��"ccc"����toUpperCase() ...�̦������A�̫�A��forEach�L�X�C
		 */
		List<String> listTest = Arrays.asList("aaa","bbb","ccc","ddd","eee");
		listTest.stream()
				.map((list_test)->list_test.toUpperCase())
				.forEach(System.out::println);
		
		System.out.println("------------------------------------------------------------------------------");
		
		//Employee �d��
		list.stream()
			.map(Employee::getName)
			.forEach(System.out::println);
		
		System.out.println("------------------------------------------------------------------------------");
		
		/*
		 *  map(Function) �d�Ҿާ@: ��r����ѫ�^��stream�C
		 *  mapSouceCode -> �y<R> Stream<R> map(Function<? super T, ? extends R> mapper)�z
		 *  
		 *  �y�{: ����["aaa","bbb","ccc","ddd","eee"] ���X��filterCharacter���ѥX{{a,a,a},{b,b,b,},{c,c,c},{d,d,d}}�A�b�פ�ާ@�AforEach�@���C
		 *  map�O����@��궰�X�C
		 */
		
		//map������^�@��Stream�A<>���OfilterCharacter��^��Stream<Character>�A�]����Stream<Stream<Character>>
		Stream<Stream<Character>> stream = listTest.stream()
				.map(Stream01::filterCharacter);//��^ {{a,a,a},{b,b,b,},{c,c,c},{d,d,d}}  
		
		//stream.forEach�������N�X�Ӫ��٬Ostream�y(filterCharacter��k��stream)�A�ҥH�٭n�A�̭����N�@���C
		stream.forEach((sm) -> {
			sm.forEach(System.out::println);
		});
		
		System.out.println("------------------------------------------------------------------------------");
		 
		/*
		 *  flatMap�d��: ��r����Ѧs�J���X�^�ǡC(�i��)
		 *  flatMap�O���@�Ӥ@�Ӥ��������X�C
		 *  
		 *  �y�{: ����{[a,a,a,b,b,b,c,c,c,d,d,d]} -> 
		 */
		Stream<Character> stream2 = listTest.stream()
				.flatMap(Stream01::filterCharacter);//��^ {a,a,a,b,b,b,,c,c,c,d,d,d} 
		stream2.forEach(System.out::println);
		
	}
	//��^��r���ѳ�@�r��
	public static Stream<Character> filterCharacter(String str){
		List<Character> list = new ArrayList<>();
		
		for (Character character : str.toCharArray()) {
			list.add(character);
		}
		return list.stream(); //�i�H����.streamr()�N���^�@�Ӭy
	}
	
	@Test
	public void test06() {
		/*
		 * sorted() �d��
		 */
		List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");
		
		list.stream()
			.sorted()
			.forEach(System.out::println);
	}
	
	@Test
	public void test07() {
		/*
		 * sorted(Scomparator com) �d��
		 * �ۤv�g����P�_���A�̷Ӧ~�ֱƧǡC
		 */
		list.stream()
			.sorted((e1,e2)->{
				if(e1.getAge().equals(e2.getAge())) {
					return e1.getName().compareTo(e2.getName());
				}else {
					return e1.getAge().compareTo(e2.getAge());
				}
			})
			.forEach(System.out::println);
	}
}
