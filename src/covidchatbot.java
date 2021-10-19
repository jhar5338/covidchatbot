import java.io.*;
import java.util.*;
import java.lang.*;

;/*
 * Format for storing entries is Surname;FirstName;Birthdate;Postcode;PhoneNumber;DateOfTest;TestResult
 */

public class covidchatbot {
	
	private static File testHistoryFile;
	private static ArrayList<String[]> entries = new ArrayList<String[]>(); 
	private static Scanner textInput;
	
	private static int entriesAdded;
	private static int entriesRemoved;
	private static int entriesCounter;
	
	private static void addEntry() {
		entriesAdded++;
		entriesCounter++;
		System.out.println("ENTRY ADDED");
	}
	
	private static void removeEntry() {
		entriesRemoved++;
		entriesCounter--;
		System.out.println("ENTRY REMOVED");
	}
	private static void submitResult() {
		System.out.println("RESULT SUBMITTED");
	}
	
	private static void viewResult() {
		String result = entryFinder();
	}
	
	private static Integer mainMenu() {
		while (true) {
			System.out.println("TYPE 1 TO ADD NEW ENTRY, 2 TO REMOVE AN ENTRY, 3 TO SUBMIT A TEST RESULT, 4 TO VIEW TEST RESULT, 0 TO EXIT");
			String inputString = textInput.nextLine();
			if (isNumeric(inputString))
				return Integer.parseInt(inputString);
		}
	}
	
	// method used to locate an entry
	public static String entryFinder() {
		while (true) {
			System.out.println("PLEASE ENTER BIRTHDATE IN FORMAT DDMMYYYY OR TYPE 0 TO GO BACK");
			String inputString = textInput.nextLine();
			
			// determine if given birth date is valid
			if (isNumeric(inputString)) {
				if (Integer.parseInt(inputString)==0) {
					return null;
				}
				if (inputString.length()==8) {
					int[] birthDate = new int[inputString.length()];
					
					// 
					for (int i = 0; i < inputString.length(); i++) {
						char temp = inputString.charAt(i);
						birthDate[i] = Character.getNumericValue(temp);
					}
					int day = birthDate[0]*10 + birthDate[1];
					int month = birthDate[2]*10 + birthDate[3];
					int year = birthDate[4]*1000 + birthDate[5]*100 + birthDate[6]*10 + birthDate[7]; 
					System.out.println(day + "," + month + "," + year);
					return null;
				}	else {
					System.out.println("INVALID NUMBER OF CHARACTERS PLEASE TRY AGAIN");
				}
			}
		}
	}
	
	//method to check if string is numeric
	public static boolean isNumeric(String s) {
		try {
			Integer.parseInt(s);
		}	catch(NumberFormatException e) {
			System.out.println("INVALID RESPONSE PLEASE TRY AGAIN");
			return false;
		}
		return true;
	}
	
	public static void main (String[] args) throws IOException {
		entriesAdded = 0;
		entriesRemoved = 0;
		textInput = new Scanner(System.in);
		testHistoryFile = new File("TestHistory.txt");
		
		// create file for test history if not found otherwise read file and store entries
		if (!testHistoryFile.isFile()) {
			try {
				testHistoryFile.createNewFile();
			} catch (IOException e) {
				System.out.println("ERROR CREATING FILE");
				e.printStackTrace();
				textInput.close();
				System.exit(1);
			}
		} else {
			
			if (testHistoryFile.length() != 0) {
				BufferedReader reader = new BufferedReader(new FileReader(testHistoryFile));
				String line = null;
				
				while ((line = reader.readLine())!= null) {
					if (line.length() < 1)
						continue;
					String[] lineArray = line.split(";");
					entries.add(lineArray);
					entriesCounter++;
				}
				reader.close();
			}
		}
		System.out.println("COVID TEST RESULT BOT");
		while (true) {
			switch (mainMenu()) {
				case 0:
					System.out.println("THANK YOU, " + entriesAdded + " ENTRIES ADDED, " + entriesRemoved + " ENTRIES REMOVED, " + entriesCounter + " ENTRIES TOTAL" );
					textInput.close();
					System.exit(0);
				case 1:
					addEntry();
					break;
				case 2:
					removeEntry();
					break;
				case 3:
					submitResult();
					break;
				case 4:
					viewResult();
					break;
				default:
					System.out.println("INVALID NUMERIC RESPONSE DETECTED, PLEASE TRY AGAIN");
					break;
			}
		}
	}
}