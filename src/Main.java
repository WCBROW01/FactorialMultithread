/*
 * Author: Will Brown
 * This program generates a factorial with parallel processing!
 */

import java.math.BigInteger;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Main {
	
	// This is global because it is shared with createResult.
	private static BigInteger result;
	
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		int number, threadCount;
		char again = 'y';
		while (again == 'y' || again == 'Y') {
			try {
				System.out.print("How many threads do you want to use?: ");
				threadCount = input.nextInt();
				
				// Clear anything left in the buffer
				input.nextLine();
				
				System.out.print("Enter number to complete factorial: ");
				number = input.nextInt();
				
				/*
				 *  If we have a greater quantity of threads than numbers to multiply, we can't even use all of the threads for this
				 *  because the program will (predictably) crash. The number is small enough at this point that initializing multiple threads
				 *  will actually take longer than calculating the factorial, so why bother?
				 */
				threadCount = number < threadCount || threadCount < 1? (int) 1 : threadCount;
				
				System.out.println("Factorial of " + number + " is " + factorial(threadCount, number));
			
			} catch (InputMismatchException e) {
				System.out.println("Invalid number!");
				
				// Clear anything left in the buffer
				input.nextLine();
			} catch (InterruptedException e) {
				System.out.println("Something was interrupted. This shouldn't happen.");
				e.printStackTrace();
			}
			
			System.out.print("Want to continue? (y/n): ");
			again = input.next().strip().charAt(0);
		}
		
		input.close();
	}
	
	// args: Total number of threads, factorial to calculate
	private static BigInteger factorial(int threadCount, int number) throws InterruptedException {
		int threadNum;
		
		result = BigInteger.ONE;

		// Create an array the size of the thread count.
		FactThread[] factThread = new FactThread[threadCount];
		
		// Start each thread.
		for (threadNum = 0; threadNum < threadCount; threadNum++) {
			factThread[threadNum] = new FactThread(threadNum, threadCount, number);
			factThread[threadNum].start();
		}
		
		/*
		 *  Stalls the program, but using the sleep method allows for any thread to call createResult on demand.
		 *  It will basically keep the program from returning the result until every thread is finished.
		 *  I was previously just using the join method for each thread and multiplying the results sequentially,
		 *  but that added a ton of overhead because there was a lot more waiting around.
		 *  This allows for each thread to just send it's result once it finishes because the order doesn't actually matter and
		 *  as a result, leaves a lot less downtime for the CPU. 
		 */
		while(Thread.activeCount() > 1)
			Thread.sleep(0);
		
		return result;
	}
	
	// Synchronized method that multiplies the result of the thread into the full factorial.
	public static synchronized void createResult(BigInteger section) {
		result = result.multiply(section);
	}
}
