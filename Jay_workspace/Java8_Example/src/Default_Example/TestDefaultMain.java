package Default_Example;

import org.junit.Test;

public class TestDefaultMain {
	
	@Test
	public void Test01() {
		MyClass myClass = new MyClass();
		System.out.println(myClass.getName());
		
		/*
		 * 	�i�H�Ѧ�MyFun_interface2�AInterface�̭��i�H�g�R�A��k�F�C
		 */
		MyFun_interface2.show();
	}
}
