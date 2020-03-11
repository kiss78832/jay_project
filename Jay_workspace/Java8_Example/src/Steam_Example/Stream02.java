package Steam_Example;

import java.util.Arrays;
import java.util.HashSet;
import java.util.IntSummaryStatistics;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.Set;
import java.util.stream.Collectors;

import org.junit.Test;

import Lambda_Example.Employee;
import Lambda_Example.Employee.Status;

/*
 *  ���d�Ҭ��פ�ާ@
 */
public class Stream02 {
	/*
	 * �d��P�ǰt:
	 * 	allMatch -> �ˬd�O�_"�ǰt�Ҧ�����"�C 		Test01()
	 * 	anyMatch -> �ˬd�O�_"�ܤ֤ǰt�@�Ӥ���"�C		Test02()
	 * 	noneMatch -> �ˬd�O�_"�S���ǰt�Ҧ�����"�C	Test03()
	 * 	findFirst -> ��^�Ĥ@�Ӥ����C				Test04()
	 * 	findAny -> ��^��estream�y�������@�����C	Test05()
	 * 	max -> ��^stream�y���̤j�ȡC				Test06()
	 * 	min -> ��^stream�y���̤p�ȡC 			Test07()
	 *  count -> ��^stream�y���������`�ӼơC 		Test08()
	 * 
	 * �k��:
	 *  reduce -> �i�H�Nstream�y���������е��X�_�ӡA�o��@�ӭȡC 	Test09()
	 * 
	 * ����:
	 *  collect -> �Nstream�y�ର��L�Φ��C�����@��collector��������@�A�Ω�stream���������J�`����k�C   Test10()
	 *  
	 * Collectors��k: 
	 * 	 Collectors.averagingInt ���� 	Test11()
	 *	 Collectors.counting �`��            	Test11()
	 *	 Collectors.summingInt �`�M       	Test11()
	 *	 Collectors.maxBy �̤j��                 	Test11()
	 * 	 Collectors.minBy �̤p��                 	Test11()
	 * 
	 * 	 Collectors.groupingBy ���� or �h�Ť���									Test12()�BTest13()
	 * 	 Collectors.partitioningBy ���� (true & false �G���k)					Test14()
     *   Collectors.summarizingInt �[�`�B�����B�̤j�̤p�Ȥu�� (Int�BDouble�BLong)		Test15()
     *   Collectors.joining ���X											 	Test16()
	 */
	
	List<Employee> employee = Arrays.asList(
			new Employee("����1",60,1000,Status.FREE),
			new Employee("����2",50,2000,Status.VOCATION),
			new Employee("����3",40,3000,Status.VOCATION),
			new Employee("����4",30,4000,Status.FREE),
			new Employee("����5",20,5000,Status.BUSY),
			new Employee("����6",19,6000,Status.BUSY),
			new Employee("����7",18,7000,Status.FREE),
			new Employee("����8",17,8000,Status.BUSY),
			new Employee("����8",17,8000,Status.VOCATION)
			);
	@Test
	public void Test01() {
		/*
		 * boolean allMatch(Predicate<? super T> predicate) �d��
		 */
		boolean b1 = employee.stream()
					.allMatch((e)->e.getSalary().equals(Status.BUSY));
		System.out.println(b1);
	}
	
	@Test
	public void Test02() {
		/*
		 * boolean anyMatch(Predicate<? super T> predicate) �d��
		 */
		boolean b1 = employee.stream()
					.anyMatch((e)->e.getSalary().equals(Status.BUSY));
		System.out.println(b1);
	}
	
	@Test
	public void Test03() {
		/*
		 *  boolean noneMatch(Predicate<? super T> predicate) �d��
		 */
		boolean b1 = employee.stream()
					.noneMatch((e)->e.getSalary().equals(Status.BUSY));
		System.out.println(b1);
	}
	
