package Steam_Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import Lambda_Example.Employee;

public class Stream01 {
	/*
	 *  一、Stream的三個操作步驟:
	 * 		(1).創建Stream (2).中間操作 (3).終止操作
	 *  二、篩選與切片
	 *  	filter -> 接收Lambda，從stream中過濾某些元素。
	 *  	limit -> 截斷流，指定被操作的元素不超過設定的數量。
	 *  	skip(n) -> 跳過元素，可以跳過n個元素後再執行，若元素不足n個，會返回一個空stream，與limit可以互相搭配。
	 *  	distenct -> 篩選，通過stream所產生的元素 hashCode()和 equals()去除重複元素。
	 *  三、
	 *  
	 *  注意:
	 *  	1.有內部迭代作用，全部交給Stream API完成。外部迭代:自己寫一個Iterator...
	 *  	2.
	 *  	3.
	 *  	4.
	 *  	5.
	 */
	List<Employee> list = Arrays.asList(
			new Employee("測試1",60,1000),
			new Employee("測試2",50,2000),
			new Employee("測試3",40,3000),
			new Employee("測試4",30,4000),
			new Employee("測試5",20,5000),
			new Employee("測試6",19,6000),
			new Employee("測試7",18,7000),
			new Employee("測試8",17,8000)
			);
	
	@Test
	public void Test01() {
		//中間操作
		Stream<Employee> stream = list.stream()
								  .filter((e) -> {
									System.out.println("Stream API Test 中間操作");
									return e.getAge() > 35;
								  });
		
		//終止操作   注意:若沒有終止操作，就整個stream都不會執行，"Stream API Test 中間操作"都不會執行。
		stream.forEach(System.out::println);
	}
	
	@Test
	public void Test02() {
		list.stream()
			.filter((e)->e.getSalary()>3000)
			.limit(3)
			.forEach(System.out::println);
	}
	
	
	 
	
}
