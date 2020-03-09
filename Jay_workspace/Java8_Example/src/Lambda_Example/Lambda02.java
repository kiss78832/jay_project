package Lambda_Example;

import java.io.PrintStream;
import java.util.Comparator;
import java.util.function.Consumer;
import java.util.function.Supplier;

import org.junit.Test;

public class Lambda02 {
	
	/*
	 * ��k�ޥ�: �YLambda�餤�����e�w�g��{�F�A�ڭ̥i�H�ϥ�"��k�ޥ�"(�i�H�z�Ѭ���k�ޥάOLambda��F�����t�@�ت�{�Φ�)
	 * 
	 * �D�n���T�ػy�k�榡: 
	 *        (1). ����:�����k�W
	 *        
	 *        (2). ���O:�R�A��k�W
	 *  
	 *  	  (3). ���O:�����k�W
	 */
	@Test
	public void Test01() {
		/*
		 * ����:�����k�W
		 * void println(String x) <-> void accept(T t) : Lambda�饲���ѼƼƶq�B���A�A�^�ǭȫ��A���@�ˡA�~��� ::�C 
		 * 
		 */
		PrintStream ps1 = System.out;
		Consumer<String> con = (x)->ps1.println(x);
		
		PrintStream ps = System.out;
		Consumer<String> con1 = ps::println;
		
		Consumer<String> con2 = System.out::println;
		con2.accept("����");
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
