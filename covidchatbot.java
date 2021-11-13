import java.io.*;
import java.util.*;
import java.text.*;

;/*
 * Format for storing entries is Surname;FirstName;Birthdate;Postcode;PhoneNumber;DateOfTest;TestResult
 * Test result will be P for positive, N for negative, U for unknown
 */

public class covidchatbot {
	
	private static File testHistoryFile;
	private static ArrayList<String[]> entries = new ArrayList<String[]>(); 
	private static Scanner textInput;
	
	private static int entriesAdded;
	private static int entriesRemoved;
	private static int entriesCounter;
	
	private static void addEntry() {
		String inputString = null;
		String[] newEntry = new String[7];
		SimpleDateFormat format = new SimpleDateFormat("ddMMyyyy");
		format.setLenient(false);
		
		// loop to get surname
		while (true) {
			System.out.println("ENTER SURNAME OR 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (inputString.length() > 0 && inputString.length() < 32) {
				inputString = inputString.toUpperCase();
				newEntry[0] = inputString;
				break;
			}	else {
				System.out.println("ERROR WITH LENGTH OF INPUT");
			}
		}
		
		// loop to get first name
		while (true) {
			System.out.println("ENTER FIRST NAME OR 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (inputString.length() > 0 && inputString.length() < 32) {
				inputString = inputString.toUpperCase();
				newEntry[1] = inputString;
				break;
			}	else {
				System.out.println("ERROR WITH LENGTH OF INPUT");
			}
		}
		
		// loop to get birth date
		while (true) {
			System.out.println("ENTER BIRTH DATE IN FORMAT DDMMYYYY OR 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (isNumeric(inputString)) {
				if (inputString.length()==8) {
					// check if given birth date is a valid date
					try {
						format.parse(inputString);	
					}	catch (ParseException e) {
						System.out.println("INVALID BIRTHDATE GIVEN");
						continue;
					}
					newEntry[2] = inputString;
					break;
				}	else {
					System.out.println("INVALID LENGTH OF INPUT");
				}
			}
		}
		
		// loop to get post code
		while (true) {
			System.out.println("ENTER POSTCODE IN FORMAT XXXX OR 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (isNumeric(inputString)) {
				if (inputString.length()==4) {
					newEntry[3] = inputString;
					break;
				}	else {
					System.out.println("INVALID LENGTH OF INPUT");
				}
			}
		}
		
		// loop to get phone number
		while (true) {
			System.out.println("ENTER PHONE NUMBER IN FORMAT XXXXXXXXXX OR 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (isNumeric(inputString)) {
				if (inputString.length()==10) {
					newEntry[4] = inputString;
					break;
				}	else {
					System.out.println("INVALID LENGTH OF INPUT");
				}
			}
		}
		
		// loop to get date of test
		while (true) {
			System.out.println("ENTER DATE OF TEST IN FORMAT DDMMYYYY OR 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (isNumeric(inputString)) {
				if (inputString.length()==8) {
					// check if given birth date is a valid date
					try {
						format.parse(inputString);	
					}	catch (ParseException e) {
						System.out.println("INVALID BIRTHDATE GIVEN");
						continue;
					}
					newEntry[5] = inputString;
					break;
				}	else {
					System.out.println("INVALID LENGTH OF INPUT");
				}
			}
		}
		
		// loop to get test result
		while (true) {
			System.out.println("ENTER TEST RESULT, P FOR POSITIVE, N FOR NEGATIVE, U FOR UNKNOWN OR TYPE 0 TO CANCEL");
			inputString = textInput.nextLine();
			if (inputString.equals("0")) 
				return;
			
			if (inputString.length() == 1) {
				inputString = inputString.toUpperCase();
				if (inputString.equals("P")||inputString.equals("N")||inputString.equals("U")) {
					newEntry[6] = inputString;
					break;
				}	else {
					System.out.println("INVALID INPUT GIVEN");
				}
			}	else {
				System.out.println("INVALID LENGTH OF INPUT");
			}
		}
		
		// confirm entry before adding
		while (true) {
			System.out.println("IS THE FOLLOWING CORRECT? Y/N");
			System.out.println(Arrays.toString(newEntry));
			inputString = textInput.nextLine();
			inputString = inputString.toUpperCase();
			if (inputString.equals("Y")) {
				// check if entry with identical details already found
				for (int i = 0; i < entries.size(); i++) {
					if (newEntry.equals(entries.get(i))) {
						System.out.println("IDENTICAL ENTRY ALREADY FOUND");
						return;
					}
				}
				entries.add(newEntry);
				entriesAdded++;
				entriesCounter++;
				System.out.println("ENTRY ADDED SUCCESSFULLY");
				break;
			}	else if (inputString.equals("N")) {
				break;
			}	else {
				System.out.println("INVALID RESPONSE");
			}
		}
	}
	
