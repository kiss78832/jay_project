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
 *	Java8 ��Date API ���s��z�L�A�C�Ӥ��������ק�A���|�Ыؤ@�ӷs������A�N�|���������w�����D�C 
 *		(a).�i��� Date_old
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
		
		//	�Q��10��������h�] task �]�w����榡
		ExecutorService pool = Executors.newFixedThreadPool(10);
		List<Future<LocalDate>> results = new ArrayList<>();
		
		for (int i = 0; i < 10; i++) {
			results.add(pool.submit(task));
		}
		
		for(Future<LocalDate> future : results) {
			System.out.println(future.get());
		}
		
		pool.shutdown();//���������
	}
}
