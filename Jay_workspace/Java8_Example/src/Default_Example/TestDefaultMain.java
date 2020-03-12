package Default_Example;

import org.junit.Test;

public class TestDefaultMain {
	
	@Test
	public void Test01() {
		MyClass myClass = new MyClass();
		System.out.println(myClass.getName());
		
		/*
		 * 	可以參考MyFun_interface2，Interface裡面可以寫靜態方法了。
		 */
		MyFun_interface2.show();
	}
}