	private static void removeEntry() {
		String[] result = entryFinder();
		
		if (result != null) {
			
			// iterate through entries to find it then remove it while updating counters
			for (int i = 0; i < entries.size(); i++) {
				if (result.equals(entries.get(i))) {
					String[] temp = entries.get(i);
					System.out.println("ENTRY FOR " + temp[0] + " " + temp[1] + " ON " + temp[5] + " REMOVED");
					entriesRemoved++;
					entriesCounter--;
					entries.remove(i);
					return;
				}
			}
			System.out.println("ISSUE REMOVING ENTRY");
			return;
		}
	}
	
	// method for updating test result field
	private static void submitResult() {
		String[] result = entryFinder();
		String inputString;
		
		if (result != null) {
			while (true) {
				System.out.println("ENTER RESULT, P FOR POSITIVE, N FOR NEGATIVE OR 0 TO CANCEL");
				inputString = textInput.nextLine();
				inputString = inputString.toUpperCase();
				if (inputString.equals("0")) {
					return;
				}	else if (inputString.equals("P")||inputString.equals("N")) {
					// find matching entry and replace test result field
					for (int i = 0; i < entries.size(); i++) {
						if (result.equals(entries.get(i))) {
							entries.get(i)[6] = inputString;
							System.out.println(entries.get(i)[6] + " RESULT SUBMITTED FOR " + entries.get(i)[1]);
							return;
						}
					}
					System.out.println("UNABLE TO REPLACE ENTRY");
					return;
				}	else {
					System.out.println("INVALID RESPONSE");
				}
			}
		}
	}
	
	private static void viewResult() {
		String[] result = entryFinder();
		if (result != null) {
			System.out.println(Arrays.toString(result));
		}
	}
	
	// main menu of program which prompts user to make option of function they want to use, returns int value of option
	private static Integer mainMenu() {
		while (true) {
			System.out.println("TYPE 1 TO ADD NEW ENTRY, 2 TO REMOVE AN ENTRY, 3 TO SUBMIT A TEST RESULT, 4 TO VIEW TEST RESULT, 0 TO EXIT");
			String inputString = textInput.nextLine();
			if (isNumeric(inputString))
				return Integer.parseInt(inputString);
		}
	}
	
	// method used to locate an entry, returns string array of entry, otherwise null if not found or cancelled or duplicates found
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
			
