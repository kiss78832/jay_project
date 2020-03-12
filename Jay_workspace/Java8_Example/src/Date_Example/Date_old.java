package Date_Example;

import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
 *	傳統Date可以重複更改同一個值，會有執行緒安全問題，執行後會報錯。
 *	Java8 Date 時間設置不可變，就像String類別一樣不可以變，會一直創造新的String。
 */
public class Date_old {
	
	public static void main(String[] args) throws Exception {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		
		Callable<Date> task = new Callable<Date>() {
			@Override
			public Date call() throws Exception {
				return sdf.parse("20200312");
			}
		};
		
		//	利用10條執行緒去跑 task 設定日期格式
		ExecutorService pool = Executors.newFixedThreadPool(10);
		List<Future<Date>> results = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for(Future<Date> future : results) {
			System.out.println(future.get());
		}
	}
}
