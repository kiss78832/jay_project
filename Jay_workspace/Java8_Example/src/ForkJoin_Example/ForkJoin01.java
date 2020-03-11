package ForkJoin_Example;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.junit.Test;

public class ForkJoin01 {
	/*
	 * ForkJoin : �|��ǲΰ�����Ӫ��󦳮Ĳv�C
	 * 		�ݨD:�y���]���|�֤ߪ�CPU�A�|�Ӱ����(A�BB�BC�BD)�A�|�ӥ���(1,2,3,4)�C�z
	 * 		�� �ǲ�: A->1;B->2;C->3;D->4 �A���]A�BD���槹���AB�BC�٨S�AA�BD�|�b��a���ʡC		
	 * 		�� ForkJoin: A->1;B->2;C->3;D->4 �A���]A�BD���槹���AB�BC�٨S�A
	 * 				    A�BD�|�h�Q��work-stealing(�u�@�Ѩ�)�h�� B or C �����������Ӱ���A�󦳮ħQ��CPU�C
	 */
	
	@Test
	public void test01() {
		Instant start = Instant.now(); //java8 �ɶ��p��s�覡
		
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalculate(0,90000000000L);
		Long sum = pool.invoke(task);
		System.out.println(sum);
		
		Instant end = Instant.now();
		
		System.out.println("�ӶO�ɶ���: " + Duration.between(start, end).toMillis());
		
		System.out.println("-----------------------------------");
	}
	
	@Test
	public void test02() {
		Instant start = Instant.now(); //java8 �ɶ��p��s�覡
		
		long sum = 0L;
		for (long i = 0; i < 90000000000L; i++) {
			sum += i;
		}
		System.out.println(sum);
		
		Instant end = Instant.now();
		
		System.out.println("�ӶO�ɶ���: " + Duration.between(start, end).toMillis());
	}
}