			if (isNumeric(inputString)) {
				// check if input is 0 indicating cancel
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
					// if no match found then loop again and ask for birth date
					if (matches.size()==0) {
						System.out.println("NO MATCH FOUND");
						continue;
					}
					// if singular match found then search successful and return that entry
					if (matches.size()==1) 
						return matches.get(0);
					
					// otherwise multiple matches found, break loop and continue search
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
			
			// check if input was numeric, then check if 0, indicated cancel
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
					
					// iterate through existing matches list to compare with given test date
					for (int i = 0; i < matches.size(); i++) {
						try {
							entryDate = format.parse(matches.get(i)[5]);
						}	catch (ParseException e) {
							System.out.println("ERROR READING DATE FROM MATCHES");
							return null;
						}
						// if match found then add to new matches list
						if (entryDate.equals(testDate)) 
							newMatches.add(matches.get(i));
						
					}
					// if no match found continue loop, prompting user for test date once again
					if (newMatches.size()==0) {
						System.out.println("NO MATCH FOUND");
						continue;
					}
					// if singular match found then return that match
					if (newMatches.size()==1)
						return newMatches.get(0);
					
					// otherwise multiple entries found, continue search
					break;
					
				}	else {
					System.out.println("INVALID NUMBER OF CHARACTERS PLEASE TRY AGAIN");
				}
			}
		}
		
		// transfer newer list to matches list and clear newer list
		matches.clear();
		matches.addAll(newMatches);
		newMatches.clear();
		
		// next try surname
		while (true) {
			System.out.println("PLEASE ENTER SURNAME OR TYPE 0 TO CANCEL");
			inputString = textInput.nextLine();
			inputString = inputString.toUpperCase();

			// check if input was 0
			if (inputString.equals("0"))
				return null;
			
			// compare input string with surname in matches
			for (int i = 0; i < matches.size(); i++) {
				String surname = matches.get(i)[0];
				if (inputString.equals(surname)) {
					newMatches.add(matches.get(i));
				}
			}
			
			// if no matches found then continue loop and prompt for surname again
			if (newMatches.size()==0) {
				System.out.println("NO MATCH FOUND");
				continue;
			}
			
			// if singular match then search successful, return it
			if (newMatches.size()==1) 
				return newMatches.get(0);
			
			// otherwise multiple entries found, continue search
			break;
		}
		
		// transfer newer list to matches list and clear newer list
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
			
			// compare given string with first name in matches
			for (int i = 0; i < matches.size(); i++) {
				String firstName = matches.get(i)[1];
				if (inputString.equals(firstName)) {
					newMatches.add(matches.get(i));
				}
			}
			
			// if no matches found then continue loop and prompt for first name again
			if (newMatches.size()==0) {
				System.out.println("NO MATCH FOUND");
				continue;
			}
			
			// if singular match then search successful, return it
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
					
					// if no match found then continue loop and prompt for phone number once again
					if (newMatches.size()==0) {
						System.out.println("NO MATCH FOUND");
						continue;
					}
					
					// if singular match found then return it
					if (newMatches.size()==1)
						return newMatches.get(0);
					
					// strange if no singular entry found at this point, likely a duplicate
					System.out.println("SINGULAR ENTRY NOT FOUND...");
					return null;
				}	else {
					System.out.println("INVALID NUMBER OF CHARACTERS PLEASE TRY AGAIN");
				}
			}
		}
	}
	
	// method to check if string is numeric, returns a boolean value
	public static boolean isNumeric(String s) {
		try {
			Integer.parseInt(s);
		}	catch(NumberFormatException e) {
			System.out.println("UNABLE TO PARSE INTEGER");
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
		// loop for program to prompt user with main menu, using a switch where each option directs to respective method
		while (true) {
			switch (mainMenu()) {
				case 0:
					System.out.println("THANK YOU, " + entriesAdded + " ENTRIES ADDED, " + entriesRemoved + " ENTRIES REMOVED, " + entriesCounter + " ENTRIES TOTAL" );
					textInput.close();
					
					// clear file then write to file using updated entries arraylist
					new PrintWriter("TestHistory.txt").close();
					FileWriter myWriter = new FileWriter("TestHistory.txt");
					String entry;
					for (int i = 0; i < entries.size(); i++) {
						entry = Arrays.toString(entries.get(i));
						entry = entry.replace(',', ';');
						entry = entry.replaceAll(" " , "");
						entry = entry.substring(1,entry.length()-1) + "\n";
						myWriter.write(entry);
					}
					myWriter.flush();
					myWriter.close();
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