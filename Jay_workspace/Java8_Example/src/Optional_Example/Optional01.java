package Optional_Example;

import java.util.Optional;

import org.junit.Test;

import Lambda_Example.Employee;
import Lambda_Example.Employee.Status;

/*
 *	基礎 Optional API
 */
public class Optional01 {
	/*
	 * Optional.of(T t): 創建一個Optional 實體。										Test01()
	 * Optional.empty(): 創建一個空的Optional 實體。									Test02()
	 * Optional.ofNullable(T t): 若 t 不為null，創建Optional 實體，否則創建空實體。			Test03()
	 * isPresnt(): 判斷是否包含值。													Test04()
	 * orElse(T t): 如果調用對象包含值，返回該值，否則返回t。								Test05()
	 * orElseGet(Supplier s): 如果調用對象包含值，返回該值，否則返回s或取的值。					Test06()
	 * map(Function f): 如果值對其處理，並返回處理後的Optional，否則返回Optional.empty()。	Test07()
	 * flatMap(Function mapper): 與map類似，要求返回值必須是Optional。					Test08()
	 * 
	 */
	
	@Test
	public void test01() {
		/*
		 * Optional.of(T t) 範例 
		 * 		(a).創建一個Optional 實體
		 * 		(b).Optional.of()好處就是可以讓你更快知道NullPointerException 在那邊出現異常，有出現就是一定在Opional.of(A物件)裡頭。
		 */
		Optional<Employee> op = Optional.of(new Employee());
		
		Employee emp = op.get();
		System.out.println(emp);
		
		//	故意報錯
		Optional<Employee> op2 = Optional.of(null);
		System.out.println(op2);
	}
	
	@Test
	public void test02() {
		/*
		 * Optional.empty() 範例  
		 * 		(a).真正出現在NullPointerException的是(2)的地方，因為拿不到值，而(1)是沒錯的。
		 * 		(b).創建一個空的Optional 實體。
		 */		
		
		//(1)
		Optional<Employee> op = Optional.empty();
		//(2)
		System.out.println(op.get());
	}	
	
	@Test
	public void test03() {
		/*
		 *  Optional.ofNullable(T t) 範例
		 *  	(a).若 t 不為null，創建Optional 實體，否則創建空實體。
		 * 		(b).錯誤訊息直接告訴你沒有value，『java.util.NoSuchElementException: No value present』
		 * 		(c).ofNullable 就是empty()跟of()結合，可看SouceCode，請看 d 。
		 * 		(d).return value == null ? empty() : of(value); 如果等於空就empty()，沒有就of(value)創建實體。
		 */
		Optional<Employee> op = Optional.ofNullable(null);
		System.out.println(op.get());
	}
	
	@Test
	public void test04() {
		/*
		 * isPresnt() 範例
		 * 		(a).判斷是否包含值。
		 */
		Optional<Employee> op = Optional.ofNullable(new Employee());
		//	 判斷是否有值，有值就印出來。
		if(op.isPresent()) {
			System.out.println(op.get());
		}
	}
	
	@Test
	public void test05() {
		/*
		 * orElse(T t) 範例
		 * 		(a).如果調用物件包含值，返回該值，否則返回t的物件。
		 * 		(b).好用
		 */
		Optional<Employee> op = Optional.ofNullable(null);
		
		Employee emp = op.orElse(new Employee("代表op物件是空值，我是預設值",99,99,Status.BUSY));
		System.out.println(emp);
	}
	
	@Test
	public void test06() {
		/*
		 * orElseGet(Supplier s) 範例
		 * 		(a).如果調用對象包含值，返回該值，否則返回s或取的值
		 * 		(b).相較orElse()差別在可以做更多事情在Supplier內，可以再去做判斷等等操作。
		 */
		Optional<Employee> op = Optional.ofNullable(null);
		Employee emp = op.orElseGet(()-> new Employee("怎麼去利用supplier擴充請查google",99,99,Status.BUSY));	
		System.out.println(emp);
	}
	
	@Test
	public void test07() {
		/*
		 * map(Function f) 範例
		 * 		(a).如果值對其處理，並返回處理後的Optional，否則返回Optional.empty()。
		 * 		(b).若調用map()物件有值的話，就使用map(Function)內getName()，沒有則返回空的Optional物件。
		 */
		Optional<Employee> op = Optional.ofNullable(new Employee("我有值被調用請讓我實作Function",99,99,Status.BUSY));
		Optional<String> name = op.map((e)->e.getName());	
		System.out.println(name.get());
	}
	
	@Test
	public void test08() {
		/*
		 * flatMap(Function mapper) 範例
		 * 		(a). 與map類似，要求返回值必須是Optional。
		 * 		(b). 與map相較下，flatMap必須把Function也用Optional包裝，防止Function也為空。
		 */
		Optional<Employee> op = Optional.ofNullable(new Employee("我有值被調用請讓我實作Function並用Optional包裝我",99,99,Status.BUSY));
		Optional<String> name = op.flatMap((e)->Optional.of(e.getName()));	
		System.out.println(name.get());
	}
}
