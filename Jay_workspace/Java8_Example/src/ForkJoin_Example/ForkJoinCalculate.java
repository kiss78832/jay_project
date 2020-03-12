package ForkJoin_Example;

import java.util.concurrent.RecursiveTask;

/*
 * RecursiveAction (沒返回值)
 * RecursiveTask<V> (有返回值)
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {
	/*
	 * 	模擬終極密碼 ，不斷的總和除2直到猜到臨界值。
	 */
	private static final long serialVersionUID = 1L;

	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}

	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000; //設定臨界值
	
	@Override
	protected Long compute() {
		long length = end - start;
		
		if(length <= THRESHOLD) {
			long sum = 0;
			for(long i = start; i<=end; i++) {
				sum +=i;
			}
			return sum;
		}else {
			long middle = (start + end) /2;
			
			ForkJoinCalculate left = new ForkJoinCalculate(start,middle);
			left.fork(); //	拆分子任務，同時壓入執行緒隊列。
			
			ForkJoinCalculate right = new ForkJoinCalculate(start,middle);
			right.fork(); 
			
			return left.join() + right.join();
		}
	}

}
