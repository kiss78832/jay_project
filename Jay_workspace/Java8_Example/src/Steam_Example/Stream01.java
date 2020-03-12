package Steam_Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import org.junit.Test;

import Lambda_Example.Employee;
/*
 *  此範例為中間操作
 */
public class Stream01 {
	/*
	 *  一、Stream的三個操作步驟: 
	 * 		(1).創建Stream (2).中間操作 (3).終止操作
	 * 
	 *  二、篩選與切片 (Test01~Test04)
	 *  	filter -> 接收Lambda，從stream中過濾某些元素。
	 *  	limit -> 截斷流，指定被操作的元素不超過設定的數量。
	 *  	skip(n) -> 跳過元素，可以跳過n個元素後再執行，若元素不足n個，會返回一個空stream，與limit可以互相搭配。
	 *  	distinct -> 篩選，通過stream所產生的元素 hashCode()和 equals()去除重複元素。
	 *  
	 *  三、映射 (Test05)
	 *  	map -> 接收Lambda，將元素轉換成其他形式，接收一個函數作為參數，該函數會被應用到每個元素，並將其映射成一個新的元素。
	 *  	flatMap -> 接收一個函數作為參數，將該stream 中的每一個都換成另一個stream，然後把所有stream 連接成一個stream。
	 *  
	 *  四、排序 (Test06~Test07)
	 *  	sorted() -> 自然排序(Comparable)，按照compareTo(T o)排序。
	 *  		例如:String 類別 『public final class String implements Comparable<String>, CharSequence』
	 *  	sorted(Scomparator com) -> 訂製排序(Comparator)，依照compare(T o1, T o2)排序。
	 *  	
	 *  注意:
	 *  	1.有內部迭代作用，全部交給Stream API完成。外部迭代:自己寫一個Iterator...
	 */
	List<Employee> list = Arrays.asList(
			new Employee("測試1",60,1000),
			new Employee("測試2",50,2000),
			new Employee("測試3",40,3000),
			new Employee("測試4",30,4000),
			new Employee("測試5",20,5000),
			new Employee("測試6",19,6000),
			new Employee("測試7",18,7000),
			new Employee("測試8",17,8000),
			new Employee("測試8",17,8000)
			);
	
	@Test
	public void test01() {
		/*
		 * filter() 範例
		 */
		//中間操作
		Stream<Employee> stream = list.stream()
								  .filter((e) -> {
									System.out.println("Stream API test 中間操作");
									return e.getAge() > 35;
								  });
		
		//終止操作   注意:若沒有終止操作，就整個stream都不會執行，"Stream API test 中間操作"都不會執行。
		stream.forEach(System.out::println);
	}
	
	@Test
	public void test02() {
		/*
		 * limit() 範例
		 * 找出滿足條件就不會在往下執行，可以去觀察印出"短路!"狀況，(1or2)都會中斷迭代，(3)找不到符合第三個條件就迭代到結束。 與&& || 短路運算概念類似，一邊對了就不會再去判斷。
		 */
		
		list.stream()
			.filter((e)->{
				System.out.println("短路!");
				//若只有(e)->e.getSalary()>3000 可以省略return，因為Stream<T> filter(Predicate<? super T> predicate);
				//內的Predicate的方法boolean test(T t)，需要回傳一個boolean值。
				return e.getSalary()<3000;
			})
			.limit(3) //找出滿足條件就不會在往下執行，可以去觀察印出"短路!"狀況，(1or2)都會中斷迭代，(3)找不到符合第三個條件就迭代到結束。 與&& || 短路運算概念類似，一邊對了就不會再去判斷。
			.forEach(System.out::println);
	}
	
	@Test
	public void test03() {
		/*
		 * skip(n) 範例
		 */
		list.stream()
		.filter((e)-> e.getSalary()<3000)
		.skip(1)
		.forEach(System.out::println);
		
	}
	
	@Test
	public void test04() {
		/*
		 * distinct() 範例
		 * hashCode()和 equals()去除重複元素。可以到Employee去掉equals、hashCode註解，就能過濾重複的。範例:測試8
		 */
		list.stream()
		.filter((e)-> e.getSalary()>100)
		.distinct()
		.forEach(System.out::println);
	}
	
	@Test
	public void test05() {
		/*
		 * map(Function) 範例  
		 * 流程: 使用迭代方式，取"aaa"執行toUpperCase() -> 取"bbb"執行toUpperCase() -> 取"ccc"執行toUpperCase() ...依此類推，最後再用forEach印出。
		 */
		List<String> listTest = Arrays.asList("aaa","bbb","ccc","ddd","eee");
		listTest.stream()
				.map((list_test)->list_test.toUpperCase())
				.forEach(System.out::println);
		
		System.out.println("------------------------------------------------------------------------------");
		
		//Employee 範例
		list.stream()
			.map(Employee::getName)
			.forEach(System.out::println);
		
		System.out.println("------------------------------------------------------------------------------");
		
		/*
		 *  map(Function) 範例操作: 把字串分解後回傳stream。
		 *  mapSouceCode -> 『<R> Stream<R> map(Function<? super T, ? extends R> mapper)』
		 *  
		 *  流程: 拿到["aaa","bbb","ccc","ddd","eee"] 集合到filterCharacter分解出{{a,a,a},{b,b,b,},{c,c,c},{d,d,d}}，在終止操作再forEach一次。
		 *  map是拿到一整串集合。
		 */
		
		//map本身返回一個Stream，<>內是filterCharacter返回的Stream<Character>，因此變Stream<Stream<Character>>
		Stream<Stream<Character>> stream = listTest.stream()
				.map(Stream01::filterCharacter);//返回 {{a,a,a},{b,b,b,},{c,c,c},{d,d,d}}  
		
		//stream.forEach本身迭代出來的還是stream流(filterCharacter方法的stream)，所以還要再裡面迭代一次。
		stream.forEach((sm) -> {
			sm.forEach(System.out::println);
		});
		
		System.out.println("------------------------------------------------------------------------------");
		 
		/*
		 *  flatMap範例: 把字串分解存入集合回傳。(進階)
		 *  flatMap是拿一個一個元素的集合。
		 *  
		 *  流程: 取到{[a,a,a,b,b,b,c,c,c,d,d,d]} -> 
		 */
		Stream<Character> stream2 = listTest.stream()
				.flatMap(Stream01::filterCharacter);//返回 {a,a,a,b,b,b,,c,c,c,d,d,d} 
		stream2.forEach(System.out::println);
		
	}
	//把英文字串拆解單一字元
	public static Stream<Character> filterCharacter(String str){
		List<Character> list = new ArrayList<>();
		
		for (Character character : str.toCharArray()) {
			list.add(character);
		}
		return list.stream(); //可以直接.streamr()就能返回一個流
	}
	
	@Test
	public void test06() {
		/*
		 * sorted() 範例
		 */
		List<String> list = Arrays.asList("aaa","bbb","ccc","ddd","eee");
		
		list.stream()
			.sorted()
			.forEach(System.out::println);
	}
	
	@Test
	public void test07() {
		/*
		 * sorted(Scomparator com) 範例
		 * 自己寫比較判斷式，依照年齡排序。
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
