package Steam_Example;

import java.util.Arrays;
import java.util.Comparator;
import java.util.List;
import java.util.Optional;
import java.util.function.Predicate;

import org.junit.Test;

import Lambda_Example.Employee;
import Lambda_Example.Employee.Status;

/*
 *  ���d�Ҭ��פ�ާ@
 */
public class Stream02 {

	/*
	 * �d��P�ǰt
	 * allMatch -> �ˬd�O�_"�ǰt�Ҧ�����"�C
	 * anyMatch -> �ˬd�O�_"�ܤ֤ǰt�@�Ӥ���"�C
	 * noneMatch -> �ˬd�O�_"�S���ǰt�Ҧ�����"�C
	 * findFirst -> ��^�Ĥ@�Ӥ����C
	 * findAny -> ��^��estream�y�������@�����C
	 * count -> ��^stream�y���������`�ӼơC
	 * max -> ��^stream�y���̤j�ȡC
	 * min -> ��^stream�y���̤p�ȡC
	 * 
	 * �i��:�ĤQ�G��
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
		
		//�������h��A�@�w��Ĥ@�ӡC
		Optional<Employee> op = employee.stream()
							.filter((e)->e.getStatus().equals(Status.FREE))
							.findAny();
		System.out.println(op);
		
		System.out.println("-------------------------------------------------");
		
		//�h������h��A�ݭ��Ӱ���������A���ƾڦh�~�ݪ��X�ӡC
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
}
