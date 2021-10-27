import java.io.*;
import java.util.*;
import java.text.*;

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
		String[] result = entryFinder();
		if (result != null) {
			System.out.println(Arrays.toString(result));
		}
	}
	
	private static Integer mainMenu() {
		while (true) {
			System.out.println("TYPE 1 TO ADD NEW ENTRY, 2 TO REMOVE AN ENTRY, 3 TO SUBMIT A TEST RESULT, 4 TO VIEW TEST RESULT, 0 TO EXIT");
			String inputString = textInput.nextLine();
			if (isNumeric(inputString))
				return Integer.parseInt(inputString);
		}
	}
	
	// method used to locate an entry, returns index of entry
	public static String[] entryFinder() {
		
		ArrayList<String[]> matches = new ArrayList<String[]>(); // store matching results during search
		ArrayList<String[]> newMatches = new ArrayList<String[]>(); // store updated matching results
		String inputString = null;
		Date entryDate = null;
		Date birthDate = null;
		Date testDate = null;
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		format.setLenient(false);
		
		// loop for finding matching entries using birth date given
		while (true) {
			System.out.println("PLEASE ENTER BIRTHDATE IN FORMAT DDMMYYYY OR TYPE 0 CANCEL");
			inputString = textInput.nextLine();
			
			// check if input was 0
			if (isNumeric(inputString)) {
				if (inputString.equals("0"))
					return null;
				
				if (inputString.length()==8) {
					// check if given birth date is a valid date
					try {
						birthDate = format.parse(inputString);	
					}	catch (ParseException e) {
						System.out.println("INVALID BIRTHDATE GIVEN");
						continue;
					}
					
					// iterate through all entries to compare with given birth date
					for (int i = 0; i < entries.size(); i++) {
						try {
							entryDate = format.parse(entries.get(i)[2]);
						}	catch (ParseException e) {
							System.out.println("ERROR READING DATE FROM ENTRIES");
							return null;
						}
						// if birth date matches then add to matches array list
						if (entryDate.equals(birthDate)) 
							matches.add(entries.get(i));
						
					}
					// check if no match found
					if (matches.size()==0) {
						System.out.println("NO MATCH FOUND");
						continue;
					}
					// check if one match found
					if (matches.size()==1) 
						return matches.get(0);
					
					// otherwise multiple matches found, continue search
					break;
					
				}	else {
					System.out.println("INVALID NUMBER OF CHARACTERS PLEASE TRY AGAIN");
				}
			}
		}
		
		// loop for finding matching entries from prior matches using date test was taken
		while (true) {
			System.out.println("PLEASE ENTER DATE TEST WAS TAKEN IN FORMAT DDMMYYYY OR TYPE 0 TO CANCEL");
			inputString = textInput.nextLine();
			
			// check if input was numeric, then check if 0
			if (isNumeric(inputString)) {
				if (inputString.equals("0")) 
					return null;
				
				if (inputString.length()==8) {
					// check if given test date is valid
					try {
						testDate = format.parse(inputString);
					}	catch (ParseException e) {
						System.out.println("INVALID TEST DATE GIVEN");
						continue;
					}
					
					// iterate through existing matches to compare with given test date
					for (int i = 0; i < matches.size(); i++) {
						try {
							entryDate = format.parse(matches.get(i)[5]);
						}	catch (ParseException e) {
							System.out.println("ERROR READING DATE FROM MATCHES");
							return null;
						}
						// if match found then add to updated matches list
						if (entryDate.equals(testDate)) 
							newMatches.add(matches.get(i));
						
					}
					// check if no match found 
					if (newMatches.size()==0) {
						System.out.println("NO MATCH FOUND");
						continue;
					}
					// check if one match found
					if (newMatches.size()==1)
						return newMatches.get(0);
					
					// otherwise multiple entries found, continue search
					break;
					
				}	else {
					System.out.println("INVALID NUMBER OF CHARACTERS PLEASE TRY AGAIN");
				}
			}
		}
		
		// transfer updated list to matches list and clear updated list
		matches.clear();
		matches.addAll(newMatches);
		newMatches.clear();
		
		// next if single match still not found, try last name
		while (true) {
			System.out.println("PLEASE ENTER LAST NAME OR TYPE 0 TO CANCEL");
			inputString = textInput.nextLine();
			inputString = inputString.toUpperCase();

			// check if input was 0
			if (inputString.equals("0"))
				return null;
			
			// compare given string with last name in matches
			for (int i = 0; i < matches.size(); i++) {
				String lastName = matches.get(i)[0];
				if (inputString.equals(lastName)) {
					newMatches.add(matches.get(i));
				}
			}
			
			// check if 0 matches
			if (newMatches.size()==0) {
				System.out.println("NO MATCH FOUND");
				continue;
			}
			
			// check if singular match
			if (newMatches.size()==1) 
				return newMatches.get(0);
			
			// otherwise multiple entries found, continue search
			break;
		}
		
		matches.clear();
		matches.addAll(newMatches);
		newMatches.clear();
		
		// next try first name
		while (true) {
			System.out.println("PLEASE ENTER FIRST NAME OR TYPE 0 TO CANCEL");
			inputString = textInput.nextLine();
			inputString = inputString.toUpperCase();

			// check if input was 0
			if (inputString.equals("0"))
				return null;
			
			// compare given string with last name in matches
			for (int i = 0; i < matches.size(); i++) {
				String firstName = matches.get(i)[1];
				if (inputString.equals(firstName)) {
					newMatches.add(matches.get(i));
				}
			}
			
			// check if 0 matches
			if (newMatches.size()==0) {
				System.out.println("NO MATCH FOUND");
				continue;
			}
			
			// check if singular match
			if (newMatches.size()==1) 
				return newMatches.get(0);
			
			// otherwise multiple entries found, continue search
			break;
		}
		
		// finally try phone number
		while (true) {
			System.out.println("PLEASE ENTER PHONE NUMBER IN FORMAT XXXXXXXXXX OR TYPE 0 TO CANCEL");
			inputString = textInput.nextLine();
			
			// check if numeric then check if 0
			if (isNumeric(inputString)) {
				if (inputString.equals("0")) 
					return null;
				
				if (inputString.length()==10) {
					// iterate through and check if phone number matches
					for (int i = 0; i < matches.size(); i++) {
						if (inputString.equals(matches.get(i)[4])) 
							newMatches.add(matches.get(i));
					}
					
					if (newMatches.size()==0) {
						System.out.println("NO MATCH FOUND");
						continue;
					}
					if (newMatches.size()==1)
						return newMatches.get(0);
					System.out.println("SINGULAR ENTRY NOT FOUND... ");
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