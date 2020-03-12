package ForkJoin_Example;

import java.util.concurrent.RecursiveTask;

/*
 * RecursiveAction (�S��^��)
 * RecursiveTask<V> (����^��)
 */
public class ForkJoinCalculate extends RecursiveTask<Long> {
	/*
	 * 	�����׷��K�X �A���_���`�M��2����q���{�ɭȡC
	 */
	private static final long serialVersionUID = 1L;

	
	public ForkJoinCalculate(long start, long end) {
		this.start = start;
		this.end = end;
	}

	private long start;
	private long end;
	
	private static final long THRESHOLD = 10000; //�]�w�{�ɭ�
	
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
			left.fork(); //	����l���ȡA�P�����J��������C�C
			
			ForkJoinCalculate right = new ForkJoinCalculate(start,middle);
			right.fork(); 
			
			return left.join() + right.join();
		}
	}

}
