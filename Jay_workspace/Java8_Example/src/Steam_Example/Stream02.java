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
 *  此範例為終止操作
 */
public class Stream02 {
	/*
	 * 查找與匹配:
	 * 	allMatch -> 檢查是否"匹配所有元素"。 		Test01()
	 * 	anyMatch -> 檢查是否"至少匹配一個元素"。		Test02()
	 * 	noneMatch -> 檢查是否"沒有匹配所有元素"。	Test03()
	 * 	findFirst -> 返回第一個元素。				Test04()
	 * 	findAny -> 返回當前stream流中的任一元素。	Test05()
	 * 	max -> 返回stream流中最大值。				Test06()
	 * 	min -> 返回stream流中最小值。 			Test07()
	 *  count -> 返回stream流中元素的總個數。 		Test08()
	 * 
	 * 歸約:
	 *  reduce -> 可以將stream流中元素反覆結合起來，得到一個值。 	Test09()
	 * 
	 * 收集:
	 *  collect -> 將stream流轉為其他形式。接收一個collector介面的實作，用於給stream中元素做彙總的方法。   Test10()
	 *  
	 * Collectors方法: 
	 * 	 Collectors.averagingInt 平均 	Test11()
	 *	 Collectors.counting 總數            	Test11()
	 *	 Collectors.summingInt 總和       	Test11()
	 *	 Collectors.maxBy 最大值                 	Test11()
	 * 	 Collectors.minBy 最小值                 	Test11()
	 * 
	 * 	 Collectors.groupingBy 分組 or 多級分組									Test12()、Test13()
	 * 	 Collectors.partitioningBy 分區 (true & false 二分法)					Test14()
     *   Collectors.summarizingInt 加總、平均、最大最小值工具 (Int、Double、Long)		Test15()
     *   Collectors.joining 結合											 	Test16()
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
		
		//stream():單執行緒去找，一定找第一個。
		Optional<Employee> op = employee.stream()
							.filter((e)->e.getStatus().equals(Status.FREE))
							.findAny();
		System.out.println(op);
		
		System.out.println("-------------------------------------------------");
		
		//parallelStream():多執行緒去找，看哪個執行緒先找到，但數據多才看的出來。
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
	
	@Test
	public void Test08() {
		/*
		 *  long count() 範例
		 */
		Long count = employee.stream()
							 .count();
		System.out.println(count);
	}
	
	@Test
	public void Test09() {
		/* 
		 *  BinaryOperator<T>: BiFunction<T,T,T>塞進T,T給 " R apply(T t, U u) "; 返回R也是T值 -> reduce中的BinaryOperator<T>的T都是同型別。
		 *  
		 *  T reduce(T identity(起始值), BinaryOperator<T> accumulator) 範例
		 *  流程: x=0,y=1,temp=1 -> x=1,y=2,temp=3 -> x=3,y=4,temp=7 -> ...
		 */
		List<Integer> list = Arrays.asList(1,2,3,4,5,6,7,8,9,10);
		
		Integer sum = list.stream()
						  .reduce(0, (x,y) -> x + y);
		System.out.println(sum);
		
		System.out.println("---------------------------------------------------");
		
		//計算公司的薪資總和:
		//問題一:為什麼返回的不是Integer類型，因為上面起始值是0，肯定不為空，但這邊Employee薪資可能為空，可能為空的東西都會封裝在Optional裡面。之後再去get()結果。
		//map和reduce結合是Google進行網路搜尋的出名技術。
		Optional<Integer> optional = employee.stream()
							   	.map(Employee::getSalary) //先做提取
							   	.reduce(Integer::sum); //再做加總
		System.out.println(optional.get());
	}
	
	@Test
	public void Test10() {
		/*
		 *  <R, A> R collect(Collector<? super T, A, R> collector) 範例
		 */
		
		//把公司人員的名稱列印出來
		List<String> list = employee.stream()
									.map(Employee::getName) //先把名稱都迭代出來
									.collect(Collectors.toList()); //再用toList()API回傳一個List集合回去。
		list.forEach(System.out::println);
		
		System.out.println("-----------------------------------------------");
		
		Set<String> set = employee.stream()
								  .map(Employee::getName) //先把名稱都迭代出來
								  .collect(Collectors.toSet()); //再用toSet()API回傳一個Set集合回去。
		set.forEach(System.out::println);
		
		System.out.println("-----------------------------------------------");
		/*
		 *  Collector<> toCollection(Supplier<C> collectionFactory)裡頭有個Supplier 回傳一個 Collector
		 */
		
		HashSet<String> hasgSet = employee.stream()
										  .map(Employee::getName) //先把名稱都迭代出來
										  .collect(Collectors.toCollection(HashSet::new)); //回傳一個HashSet
		hasgSet.forEach(System.out::println);
	}
	
	@Test
	public void Test11() {
		//總數
		Long count = employee.stream()
							 .collect(Collectors.counting());
		System.out.println(count);
		
		System.out.println("-----------------------------------------");
		
		//平均
		Double avg = employee.stream()
							 .collect(Collectors.averagingInt(Employee::getSalary));
		System.out.println(avg);
		
		System.out.println("-----------------------------------------");
		
		//總和
		Integer sum = employee.stream()
							 .collect(Collectors.summingInt(Employee::getSalary));
		System.out.println(sum);
		
		System.out.println("-----------------------------------------");
		
		//最大值
		//Collectors.maxBy(Comparator) 裡面是一個Comparator 需要實作 compare(T o1, T o2)
		Optional<Employee> max = employee.stream()
							 			 .collect(Collectors.maxBy((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary())));
		System.out.println(max.get());
		
		System.out.println("-----------------------------------------");
		
		//最小值
		//寫法一(取得Employee物件):
		Optional min = employee.stream()
							 .collect(Collectors.minBy((e1,e2)->Integer.compare(e1.getSalary(),e2.getSalary())));
		System.out.println(min.get());
		
		//寫法二(取得Employee的Salary的值):
		Optional min2 = employee.stream()
								.map(Employee::getSalary)
								.collect(Collectors.minBy((Integer::compare)));
		System.out.println(min2.get());
		}
		
	@Test
	public void Test12() {
		/*
		 * 分組 (按照狀態分組)
		 * groupingBy 返回類型groupingBy(classifier, toList())
		 * collect 這時候返回Map，collect會依照內部collectors所使用的方法回傳不同值。
		 */
		Map<Status,List<Employee>> map = employee.stream()
						 .collect(Collectors.groupingBy(Employee::getStatus));
		System.out.println(map);
	}
		
	@Test
	public void Test13() {
		/*
		 * 多級分組 (按照狀態、年齡分組)
		 * groupingBy 返回類型groupingBy(classifier, toList())
		 * collect 這時候返回Map<Status,Map<Object,List<Employee>>>參考下方↓，collect會依照內部collectors所使用的方法回傳不同值。
		 * 
		 * 『Map<Employee.Status,Map<Object,List<Employee>>> to Map<Employee.Status,List<Employee>>』
		 */
		Map<Status,Map<String,List<Employee>>> map = employee.stream()
				 .collect(Collectors.groupingBy(Employee::getStatus,Collectors.groupingBy((e)->{
					 if((e).getAge() <=40) {
						 return "年輕人";
					 }else {
						 return "老年人";
					 }
				 })));
		System.out.println(map);
	}	
	
	@Test
	public void Test14() {
		/*
		 * 分區 (按照薪資分區) -> 二分法
		 * 會返回true的物件，false的物件
		 */
		Map<Boolean,List<Employee>> map = employee.stream()
				 .collect(Collectors.partitioningBy((e)->e.getSalary() > 3000));
		System.out.println(map);
	}
	
	@Test
	public void Test15() {
		/*
		 * summarizing工具 根據型態去選擇，可以算出平均、總數、最大最小值，加總...等等功能
		 */
		IntSummaryStatistics intSum = employee.stream()
		 .collect(Collectors.summarizingInt(Employee::getSalary));
		
		System.out.println(intSum.getAverage()); //平均
		System.out.println(intSum.getCount()); //總數
		System.out.println(intSum.getMax()); //最大值
	}
	
	@Test
	public void Test16() {
		/*
		 * joining() 可以把物件結合
		 * joining(["兩者中間"],["字串首"],["字串尾"])
		 */
		String str = employee.stream()
						.map(Employee::getName)
						.collect(Collectors.joining(","));
		System.out.println(str);
	}
}
