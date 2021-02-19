import java.math.BigInteger;

/**
 * This program generates a factorial with parallel processing!
 * @author Will Brown
 * @version 2.0.0-alpha
 */

public class Factorial {
	
	private BigInteger result;
	private int number, threadCount;
	
	/**
	 * Empty-argument constructor that sets everything to 0 to denote that nothing has happened.
	 */
	public Factorial() {
		result = BigInteger.ZERO;
		setNumber(0);
		setThreadCount(0);
	}
	
	/**
	 * Constructor that takes an input to calculate the factorial for.
	 * Automatically sets the number of threads based on system specifications.
	 * @param number the input number
	 */
	public Factorial(int number) {
		setNumber(number);
		setThreadCount(0);
		genFactorial();
	}
	
	/**
	 * Constructor that takes an input to calculate the factorial for.
	 * Sets the number of threads based on user parameters.
	 * @param number the input number
	 */
	public Factorial(int number, int threadCount) {
		setNumber(number);
		setThreadCount(threadCount);
		genFactorial();
	}

	/**
	 * Sets up the threads and returns the result once everything is done.
	 * @return The resulting factorial.
	 */
	public void genFactorial() {
		result = BigInteger.ONE;
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
	
	/**
	 * Sets up the threads and returns the result once everything is done.
	 * @param number input to calculate the factorial of
	 * @return The resulting factorial.
	 */
	public void genFactorial(int number) {
		setNumber(number);
		genFactorial();
	}
	
	/**
	 * Sets up the threads and returns the result once everything is done.
	 * @param number input to calculate the factorial of
	 * @param threadCount total number of threads
	 * @return The resulting factorial.
	 */
	public void genFactorial(int number, int threadCount) {
		setNumber(number);
		setThreadCount(threadCount);
		genFactorial();
	}
	
	/**
	 * @return the result
	 */
	public BigInteger getResult() {
		return result;
	}

	/**
	 * @return the number
	 */
	public int getNumber() {
		return number;
	}

	/**
	 * @param number the number to set
	 */
	public void setNumber(int number) {
		this.number = number < 0 ? 0 : number;
	}

	/**
	 * @return the threadCount
	 */
	public int getThreadCount() {
		return threadCount;
	}

	/**
	 * @param threadCount the threadCount to set
	 */
	public void setThreadCount(int threadCount) {
		this.threadCount = threadCount < 0 ? 0 : threadCount;
	}
	
}
