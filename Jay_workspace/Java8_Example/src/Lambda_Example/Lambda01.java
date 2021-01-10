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
 * Java8 ���m�|�j�֤ߤ��� : 
 * 		 	Consumer<T> :���O������  
 * 				void accept(T t);
 * 
 * 			Supplier<T> :�ѵ�������
 * 				T get();
 * 
 * 			Function<T,R>:��ƫ�����
 * 				R apply(T t);
 * 
 * 			Predicate<T> :�_��������
 * 				boolean test(T t);
 * 
 * 	��L����:
 * 			BiFunction<T,U,R> �Ѽ� :T�BU  ��^�� :R
 *			BiConsumer<T,U> �Ѽ� :T�BU  ��^��:void
 *			...	(�٦��\�h�����i�H�ŦX�h�Ѽƪ��榡�A�i�H�W���d��)
 * 
 * Lambda ���� : ��F�ѼƦC��C
 * Lambda �k�� : ��F�n���檺�\��A�]�i�H����@���覡�A��Lambda��C
 * 
 */

public class Lambda01 {
	List<Employee> list = Arrays.asList(
			new Employee("����1",60,1000),
			new Employee("����2",50,2000),
			new Employee("����3",40,3000),
			new Employee("����4",30,4000),
			new Employee("����5",20,5000),
			new Employee("����6",19,6000),
			new Employee("����7",18,7000),
			new Employee("����8",17,8000)
			);
	
	@Test
	public void test01() {
		/*
		 * Consumer ��k  void accept(T t) �y�@�ӰѼƨõL��^�ȡC�z
		 * (m) -> �N���@�ӰѼơA�Y�u���@�ذѼơA���]�i�H���άA���A�ߺD���٬O�g����n�C
		 * 
		 * 	�y�{:10000 -> accept(10000) -> Lambda�k����@Consumer�����U��void accept(T t)���e�A
		 * 	�]�i�H���o��accept(x)����x�ѼơC 
		 * 
		 *  
		 */
		happy(10000,(m)->System.out.println("Consumer test �ǰe�Ѽ�:" + m + "��"));
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
		 * 	���@�ӰѼơA�æ���^�ȡC
		 *  Supplier<T>:��ƫ����� �C ��k :T get();
		 * 
		 * 	�y�{:getNumList(10,() -> (int)(Math.random() * 100)) �ǰѼ�10�i�h�A��@Supplier[(int)(Math.random() * 100)]�æ^�Ǥ@��Integer�����A 
		 * 		�ҥH�~���n�ӱ�Integer n = supplier.get()�A��test01()�ۤ񪺸ܡAtest01()�|�����L�X�ӡA
		 *      �AgetNumList�^�Ǥ@��List�A�ҥH�Τ@��List���_�ӡA�b��foreach�L�X�ӡC
		 *
		 * */
		List<Integer> numList = getNumList(10,() -> (int)(Math.random() * 100));
		
		for(Integer num : numList) {
			System.out.println(num);
		}
	}
	
	//	�ݨD:�����H���ơA��ilist���X�̭��C
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
		 *	 ���@�ӰѼơA�æ���^�ȡC
		 *   Function<T,R>:��ƫ������C ��k :R apply(T t);
		 *   �y�{:��"\t\t\t\t Supplier test    "�r��ᵹfun.apply(str)�ߧY�h��@(str) -> str.trim()�A�^�ǭ����ӴN�O�̷�(str) -> str.trim()�Ӥ��O�^��Function�C
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
		  * Predicate<T> :�_��������     ��k:boolean test(T t);
		  * 
		  */
		List<String> list = Arrays.asList("���� ","Predicate test ","�_�������� ","�^�� boolean ");
		List<String> strList = filterStr(list,(x)->x.length()>5);
		
		for(String str : strList) {
			
			System.out.print(str);
		}
	}
	
	//	�N�������󪺻ݨD�r��A��i���X���C
	public List<String> filterStr(List<String> list,Predicate<String> predicate){
		List<String> strList = new ArrayList<>();
		
		for(String str : list) {
			//predicate.test(str)�����h��@x.length()>5�A�M��|�^�Ǥ@��boolean��
			if(predicate.test(str)) {
				//���פj��5���~�|List
				strList.add(str);
			}
		}
		return strList;
	}
	
	@Test
	public void test05() {
		ArrayList<Integer> numbers = new ArrayList<>(Arrays.asList(1,2,3,4,5,6));
		Spliterator<Integer> numbers1 = numbers.spliterator();

		numbers1.tryAdvance(e -> System.out.println(e + "�C")); // 1
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
