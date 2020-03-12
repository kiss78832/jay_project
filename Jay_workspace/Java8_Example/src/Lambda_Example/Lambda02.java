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
	 * ��k�ޥ�: �YLambda�餤�����e�w�g��{�F�A�ڭ̥i�H�ϥ�"��k�ޥ�"(�i�H�z�Ѭ���k�ޥάOLambda��F�����t�@�ت�{�Φ�)
	 * 
	 *	 �D�n���T�ػy�k�榡: 
	 *        (1).����:�����k�W
	 *        
	 *        (2). ���O:�R�A��k�W
	 *  
	 *  	  (3). ���O:�����k�W
	 *  
	 * 	�غc�l�ޥή榡: 
	 *  	  (1). ClassName::new
	 *  
	 * 	�`�N:
	 *     1.Lambda�餤�եΤ�k���ѼƻP��^�������A�n�P��Ʀ���������H��k����ƦC��M��^�������O���@�P�C
	 *     2.�ݭn�եΪ��غc�l���ѼƦC��n�PFunction<T, R>�@�P�C
	 *     3.A::B�o��Ӥ@�w�n�����p
	 *     		a.Employee::getAge->(Employee��getAge())
	 *     		b.Integer::compare->(Integer��compare())
	 *     		c.System.out::println->(System����out ; out�OPrintStream���O����println()��k)
	 *     		d.(���I)A::B �o��Ӥ@�w�i�HA.B()�A�]�N�O�@�w�O�e�̪���k�α`�ơC
	 */
	@Test
	public void Test01() {
		/*
		 * 	����::�����k�W
		 *  void accept(T t) <-> void println(String x)  : Lambda�饲���ѼƼƶq�B���A�A�^�ǭȫ��A���@�ˡA�~��� ::�C
		 * 	��k�ޥΪ��k���p�G�O��k�N���Υ[()�C
		 */
		PrintStream ps1 = System.out;
		Consumer<String> con = (x)->ps1.println(x);
		
		//�g�k�@: ����::�����k�W (ps::println)  ���O::�����k�W(PrintStream::println)->���OLambda�餣�i��gPrintStream.println()�A�sĶ���|���\�C
		PrintStream ps = System.out;
		Consumer<String> con1 = ps::println;
		//�g�k�G:
		Consumer<String> con2 = System.out::println;
		con2.accept("����");
		
	}
	
	@Test
	public void Test02() {
		/*
		 * 	����::�����k�W
		 *  void accept(T t) <-> void println(String x)  : Lambda�饲���ѼƼƶq�B���A�A�^�ǭȫ��A���@�ˡA�~��� ::�C 
		 */
		Employee emp = new Employee();
		Supplier<String> sup = () -> emp.getName();
		String str = sup.get();
		System.out.println(str);
		
		//����::�����k�W (emp::getAge)  ���O::�����k�W(Supplier::getAge)->���OLambda�餣�i��gEmployee.getName()�A�sĶ���|���\�C
		Supplier<Integer> sup2 = emp::getAge;
		Integer num = sup2.get();
		System.out.println(num);
	}
	
	@Test
	public void Test03() {
		/*
		 *	 ���O::�R�A��k�W 
		 *  Integer : public static int compare(int x, int y)
		 */
		
		Comparator<Integer> com = (x,y) -> Integer.compare(10, 9);
		
		Comparator<Integer> com1 = Integer::compare;
	}
	
	@Test
	public void Test04() {
		/*
		 * 	���O::�����k�W 
		 *  Integer : public static int compare(int x, int y)
		 * 
		 * �W�d:(x,y) -> x.equals(y) �y(x,y)-> x�@�w�n�O�եΪ̡Ay�@�w�n�O��k���Ѽơz�A�~��ϥΤ�k�ޥΡC
		 */
		
		BiPredicate<String,String> bp = (x,y) -> x.equals(y);
		
		BiPredicate<String,String> bp2 = String::equals;
	}
	
	@Test
	public void Test05() {
		/*
		 * ClassName(���O�W��)::new
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
		
		//	��Ҥ@�U�o��եάOEmployee�����ӫغc�l?�O�X�ӰѼƪ�? 
		//	Ans:���M��public interface Function<T, R>�̭��O�@�ӰѼơA�ҥH�O��Employee���@�ӰѼƫغc�l�C
		Function<Integer,Employee> fun2 = Employee::new;
		Employee emp = fun2.apply(101);
		System.out.println(emp);
		
		//	��Ҥ@�U�U��{������sĶ���|�L? �]��Employee�غc�l�S����ӰѼƪ��غc�l�C�`�N:<Integer,Integer,Employee>:�e����ӬO�ѼơA�᭱�O�^�ǭȡCBiFunction<T, U, R>
		//	BiFunction<Integer,Integer,Employee> bf = Employee::new;
	}
	
	
}
