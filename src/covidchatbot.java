import java.io.*;
import java.util.*;
import java.lang.*;

;/*
 * Format for storing entries is Surname;FirstName;Birthdate;Postcode;PhoneNumber;DateOfTest;TestResult
 */

public class covidchatbot {
	
	private static File TestHistoryFile;
	private static ArrayList<String[]> Entries = new ArrayList<String[]>(); 
	private static Scanner TextInput;
	
	private static int EntriesAdded;
	private static int EntriesRemoved;
	private static int EntriesCounter;
	
	private static void AddEntry() {
		EntriesAdded++;
		EntriesCounter++;
		System.out.println("ENTRY ADDED");
	}
	
	private static void RemoveEntry() {
		EntriesRemoved++;
		EntriesCounter--;
		System.out.println("ENTRY REMOVED");
	}
	private static void SubmitResult() {
		System.out.println("RESULT SUBMITTED");
	}
	
	private static void ViewResult() {
		System.out.println("PLEASE ENTER BIRTHDATE IN FORMAT DDMMYYYY");
		String InputString = TextInput.nextLine();
	}
	
	private static Integer MainMenu() {
		while (true) {
			System.out.println("TYPE 1 TO ADD NEW ENTRY, 2 TO REMOVE AN ENTRY, 3 TO SUBMIT A TEST RESULT, 4 TO VIEW TEST RESULT, 0 TO EXIT");
			String InputString = TextInput.nextLine();
			try {
				return Integer.parseInt(InputString);
			}	catch(NumberFormatException e) {
				System.out.println("NON NUMERIC RESPONSE DETECTED, PLEASE TRY AGAIN");
			}
		}
	}
	
	public static void main (String[] args) throws IOException {
		EntriesAdded = 0;
		EntriesRemoved = 0;
		TextInput = new Scanner(System.in);
		TestHistoryFile = new File("TestHistory.txt");
		
		// create file for test history if not found otherwise read file and store entries
		if (!TestHistoryFile.isFile()) {
			try {
				TestHistoryFile.createNewFile();
			} catch (IOException e) {
				System.out.println("ERROR CREATING FILE");
				e.printStackTrace();
				TextInput.close();
				System.exit(1);
			}
		} else {
			
			if (TestHistoryFile.length() != 0) {
				BufferedReader Reader = new BufferedReader(new FileReader(TestHistoryFile));
				String Line = null;
				
				while ((Line = Reader.readLine())!= null) {
					if (Line.length() < 1)
						continue;
					String[] LineArray = Line.split(";");
					Entries.add(LineArray);
					EntriesCounter++;
				}
				Reader.close();
			}
		}
		System.out.println("COVID TEST RESULT BOT");
		while (true) {
			switch (MainMenu()) {
				case 0:
					System.out.println("THANK YOU, " + EntriesAdded + " ENTRIES ADDED, " + EntriesRemoved + " ENTRIES REMOVED, " + EntriesCounter + " ENTRIES TOTAL" );
					TextInput.close();
					System.exit(0);
				case 1:
					AddEntry();
					break;
				case 2:
					RemoveEntry();
					break;
				case 3:
					SubmitResult();
					break;
				case 4:
					ViewResult();
					break;
				default:
					System.out.println("INVALID NUMERIC RESPONSE DETECTED, PLEASE TRY AGAIN");
					break;
			}
		}
	}
}