	@Test
	public void Test04() {
		/*
		 *  Optional<T> findFirst() �d��
		 *  Optional�i�H���C��null�����v�A�p�G���Ŧ��N��API��k�C
		 */
		Optional<Employee> op = employee.stream()
							.sorted((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary()))
							.findFirst();
		System.out.println(op);
	}
	
	@Test
	public void Test05() {
		/*
		 *  Optional<T> findAny() �d��
		 *  ��^���A�OFREE�����u
		 */
		
		//stream():�������h��A�@�w��Ĥ@�ӡC
		Optional<Employee> op = employee.stream()
							.filter((e)->e.getStatus().equals(Status.FREE))
							.findAny();
		System.out.println(op);
		
		System.out.println("-------------------------------------------------");
		
		//parallelStream():�h������h��A�ݭ��Ӱ���������A���ƾڦh�~�ݪ��X�ӡC
		Optional<Employee> op1 = employee.parallelStream()
				.filter((e)->e.getStatus().equals(Status.FREE))
				.findAny();
		System.out.println(op1);
	}
	
	@Test
	public void Test06() {
		/*
		 *  Optional<T> max(Comparator<? super T> comparator) �d��
		 */
		Optional<Employee> op = employee.stream()
							.max((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary()));
		System.out.println(op.get());
	}
	
	@Test
	public void Test07() {
		/*
		 *  Optional<T> min(Comparator<? super T> comparator) �d��
		 */
		Optional<Employee> op = employee.stream()
							.min((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary()));
		System.out.println(op.get());
	}
	
	@Test
	public void Test08() {
		/*
		 *  long count() �d��
		 */
		Long count = employee.stream()
							 .count();
		System.out.println(count);
	}
	
	@Test
	public void Test09() {
		/* 
		 *  BinaryOperator<T>: BiFunction<T,T,T>��iT,T�� " R apply(T t, U u) "; ��^R�]�OT�� -> reduce����BinaryOperator<T>��T���O�P���O�C
		 *  
		 *  T reduce(T identity(�_�l��), BinaryOperator<T> accumulator) �d��
		 *  �y�{: x=0,y=1,temp=1 -> x=1,y=2,temp=3 -> x=3,y=4,temp=7 -> ...
		 */
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
						  .reduce(0, (x,y) -> x + y);
		System.out.println(sum);
		
		System.out.println("---------------------------------------------------");
		
		//�p�⤽�q���~���`�M:
		//���D�@:�������^�����OInteger�����A�]���W���_�l�ȬO0�A�֩w�����šA���o��Employee�~��i�ର�šA�i�ର�Ū��F�賣�|�ʸ˦bOptional�̭��C����A�hget()���G�C
		//map�Mreduce���X�OGoogle�i������j�M���X�W�޳N�C
		Optional<Integer> optional = employee.stream()
							   	.map(Employee::getSalary) //��������
							   	.reduce(Integer::sum); //�A���[�`
		System.out.println(optional.get());
	}
	
	@Test
	public void Test10() {
		/*
		 *  <R, A> R collect(Collector<? super T, A, R> collector) �d��
		 */
		
		//�⤽�q�H�����W�٦C�L�X��
		List<String> list = employee.stream()
									.map(Employee::getName) //����W�ٳ����N�X��
									.collect(Collectors.toList()); //�A��toList()API�^�Ǥ@��List���X�^�h�C
		list.forEach(System.out::println);
		
		System.out.println("-----------------------------------------------");
		
		Set<String> set = employee.stream()
								  .map(Employee::getName) //����W�ٳ����N�X��
								  .collect(Collectors.toSet()); //�A��toSet()API�^�Ǥ@��Set���X�^�h�C
		set.forEach(System.out::println);
		
		System.out.println("-----------------------------------------------");
		/*
		 *  Collector<> toCollection(Supplier<C> collectionFactory)���Y����Supplier �^�Ǥ@�� Collector
		 */
		
		HashSet<String> hasgSet = employee.stream()
										  .map(Employee::getName) //����W�ٳ����N�X��
										  .collect(Collectors.toCollection(HashSet::new)); //�^�Ǥ@��HashSet
		hasgSet.forEach(System.out::println);
	}
	
