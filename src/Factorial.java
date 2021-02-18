import java.math.BigInteger;

/**
 * This program generates a factorial with parallel processing!
 * @author Will Brown
 * @version 2.0.0-alpha
 */

public class Factorial {
	
	private BigInteger result;
	
	/**
	 * Constructor that takes an input to calculate the factorial for.
	 * Automatically sets the number of threads based on system specifications.
	 * @param number the input number
	 * @throws InterruptedException
	 */
	public Factorial(int number) {
		result = BigInteger.ONE;
		genFactorial(number, 0);
	}
	
	public Factorial(int number, int threadCount) {
		result = BigInteger.ONE;
		genFactorial(number, threadCount);
	}

	/**
	 * Sets up the threads and returns the result once everything is done.
	 * @param threadCount total number of threads
	 * @param number input to calculate the factorial of
	 * @return The resulting factorial.
	 * @throws InterruptedException
	 */
	private void genFactorial(int number, int threadCount) {
		int threadNum;
		boolean finished = false;

		// If the input is less than 1 (either 0 or a negative number), grab the number of available processors and use that.
		threadCount = threadCount < 1 ? Runtime.getRuntime().availableProcessors() : threadCount;
		
		// If we have a greater quantity of threads than numbers to multiply, fall back to 1 thread.
		threadCount = number < threadCount ? 1 : threadCount;
		
		// Create an array the size of the thread count.
		FactThread[] factThread = new FactThread[threadCount];
		
		// Start each thread.
		for (threadNum = 0; threadNum < threadCount; threadNum++) {
			factThread[threadNum] = new FactThread(threadNum, threadCount, number);
			factThread[threadNum].start();
		}
		
		// Poll each thread to see if it is finished calculating and I need to multiply it to the output.
		while (!finished) {
			finished = true;
			
			for (threadNum = 0; threadNum < threadCount; threadNum++) {
				if (!factThread[threadNum].isSectionReturned()) {
					finished = false;
					
					if (!factThread[threadNum].isAlive()) {
						result = result.multiply(factThread[threadNum].getSection());
					}
				}
			}
		}
	}
	
	public BigInteger getResult() {
		return result;
	}
	
}
