package Lambda_Example;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Predicate;
import java.util.function.Supplier;

import org.junit.Test;


/*
 * Java8 ず竚みざ : 
 * 		 	Consumer<T> :禣ざ  
 * 				void accept(T t);
 * 
 * 			Supplier<T> :ㄑ倒ざ
 * 				T get();
 * 
 * 			Function<T,R>:ㄧ计ざ
 * 				R apply(T t);
 * 
 * 			Predicate<T> :耞ēざ
 * 				boolean test(T t);
 * 
 * ㄤざ:
 * 			BiFunction<T,U,R> 把计 :TU   :R
 *			BiConsumer<T,U> 把计 :TU  :void
 *			... (临Τ砛ざ才把计Α呼琩高)
 * 
 * Lambda オ凹 : 笷把计
 * Lambda 凹 : 笷璶磅︽弧龟よΑ嘿Lambda砰
 * 
 * */

public class Lambda01 {
	List<Employee> list = Arrays.asList(
			new Employee("代刚1",60,1000),
			new Employee("代刚2",50,2000),
			new Employee("代刚3",40,3000),
			new Employee("代刚4",30,4000),
			new Employee("代刚5",20,5000),
			new Employee("代刚6",19,6000),
			new Employee("代刚7",18,7000),
			new Employee("代刚8",17,8000)
			);
	
	@Test
	public void Test01() {
		/*
		 * Consumer よ猭  void accept(T t) 把计礚
		 * (m) -> Τ把计璝Τ贺把计ㄤ龟ぃノ珹腹策篋┦临琌糶ゑ耕
		 * 
		 * 瑈祘:10000 -> accept(10000) -> Lambda凹龟Consumerざvoid accept(T t)ず甧
		 *     眔accept(x)ずx把计 
		 * */
		happy(10000,(m)->System.out.println("Consumer Test 肚癳把计:" + m + "じ"));
	}
	
	public void happy(double money , Consumer<Double> con) {
		con.accept(money);
	}
	
	//-------------------------------------------------------------------------------------	
	
	@Test
	public void Test02() {
		/*
		 * Τ把计Τ
		 * Supplier<T>:ㄧ计ざ  よ猭 :T get();
		 * 
		 * 瑈祘:getNumList(10,() -> (int)(Math.random() * 100)) 肚把计10秈龟Supplier getNumList肚List
		 * 	       ┮ノList钡癬ㄓノforeachㄓ
		 *
		 * */
		List<Integer> numList = getNumList(10,() -> (int)(Math.random() * 100));
		
		for(Integer num : numList) {
			System.out.println(num);
		}
	}
	
	//惠―:玻ネ繦诀计秈list栋柑
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
	public void Test03() {
		/*
		 * Τ把计Τ
		 * Function<T,R>:ㄧ计ざ よ猭 :R apply(T t);
		 * */
		String newStr = strHandler("\t\t\t\t Supplier Test    ",(str) -> str.trim());
		System.out.println(newStr);
	}
	
	public String strHandler(String str, Function<String , String> fun) {
		return fun.apply(str);
	}
	
	//-------------------------------------------------------------------------------------	
	
	@Test
	public void Test04() {
		 /*
		  * Predicate<T> :耞ēざ     よ猭:boolean test(T t);
		  * 
		  */
		List<String> list = Arrays.asList("代刚 ","Predicate Test ","耞ēざ ","肚 boolean ");
		List<String> strList = filterStr(list,(x)->x.length()>5);
		
		for(String str : strList) {
			System.out.print(str);
		}
	}
	
	//盢骸ì兵ン惠―﹃秈栋い
	public List<String> filterStr(List<String> list,Predicate<String> predicate){
		List<String> strList = new ArrayList<>();
		
		for(String str : list) {
			if(predicate.test(str)) {
				strList.add(str);
			}
		}
		return strList;
	}
}
