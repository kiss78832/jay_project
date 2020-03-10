package Steam_Example;

import java.util.Arrays;
import java.util.List;
import java.util.stream.Stream;

import org.junit.Test;

import Lambda_Example.Employee;

public class Stream01 {
	/*
	 *  �@�BStream���T�Ӿާ@�B�J:
	 * 		(1).�Ы�Stream (2).�����ާ@ (3).�פ�ާ@
	 *  �G�B�z��P����
	 *  	filter -> ����Lambda�A�qstream���L�o�Y�Ǥ����C
	 *  	limit -> �I�_�y�A���w�Q�ާ@���������W�L�]�w���ƶq�C
	 *  	skip(n) -> ���L�����A�i�H���Ln�Ӥ�����A����A�Y��������n�ӡA�|��^�@�Ӫ�stream�A�Plimit�i�H���۷f�t�C
	 *  	distenct -> �z��A�q�Lstream�Ҳ��ͪ����� hashCode()�M equals()�h�����Ƥ����C
	 *  �T�B
	 *  
	 *  �`�N:
	 *  	1.���������N�@�ΡA�����浹Stream API�����C�~�����N:�ۤv�g�@��Iterator...
	 *  	2.
	 *  	3.
	 *  	4.
	 *  	5.
	 */
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
		//�����ާ@
		Stream<Employee> stream = list.stream()
								  .filter((e) -> {
									System.out.println("Stream API Test �����ާ@");
									return e.getAge() > 35;
								  });
		
		//�פ�ާ@   �`�N:�Y�S���פ�ާ@�A�N���stream�����|����A"Stream API Test �����ާ@"�����|����C
		stream.forEach(System.out::println);
	}
	
	@Test
	public void Test02() {
		list.stream()
			.filter((e)->e.getSalary()>3000)
			.limit(3)
			.forEach(System.out::println);
	}
	
	
	 
	
}
