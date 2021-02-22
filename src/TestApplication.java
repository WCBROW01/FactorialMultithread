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
			System.out.print(help());
		} else {
			boolean numberProvided = false;
			threadCount = 0; // default in case no value is entered
			
			// Iterate through arguments
			for (int argument = 0; argument < args.length; argument++) {
				if (args[argument].equals("-n") || args[argument].equals("--number")) {
					numberProvided = true;
					number = Integer.parseInt(args[argument + 1]);
				} else if (args[argument].equals("-t") || args[argument].equals("--threads")) {
					threadCount = Integer.parseInt(args[argument + 1]);
				}
			}
			
			if (numberProvided) {
				printFactorial();
			} else {
				System.out.print("You did not provide a number!");
			}
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
			
			System.out.print("\nWant to continue? (y/n): ");
			again = input.next().strip().charAt(0);
		}
		
		input.close();
	}
	
	/**
	 * Creates a Factorial and prints it to the console.
	 */
	private static void printFactorial() {
		Factorial myFactorial = new Factorial(number, threadCount);
		System.out.print("Factorial of " + myFactorial.getNumber() + " is " + myFactorial.getResult());
	}
	
	/**
	 * Prints help information.
	 */
	private static String help() {
		return "Usage: java TestApplication [OPTIONS]\n"
				+ "This program generates a factorial with parallel processing!\n\n"
				+ "Options:\n"
				+ "-i, --interactive\tStart in interactive mode. Default if no arguments are passed.\n"
				+ "-n, --number NUMBER\tInput number to calculate the factorial of.\n"
				+ "-t, --threads THREADS\tNumber of threads to calculate the factorial with. (Automatically determined if not passed)";
	}
}
