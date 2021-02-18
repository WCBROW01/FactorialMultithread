import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This program generates a factorial with parallel processing!
 * @author Will Brown
 * @version 2.0.0-alpha
 */

public class TestApplication {
	
	/**
	 * Main method. Mostly for entering parameters and displaying text.
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		Factorial myFactorial;
		int number, threadCount;
		char again = 'y';
		
		while (again == 'y' || again == 'Y') {
			try {
				System.out.print("How many threads do you want to use? (0 to automatically allocate): ");
				threadCount = input.nextInt();
				
				// Clear anything left in the buffer
				input.nextLine();
				
				System.out.print("Enter number to complete factorial: ");
				number = input.nextInt();
				
				myFactorial = new Factorial(number, threadCount);
				System.out.println("Factorial of " + number + " is " + myFactorial.getResult());
			
			} catch (InputMismatchException e) {
				System.out.println("Invalid number!");
				
				// Clear anything left in the buffer
				input.nextLine();
			}
			
			System.out.print("Want to continue? (y/n): ");
			again = input.next().strip().charAt(0);
		}
		
		input.close();
	}
	
}
