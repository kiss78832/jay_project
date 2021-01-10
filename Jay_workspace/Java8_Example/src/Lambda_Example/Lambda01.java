package Lambda_Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Hashtable;
import java.util.Iterator;
import java.util.List;
import java.util.Spliterator;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;


/*
 * Java8 內置四大核心介面 : 
 * 		 	Consumer<T> :消費型介面  
 * 				void accept(T t);
 * 
 * 			Supplier<T> :供給型介面
 * 				T get();
 * 
 * 			Function<T,R>:函數型介面
 * 				R apply(T t);
 * 
 * 			Predicate<T> :斷言型介面
 * 				boolean test(T t);
 * 
 * 	其他介面:
 * 			BiFunction<T,U,R> 參數 :T、U  返回值 :R
 *			BiConsumer<T,U> 參數 :T、U  返回值:void
 *			...	(還有許多介面可以符合多參數的格式，可以上網查詢)
 * 
 * Lambda 左側 : 表達參數列表。
 * Lambda 右側 : 表達要執行的功能，也可以說實作的方式，稱Lambda體。
 * 
 */

public class Lambda01 {
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
	public void test01() {
		/*
		 * Consumer 方法  void accept(T t) 『一個參數並無返回值。』
		 * (m) -> 代表有一個參數，若只有一種參數，其實也可以不用括號，習慣性還是寫比較好。
		 * 
		 * 	流程:10000 -> accept(10000) -> Lambda右側實作Consumer介面下的void accept(T t)內容，
		 * 	也可以取得到accept(x)內的x參數。 
		 * 
		 *  
		 */
		happy(10000,(m)->System.out.println("Consumer test 傳送參數:" + m + "元"));
	}
	
	public void happy(double money , Consumer<Double> con) {
		System.out.println("1");
		con.accept(money);
		System.out.println("2");
	}
	
	//-------------------------------------------------------------------------------------	
	
	@Test
	public void test02() {
		/*
		 * 	有一個參數，並有返回值。
		 *  Supplier<T>:函數型介面 。 方法 :T get();
		 * 
		 * 	流程:getNumList(10,() -> (int)(Math.random() * 100)) 傳參數10進去，實作Supplier[(int)(Math.random() * 100)]並回傳一個Integer類型， 
		 * 		所以才能用n來接Integer n = supplier.get()，跟test01()相比的話，test01()會直接印出來，
		 *      ，getNumList回傳一個List，所以用一個List接起來，在用foreach印出來。
		 *
		 * */
		List<Integer> numList = getNumList(10,() -> (int)(Math.random() * 100));
		
		for(Integer num : numList) {
			System.out.println(num);
		}
	}
	
	//	需求:產生隨機數，放進list集合裡面。
	public List<Integer> getNumList(int num, Supplier<Integer> supplier){
		List<Integer> list = new ArrayList<>();
			
			for (int i = 0; i < num; i++) {
				Integer n = supplier.get();  
				list.add(n);
			}
		return list;
	}
	
	//-------------------------------------------------------------------------------------		
	
	@Test
	public void test03() {
		/*
		 *	 有一個參數，並有返回值。
		 *   Function<T,R>:函數型介面。 方法 :R apply(T t);
		 *   流程:把"\t\t\t\t Supplier test    "字串丟給fun.apply(str)立即去實作(str) -> str.trim()，回傳值應該就是依照(str) -> str.trim()而不是回傳Function。
		 * */
		String newStr = strHandler("\t\t\t\t Supplier test    ",(str) -> str.trim());
		System.out.println(newStr);
	}
	
	public String strHandler(String str, Function<String , String> fun) {
		return fun.apply(str);
	}
	
	//-------------------------------------------------------------------------------------	
	
	@Test
	public void test04() {
		 /*
		  * Predicate<T> :斷言型介面     方法:boolean test(T t);
		  * 
		  */
		List<String> list = Arrays.asList("測試 ","Predicate test ","斷言型介面 ","回傳 boolean ");
		List<String> strList = filterStr(list,(x)->x.length()>5);
		
		for(String str : strList) {
			
			System.out.print(str);
		}
	}
	
	//	將滿足條件的需求字串，放進集合中。
	public List<String> filterStr(List<String> list,Predicate<String> predicate){
		List<String> strList = new ArrayList<>();
		
		for(String str : list) {
			//predicate.test(str)直接去實作x.length()>5，然後會回傳一個boolean值
			if(predicate.test(str)) {
				//長度大於5的才會List
				strList.add(str);
			}
		}
		return strList;
	}
	
	@Test
	public void test05() {
		ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
		Spliterator<Integer> numbers1 = numbers.spliterator();

		numbers1.tryAdvance(e -> System.out.println(e + "。")); // 1
////
//		numbers1.forEachRemaining( e -> System.out.println( e ) ); // 2 3 4 5 6

		Spliterator<Integer> numbers2 = numbers1.trySplit();
//
		numbers1.forEachRemaining( e -> System.out.println( e +",") );      //4 5 6
		numbers2.forEachRemaining( e -> System.out.println( e +"=") );      //1 2 3
	}
	
	@Test
	public void test06() {
		Hashtable<String,Object> map = new Hashtable<>();
		map.put("B","2");
		map.put("A","1");
		map.put("C","3");
		map.put("1","3");
		map.put("2","3");
		map.put("7","3");
		map.put("4","3");
		map.put("6","3");
		map.put("5","3");
		map.put("3","3");
		
		
		Iterator itr = map.
		while (itr.hasNext()) {
			System.out.println(itr.next());
			
		}
		

	}
}