	@Test
	public void Test11() {
		//�`��
		Long count = employee.stream()
							 .collect(Collectors.counting());
		System.out.println(count);
		
		System.out.println("-----------------------------------------");
		
		//����
		Double avg = employee.stream()
							 .collect(Collectors.averagingInt(Employee::getSalary));
		System.out.println(avg);
		
		System.out.println("-----------------------------------------");
		
		//�`�M
		Integer sum = employee.stream()
							 .collect(Collectors.summingInt(Employee::getSalary));
		System.out.println(sum);
		
		System.out.println("-----------------------------------------");
		
		//�̤j��
		//Collectors.maxBy(Comparator) �̭��O�@��Comparator �ݭn��@ compare(T o1, T o2)
		Optional<Employee> max = employee.stream()
							 			 .collect(Collectors.maxBy((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary())));
		System.out.println(max.get());
		
		System.out.println("-----------------------------------------");
		
		//�̤p��
		//�g�k�@(���oEmployee����):
		Optional min = employee.stream()
							 .collect(Collectors.minBy((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary())));
		System.out.println(min.get());
		
		//�g�k�G(���oEmployee��Salary����):
		Optional min2 = employee.stream()
								.map(Employee::getSalary)
								.collect(Collectors.minBy((Integer::compare)));
		System.out.println(min2.get());
		}
		
	@Test
	public void Test12() {
		/*
		 * ���� (���Ӫ��A����)
		 * groupingBy ��^����groupingBy(classifier, toList())
		 * collect �o�ɭԪ�^Map�Acollect�|�̷Ӥ���collectors�ҨϥΪ���k�^�Ǥ��P�ȡC
		 */
		Map<Status,List<Employee>> map = employee.stream()
						 .collect(Collectors.groupingBy(Employee::getStatus));
		System.out.println(map);
	}
		
	@Test
	public void Test13() {
		/*
		 * �h�Ť��� (���Ӫ��A�B�~�֤���)
		 * groupingBy ��^����groupingBy(classifier, toList())
		 * collect �o�ɭԪ�^Map<Status,Map<Object,List<Employee>>>�ѦҤU����Acollect�|�̷Ӥ���collectors�ҨϥΪ���k�^�Ǥ��P�ȡC
		 * 
		 * �yMap<Employee.Status,Map<Object,List<Employee>>> to Map<Employee.Status,List<Employee>>�z
		 */
		Map<Status,Map<String,List<Employee>>> map = employee.stream()
				 .collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy((e)->{
					 if((e).getAge() <=40) {
						 return "�~���H";
					 }else {
						 return "�Ѧ~�H";
					 }
				 })));
		System.out.println(map);
	}	
	
	@Test
	public void Test14() {
		/*
		 * ���� (�����~�����) -> �G���k
		 * �|��^true������Afalse������
		 */
		Map<Boolean,List<Employee>> map = employee.stream()
				 .collect(Collectors.partitioningBy((e)->e.getSalary() > 3000));
		System.out.println(map);
	}
	
	@Test
	public void Test15() {
		/*
		 * summarizing�u�� �ھګ��A�h��ܡA�i�H��X�����B�`�ơB�̤j�̤p�ȡA�[�`...�����\��
		 */
		IntSummaryStatistics intSum = employee.stream()
		 .collect(Collectors.summarizingInt(Employee::getSalary));
		
		System.out.println(intSum.getAverage()); //����
		System.out.println(intSum.getCount()); //�`��
		System.out.println(intSum.getMax()); //�̤j��
	}
	
	@Test
	public void Test16() {
		/*
		 * joining() �i�H�⪫�󵲦X
		 * joining(["��̤���"],["�r�ꭺ"],["�r���"])
		 */
		String str = employee.stream()
						.map(Employee::getName)
						.collect(Collectors.joining(","));
		System.out.println(str);
	}
}
