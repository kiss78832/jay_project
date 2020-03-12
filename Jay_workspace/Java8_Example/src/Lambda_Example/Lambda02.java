package Lambda_Example;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.BiFunction;
import java.util.function.BiPredicate;
import java.util.function.Consumer;
import java.util.function.Function;
import java.util.function.Supplier;

import org.junit.Test;

public class Lambda02 {
	
	/*
	 * 方法引用: 若Lambda體中的內容已經實現了，我們可以使用"方法引用"(可以理解為方法引用是Lambda表達式的另一種表現形式)
	 * 
	 *	 主要有三種語法格式: 
	 *        (1).物件:實體方法名
	 *        
	 *        (2). 類別:靜態方法名
	 *  
	 *  	  (3). 類別:實體方法名
	 *  
	 * 	建構子引用格式: 
	 *  	  (1). ClassName::new
	 *  
	 * 	注意:
	 *     1.Lambda體中調用方法的參數與返回值類型，要與函數式介面中抽象方法的函數列表和返回值類型保持一致。
	 *     2.需要調用的建構子的參數列表要與Function<T, R>一致。
	 *     3.A::B這兩個一定要有關聯
	 *     		a.Employee::getAge->(Employee有getAge())
	 *     		b.Integer::compare->(Integer有compare())
	 *     		c.System.out::println->(System內有out ; out是PrintStream類別內有println()方法)
	 *     		d.(重點)A::B 這兩個一定可以A.B()，也就是一定是前者的方法或常數。
	 */
	@Test
	public void Test01() {
		/*
		 * 	物件::實體方法名
		 *  void accept(T t) <-> void println(String x)  : Lambda體必須參數數量、型態，回傳值型態都一樣，才能用 ::。
		 * 	方法引用的右側如果是方法就不用加()。
		 */
		PrintStream ps1 = System.out;
		Consumer<String> con = (x)->ps1.println(x);
		
		//寫法一: 物件::實體方法名 (ps::println)  類別::實體方法名(PrintStream::println)->但是Lambda體不可能寫PrintStream.println()，編譯不會成功。
		PrintStream ps = System.out;
		Consumer<String> con1 = ps::println;
		//寫法二:
		Consumer<String> con2 = System.out::println;
		con2.accept("測試");
		
	}
	
	@Test
	public void Test02() {
		/*
		 * 	物件::實體方法名
		 *  void accept(T t) <-> void println(String x)  : Lambda體必須參數數量、型態，回傳值型態都一樣，才能用 ::。 
		 */
		Employee emp = new Employee();
		Supplier<String> sup = () -> emp.getName();
		String str = sup.get();
		System.out.println(str);
		
		//物件::實體方法名 (emp::getAge)  類別::實體方法名(Supplier::getAge)->但是Lambda體不可能寫Employee.getName()，編譯不會成功。
		Supplier<Integer> sup2 = emp::getAge;
		Integer num = sup2.get();
		System.out.println(num);
	}
	
	@Test
	public void Test03() {
		/*
		 *	 類別::靜態方法名 
		 *  Integer : public static int compare(int x, int y)
		 */
		
		Comparator<Integer> com = (x,y) -> Integer.compare(10, 9);
		
		Comparator<Integer> com1 = Integer::compare;
	}
	
	@Test
	public void Test04() {
		/*
		 * 	類別::實體方法名 
		 *  Integer : public static int compare(int x, int y)
		 * 
		 * 規範:(x,y) -> x.equals(y) 『(x,y)-> x一定要是調用者，y一定要是方法內參數』，才能使用方法引用。
		 */
		
		BiPredicate<String,String> bp = (x,y) -> x.equals(y);
		
		BiPredicate<String,String> bp2 = String::equals;
	}
	
	@Test
	public void Test05() {
		/*
		 * ClassName(類別名稱)::new
		 * 
		 * Employee::new
		 */
		Supplier<Employee> sup = () -> new Employee();
		
		Supplier<Employee> sup2 = Employee::new;
		Employee emp = sup2.get();
		System.out.println(emp);
	}
	
	@Test
	public void Test06() {
		Function<Integer,Employee> fun = (x) -> new Employee(x);
		
		//	思考一下這邊調用是Employee的哪個建構子?是幾個參數的? 
		//	Ans:取決於public interface Function<T, R>裡面是一個參數，所以是取Employee的一個參數建構子。
		Function<Integer,Employee> fun2 = Employee::new;
		Employee emp = fun2.apply(101);
		System.out.println(emp);
		
		//	思考一下下方程式為何編譯不會過? 因為Employee建構子沒有兩個參數的建構子。注意:<Integer,Integer,Employee>:前面兩個是參數，後面是回傳值。BiFunction<T, U, R>
		//	BiFunction<Integer,Integer,Employee> bf = Employee::new;
	}
	
	
}
