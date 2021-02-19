import java.util.InputMismatchException;
import java.util.Scanner;

/**
 * This program generates a factorial with parallel processing!
 * @author Will Brown
 * @version 2.0.0-alpha
 */

public class TestApplication {
	
	private static int number, threadCount;
	
	/**
	 * Main method. Handles parameters.
	 * @param args
	 */
	public static void main(String[] args) {
		if (args.length == 0 || args[0].equals("-i") || args[0].equals("--interactive")) {
			interactive();
		} else if (args[0].equals("-h") || args[0].equals("--help")) {
			printHelp();
		} else {
			threadCount = 0; // default in case no value is entered
			
			// Iterate through arguments
			for (int argument = 0; argument < args.length; argument++) {
				if (args[argument].equals("-n") || args[argument].equals("--number")) {
					number = Integer.parseInt(args[argument + 1]);
				} else if (args[argument].equals("-t") || args[argument].equals("--threads")) {
					threadCount = Integer.parseInt(args[argument] + 1);
				}
			}
			
			printFactorial();
		}
	}
	
	/**
	 * Interactive portion of the program. Runs if you enter no arguments or enter "-i" or "--interactive".
	 */
	private static void interactive() {
		Scanner input = new Scanner(System.in);
		char again = 'y';
		
		while (again == 'y' || again == 'Y') {
			try {
				System.out.print("How many threads do you want to use? (0 to automatically allocate): ");
				threadCount = input.nextInt();
				
				// Clear anything left in the buffer
				input.nextLine();
				
				System.out.print("Enter number to complete factorial: ");
				number = input.nextInt();
				
				printFactorial();
			
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
	
	/**
	 * Creates a Factorial and prints it to the console.
	 */
	private static void printFactorial() {
		Factorial myFactorial = new Factorial(number, threadCount);
		System.out.println("Factorial of " + number + " is " + myFactorial.getResult());
	}
	
	/**
	 * Prints help information.
	 */
	private static void printHelp() {
		// TODO: Create help text
		System.out.println("Unimplemented feature: help");
	}
}
