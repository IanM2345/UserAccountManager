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
import java.io.InputStream;
import java.net.URL;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.ArrayList;
import java.util.Scanner;

public class FileTest {

	public static void main(String[] args) {
		// Define the filename
		String filename= "C:\\Users\\user\\.eclipse\\LabSix_3132394\\src\\griffith\\RandomWordsFile.txt";
		// Sort the file
		String sortedFilename = sortFile(filename);
		System.out.println("Sorted file: " +sortedFilename);
		
		// Input for download method
		Scanner input = new Scanner(System.in);
		System.out.println("Enter the desired url");
		String url= input.nextLine();
		System.out.println("Enter the fileName (including extension)");
		String fileName= input.nextLine();
		System.out.println("Enter the location to save the file:");
		String location= input.nextLine();
	}
	
	// Method to sort a file
	public static String sortFile(String filename) {
		ArrayList<String> values= new ArrayList<>();
		try {
			// Open file for reading
			BufferedReader reader = new BufferedReader (new FileReader(filename));
			String line;
			// Read lines from file
			while ((line = reader.readLine())!= null) {
				// Add each line to ArrayList
				values.add(line);
			}
			reader.close(); // Close file
			
			// Sort values using bubble sort
			bubbleSort(values);
			
			// Get sorted filename
			String sortedFilename = getSortedFilename(filename);
			// Open file for writing
			BufferedWriter writer = new BufferedWriter (new FileWriter(sortedFilename));
			
			// Write sorted values to file
			for (String value : values) {
				writer.write(value + "\n");
			}
			writer.close(); // Close file
			
			return sortedFilename;
		} catch (IOException e) {
			// Print stack trace if an IOException occurs
			e.printStackTrace();
			return null;
		}
	}
	
	// Bubble sort implementation
	public static void bubbleSort (ArrayList<String> values) {
		int n = values.size();
		for (int i=0; i<n-1; i++) {
			for (int j=0; j<n-1-i; j++) {
				if (values.get(j).compareTo(values.get(j+1))>0) {
					// Swap elements if they are out of order
					String tool= values.get(j);
					values.set(j,  values.get(j+1));
					values.set(j+1, tool);
				}
			}
		}
	}
	
	// Method to generate sorted filename
	public static String getSortedFilename(String filename) {
		int Index= filename.lastIndexOf('.');
		// Extract file name without extension
		String nameWithNOExtention = filename.substring(0, Index);
		// Extract file extension
		String extension = filename.substring(Index);
		// Append 'sorted' to filename
		return  nameWithNOExtention + "sorted" +extension;
	}
	
	// Method to download a file from a URL
	public static void downloads(String url, String fileName, String location) {
		try {
			// Check if both URL and fileName have extensions
			if (!hasExtension(url) || !hasExtension(fileName)) {
				// Print error message if extensions are missing
				System.out.println("Both URL and fileName must have extensions.");
				return;
			}
			
			// Check if extensions match
			String urlExtension = getFileExtension(url);
			String fileExtension = getFileExtension(fileName);
			if (!urlExtension.equals(fileExtension)) {
				// Print error message if extensions don't match
				System.out.println("Extensions of URL and fileName do not match.");
				return;
			}
			
			// Create output directory if it doesn't exist
			File directory = new File(location);
			if (!directory.exists()) {
				directory.mkdirs();
			}
			
			// Download file from URL
			InputStream in = new URL(url).openStream();
			Files.copy(in,  Paths.get(location, fileName));
			System.out.println("File downloaded successfully.");
		} catch (IOException e) {
			// Print stack trace if an IOException occurs
			e.printStackTrace();
		}
	}
	
	// Check if a filename has an extension
	private static boolean hasExtension(String fileName) {
		// Check if last index of '.' exists in the filename
		return fileName.lastIndexOf('.') != -1;
	}
	
	// Get the extension of a filename
	private static String getFileExtension(String fileName) {
		int Index= fileName.lastIndexOf('.');
		// Extract file extension
		return fileName.substring(Index);
	}
}

