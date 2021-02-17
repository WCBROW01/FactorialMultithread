import java.math.BigInteger;

/**
 * The class that each thread uses to calculate its designated section of the factorial.
 * @author Will Brown
 * @version 1.0
 */

public class FactThread extends Thread {

	private int start, end;

	/**
	 * Determines the start and end points of the factorial for this specific thread.
	 * @param threadNum the number designated to the current thread 
	 * @param threadCount total number of threads
	 * @param number input to calculate the factorial of
	 */
	public FactThread(int threadNum, int threadCount, int number) {
		/*
		 *  Store the start of the section to calculate as an integer (keeping this as a float could cause problems)
		 *  by multiplying the fraction of the current thread and total number of threads by the factorial we want.
		 *  We add 1 to this value because we don't want the calculations of any threads to overlap.
		 */
		start = (int) ((double) threadNum/threadCount * number) + 1;

		/*
		 *  Store the end of the section to calculate as an integer (keeping this as a float could cause problems)
		 *  by multiplying the fraction of the next thread and total number of threads by the factorial we want.
		 *  If we're on the last thread, the end is just the end of the factorial.
		 */
		end = (int) ((double) (threadNum+1)/threadCount * number);
	}

	/**
	 * Generate the appropriate section of the factorial and send it back to the main thread.
	 */
	public void run() {
		BigInteger section = BigInteger.ONE;
		
		for (int count = start; count <= end; count++) {
			section = section.multiply(BigInteger.valueOf(count));
		}
		
		Main.createResult(section);
	}
}