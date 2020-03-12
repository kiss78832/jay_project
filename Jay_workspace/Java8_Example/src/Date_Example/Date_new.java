package Date_Example;

import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 *	Java8 把Date API 重新整理過，每個日期都不能修改，都會創建一個新的實體，就會防止執行緒安全問題。 
 *		(a).可對照 Date_old
 */
public class Date_new {
	
	public static void main(String[] args) throws Exception {
		DateTimeFormatter format = DateTimeFormatter.ofPattern("yyyyMMdd");
		
		Callable<LocalDate> task = new Callable<LocalDate>() {

			@Override
			public LocalDate call() throws Exception {
				return LocalDate.parse("20200312",format);
			}
		};
		
		//	利用10條執行緒去跑 task 設定日期格式
		ExecutorService pool = Executors.newFixedThreadPool(10);
		List<Future<LocalDate>> results = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for(Future<LocalDate> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();//關閉執行緒
	}
}
