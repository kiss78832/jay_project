package Lambda_Example;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

public class Lambda02 {
	
	/*
	 * 方法引用: 若Lambda體中的內容已經實現了，我們可以使用"方法引用"(可以理解為方法引用是Lambda表達式的另一種表現形式)
	 * 
	 * 主要有三種語法格式: 
	 *        (1). 物件:實體方法名
	 *        
	 *        (2). 類別:靜態方法名
	 *  
	 *  	  (3). 類別:實體方法名
	 */
	@Test
	public void Test01() {
		/*
		 * 物件:實體方法名
		 * void println(String x) <-> void accept(T t) : Lambda體必須參數數量、型態，回傳值型態都一樣，才能用 ::。 
		 * 
		 */
		PrintStream ps1 = System.out;
		Consumer<String> con = (x)->ps1.println(x);
		
		PrintStream ps = System.out;
		Consumer<String> con1 = ps::println;
		
		Consumer<String> con2 = System.out::println;
		con2.accept("測試");
	}
	
	@Test
	public void Test02() {
		Employee emp = new Employee();
		Supplier<String> sup = () -> emp.getName();
		String str = sup.get();
		System.out.println(str);
		
		Supplier<Integer> sup2 = emp::getAge;
		Integer num = sup2.get();
		System.out.println(num);
	}
	
	@Test
	public void Test03() {
		Comparator<Integer> com = (x,y) -> Integer.compare(10, 9);
		
		Comparator<Integer> com1 = Integer::compare;
		
	}
	
	
	
	
}
