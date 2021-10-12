import java.io.*;
import java.util.*;

/*
 * Format for storing entries is Surname;FirstName;Birthdate;Postcode;PhoneNumber;DateOfTest;TestResult
 */

public class covidchatbot {
	
	private static File TestHistoryFile;
	private static ArrayList<String[]> Entries = new ArrayList<String[]>(); 
	
	public static void main (String[] args) throws IOException {
		
		FileInputStream in = null;
		FileOutputStream out = null;
		TestHistoryFile = new File("TestHistory.txt");
		
		// create file for test history if not found otherwise read file and store entries
		if (!TestHistoryFile.isFile()) {
			try {
				TestHistoryFile.createNewFile();
			} catch (IOException e) {
				System.out.println("Error creating file");
				e.printStackTrace();
			}
		} else {
			
			if (TestHistoryFile.length() != 0) {
				BufferedReader Reader = new BufferedReader(new FileReader(TestHistoryFile));
				String Line = null;
				
				while ((Line = Reader.readLine())!= null) {
					String[] LineArray = Line.split(";");
					Entries.add(LineArray);
				}
			}
		}
		
	}
}