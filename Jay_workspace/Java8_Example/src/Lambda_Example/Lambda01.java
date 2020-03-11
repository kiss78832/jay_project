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
 * ��L����:
 * 			BiFunction<T,U,R> �Ѽ� :T�BU  ��^�� :R
 *			BiConsumer<T,U> �Ѽ� :T�BU  ��^��:void
 *			... (�٦��\�h�����i�H�ŦX�h�Ѽƪ��榡�A�i�H�W���d��)
 * 
 * Lambda ���� : ��F�ѼƦC��C
 * Lambda �k�� : ��F�n���檺�\��A�]�i�H����@���覡�A��Lambda��C
 * 
 * */

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
	public void Test01() {
		/*
		 * Consumer ��k  void accept(T t) �y�@�ӰѼƨõL��^�ȡC�z
		 * (m) -> �N���@�ӰѼơA�Y�u���@�ذѼơA���]�i�H���άA���A�ߺD���٬O�g����n�C
		 * 
		 * �y�{:10000 -> accept(10000) -> Lambda�k����@Consumer�����U��void accept(T t)���e�A
		 *     �]�i�H���o��accept(x)����x�ѼơC 
		 * */
		happy(10000,(m)->System.out.println("Consumer Test �ǰe�Ѽ�:" + m + "��"));
	}
	
	public void happy(double money , Consumer<Double> con) {
		con.accept(money);
	}
	
	//-------------------------------------------------------------------------------------	
	
	@Test
	public void Test02() {
		/*
		 * ���@�ӰѼơA�æ���^�ȡC
		 * Supplier<T>:��ƫ����� �C ��k :T get();
		 * 
		 * �y�{:getNumList(10,() -> (int)(Math.random() * 100)) �ǰѼ�10�i�h�A��@Supplier�A getNumList�^�Ǥ@��List�A
		 * 	       �ҥH�Τ@��List���_�ӡA�b��foreach�L�X�ӡC
		 *
		 * */
		List<Integer> numList = getNumList(10,() -> (int)(Math.random() * 100));
		
		for(Integer num : numList) {
			System.out.println(num);
		}
	}
	
	//�ݨD:�����H���ơA��ilist���X�̭��C
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
		 * ���@�ӰѼơA�æ���^�ȡC
		 * Function<T,R>:��ƫ������C ��k :R apply(T t);
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
		  * Predicate<T> :�_��������     ��k:boolean test(T t);
		  * 
		  */
		List<String> list = Arrays.asList("���� ","Predicate Test ","�_�������� ","�^�� boolean ");
		List<String> strList = filterStr(list,(x)->x.length()>5);
		
		for(String str : strList) {
			System.out.print(str);
		}
	}
	
	//�N�������󪺻ݨD�r��A��i���X���C
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
