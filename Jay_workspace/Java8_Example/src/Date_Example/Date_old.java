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
 *	�ǲ�Date�i�H���Ƨ��P�@�ӭȡA�|��������w�����D�A�����|�����C
 *	Java8 Date �ɶ��]�m���i�ܡA�N��String���O�@�ˤ��i�H�ܡA�|�@���гy�s��String�C
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
		
		//	�Q��10��������h�] task �]�w����榡
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
