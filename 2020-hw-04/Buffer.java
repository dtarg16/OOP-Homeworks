// Buffer.java

import java.util.concurrent.*;

/*
 Holds the transactions for the worker
 threads.
*/
public class Buffer  {
	public static final int SIZE = 64;
	private BlockingQueue<Transaction> bq;
	public Buffer (){
		bq = new ArrayBlockingQueue<Transaction>(SIZE);
	}
	public void enqueue(Transaction transaction) throws InterruptedException {
		bq.put(transaction);
	}
	public Transaction dequeue() throws InterruptedException {
		return bq.take();
	}

}
