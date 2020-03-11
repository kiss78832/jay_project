package ForkJoin_Example;

import java.time.Duration;
import java.time.Instant;
import java.util.concurrent.ForkJoinPool;
import java.util.concurrent.ForkJoinTask;

import org.junit.Test;

public class ForkJoin01 {
	/*
	 * ForkJoin : 會比傳統執行緒來的更有效率。
	 * 		需求:『假設有四核心的CPU，四個執行緒(A、B、C、D)，四個任務(1,2,3,4)。』
	 * 		● 傳統: A->1;B->2;C->3;D->4 ，假設A、D執行完畢，B、C還沒，A、D會在原地不動。		
	 * 		● ForkJoin: A->1;B->2;C->3;D->4 ，假設A、D執行完畢，B、C還沒，
	 * 				    A、D會去利用work-stealing(工作竊取)去拿 B or C 部分未完成來執行，更有效利用CPU。
	 */
	
	@Test
	public void test01() {
		Instant start = Instant.now(); //java8 時間計算新方式
		
		ForkJoinPool pool = new ForkJoinPool();
		ForkJoinTask<Long> task = new ForkJoinCalculate(0,90000000000L);
		Long sum = pool.invoke(task);
		System.out.println(sum);
		
		Instant end = Instant.now();
		
		System.out.println("耗費時間為: " + Duration.between(start, end).toMillis());
		
		System.out.println("-----------------------------------");
	}
	
	@Test
	public void test02() {
		Instant start = Instant.now(); //java8 時間計算新方式
		
		long sum = 0L;
		for (long i = 0; i < 90000000000L; i++) {
			sum += i;
		}
		System.out.println(sum);
		
		Instant end = Instant.now();
		
		System.out.println("耗費時間為: " + Duration.between(start, end).toMillis());
	}
}
