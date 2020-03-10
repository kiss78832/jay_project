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
 *  此範例為終止操作
 */
public class Stream02 {

	/*
	 * 查找與匹配
	 * allMatch -> 檢查是否"匹配所有元素"。
	 * anyMatch -> 檢查是否"至少匹配一個元素"。
	 * noneMatch -> 檢查是否"沒有匹配所有元素"。
	 * findFirst -> 返回第一個元素。
	 * findAny -> 返回當前stream流中的任一元素。
	 * count -> 返回stream流中元素的總個數。
	 * max -> 返回stream流中最大值。
	 * min -> 返回stream流中最小值。
	 * 
	 * 進度:第十二章
	 */
	
	List<Employee> employee = Arrays.asList(
			new Employee("測試1",60,1000,Status.FREE),
			new Employee("測試2",50,2000,Status.VOCATION),
			new Employee("測試3",40,3000,Status.VOCATION),
			new Employee("測試4",30,4000,Status.FREE),
			new Employee("測試5",20,5000,Status.BUSY),
			new Employee("測試6",19,6000,Status.BUSY),
			new Employee("測試7",18,7000,Status.FREE),
			new Employee("測試8",17,8000,Status.BUSY),
			new Employee("測試8",17,8000,Status.VOCATION)
			);
	@Test
	public void Test01() {
		/*
		 * boolean allMatch(Predicate<? super T> predicate) 範例
		 */
		boolean b1 = employee.stream()
					.allMatch((e)->e.getSalary().equals(Status.BUSY));
		System.out.println(b1);
	}
	
	@Test
	public void Test02() {
		/*
		 * boolean anyMatch(Predicate<? super T> predicate) 範例
		 */
		boolean b1 = employee.stream()
					.anyMatch((e)->e.getSalary().equals(Status.BUSY));
		System.out.println(b1);
	}
	
	@Test
	public void Test03() {
		/*
		 *  boolean noneMatch(Predicate<? super T> predicate) 範例
		 */
		boolean b1 = employee.stream()
					.noneMatch((e)->e.getSalary().equals(Status.BUSY));
		System.out.println(b1);
	}
	
	@Test
	public void Test04() {
		/*
		 *  Optional<T> findFirst() 範例
		 *  Optional可以降低為null的機率，如果為空有代替API方法。
		 */
		Optional<Employee> op = employee.stream()
							.sorted((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary()))
							.findFirst();
		System.out.println(op);
	}
	
	@Test
	public void Test05() {
		/*
		 *  Optional<T> findAny() 範例
		 *  返回狀態是FREE的員工
		 */
		
		//單執行緒去找，一定找第一個。
		Optional<Employee> op = employee.stream()
							.filter((e)->e.getStatus().equals(Status.FREE))
							.findAny();
		System.out.println(op);
		
		System.out.println("-------------------------------------------------");
		
		//多執行緒去找，看哪個執行緒先找到，但數據多才看的出來。
		Optional<Employee> op1 = employee.parallelStream()
				.filter((e)->e.getStatus().equals(Status.FREE))
				.findAny();
		System.out.println(op1);
	}
	
	@Test
	public void Test06() {
		/*
		 *  Optional<T> max(Comparator<? super T> comparator) 範例
		 */
		Optional<Employee> op = employee.stream()
							.max((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary()));
		System.out.println(op.get());
	}
	
	@Test
	public void Test07() {
		/*
		 *  Optional<T> min(Comparator<? super T> comparator) 範例
		 */
		Optional<Employee> op = employee.stream()
							.min((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary()));
		System.out.println(op.get());
	}
}
