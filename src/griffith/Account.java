//Ian Mwai Gachoki
//3132394
//04/4/2024
package griffith;

import java.io.BufferedReader;
import java.io.BufferedWriter;
import java.io.File;
import java.io.FileReader;
import java.io.FileWriter;
import java.io.IOException;
import java.time.LocalDate;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.util.Scanner;

public class Account {
	
	private static final String AccountFile = "C:\\Users\\user\\.eclipse\\LabSix_3132394\\src\\griffith\\accounts.csv.txt";
	
	// Main method
	public static void main(String [] args) {
		Account account = new Account();
		
		// User input for signup or login
		Scanner scanner = new Scanner(System.in);
		System.out.println("Welcome!");
		System.out.println("Enter S to signup or L to login");
		String input = scanner.nextLine().toLowerCase();
		//Checks the input
		while (!(input.equals("s") || input.equals("l"))) {
		    System.out.println("Invalid input. Please enter S to signup or L to login");
		    input = scanner.nextLine().toLowerCase();
		}
		// Check user input
		if(input.equals("s")) {
			//Calls sign up method
			account.signup();
		}
		else if (input.equals("l")) {
			//Calls login method
			account.login();
		}
		
	}
	
	// Signup method
	public void signup() {
		try (Scanner userInput= new Scanner(System.in);
					BufferedWriter writer= new BufferedWriter(new FileWriter(AccountFile, true))){
			
			System.out.println("Sign Up:");
			System.out.println("Enter username: ");
			String username = userInput.nextLine();
			
			// Check if username already exists
			if (isUsernameExists(username)) {
				System.out.println("Username already exists. Please choose a different one.");
				login();
				return;
			}
			
			System.out.println("Enter password:");
			String password= userInput.nextLine();
			
			// Get current date
			String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-YY"));
			
			// Write user information to file
			writer.write(username+ ","+ password+","+ currentDate+ "\n");
			System.out.println("Signed up successfully!");
			
		}catch (IOException e) {
			// Print stack trace if IOException occurs
			e.printStackTrace();
		}
	}
	
	// Login method
	public void login() {
	    try (Scanner scan = new Scanner(System.in);
	         BufferedReader reader = new BufferedReader(new FileReader(AccountFile))) {

	        System.out.println("\nLogin:");
	        System.out.print("Enter username: ");
	        String username = scan.nextLine();

	        // Check if username exists in accounts file
	        String line;
	        boolean userFound = false;
	        while ((line = reader.readLine()) != null) {
	            String[] parts = line.split(",");
	            if (parts.length >= 3 && parts[0].equals(username)) {
	                userFound = true;
	                System.out.print("Enter password: ");
	                String password = scan.nextLine();

	                // Check if password matches
	                if (parts[1].equals(password)) {
	                    System.out.println("Welcome " + username + "!");

	                    // Calculate days since last login
	                    LocalDate lastLogin = LocalDate.parse(parts[2], DateTimeFormatter.ofPattern("dd-MM-yy"));
	                    long daysSinceLastLogin = ChronoUnit.DAYS.between(lastLogin, LocalDate.now());
	                    System.out.println("It's been " + daysSinceLastLogin + " days since you last logged in.");

	                    // Update last login date
	                    String currentDate = LocalDate.now().format(DateTimeFormatter.ofPattern("dd-MM-yy"));
	                    updateLastLogin(username, currentDate);
	                    return;
	                } else {
	                    System.out.println("Incorrect password. Please try again.");
	                    return;
	                }
	            }
	        }
	        if (!userFound) {
	            System.out.println("Username doesn't exist. Please sign up first.");
	        }

	    } catch (IOException e) {
	        // Print stack trace if IOException occurs
	        e.printStackTrace();
	    }
	}

	// Check if username exists
	private boolean isUsernameExists(String username) throws IOException {
	    try (BufferedReader reader = new BufferedReader(new FileReader(AccountFile))) {
	        // Read each line from the accounts file
	        String line;
	        while ((line = reader.readLine()) != null) {
	            // Split the line into parts using comma as delimiter
	            String[] parts = line.split(",");
	            // Check if the first part (username) matches the provided username
	            if (parts[0].equals(username)) {
	                // If the username exists, return true
	                return true;
	            }
	        }
	    }
	    // If the username does not exist, return false
	    return false;
	}


	// Update last login date
	private void updateLastLogin(String username, String currentDate) throws IOException {
	    // Create a temporary file for performing updates
	    String tempFile = "temp.csv";
	    try (
	        // Open the original accounts file for reading and the temporary file for writing
	        BufferedReader reader = new BufferedReader(new FileReader(AccountFile));
	        BufferedWriter writer = new BufferedWriter(new FileWriter(tempFile))
	    ) {
	        String line;
	        // Read each line from the original file
	        while ((line = reader.readLine()) != null) {
	            // Split the line into parts using comma as delimiter
	            String[] parts = line.split(",");
	            // Check if the username matches the username to update
	            if (parts[0].equals(username)) {
	                // If the username matches, update the line with the new login date
	                line = parts[0] + "," + parts[1] + "," + currentDate;
	            }
	            // Write the updated or unchanged line to the temporary file
	            writer.write(line + "\n");
	        }
	    } catch (IOException e) {
	        // Print stack trace if IOException occurs
	        e.printStackTrace();
	    }
	    // Create File objects for the original and temporary files
	    File file = new File(AccountFile);
	    File temp = new File(tempFile);
	    // Rename the temporary file to replace the original file
	    temp.renameTo(file);
	}

}

