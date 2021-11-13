# Covid Chat Bot

This simple chat bot built using Java is used to manage covid test results.

## Description

This chat bot is used to manage test result entries with 4 simple commands in a conversational manner.
1. add entry
2. remove entry
3. submit test result
4. view entry

These entries are stored locally in a txt file named *TestHistory.txt*, which will be created if not found. 

## Formatting

![TestHistoryFile](https://user-images.githubusercontent.com/18254925/141606801-e84bea02-b79d-413c-b22e-12e98e2bda05.png)

The test entries are stored within the text file in the following string:
LASTNAME;FIRSTNAME;BIRTHDATE;POSTCODE;PHONENUMBER;DATETAKEN;TESTRESULT

Where *DATETAKEN* is the date when the covid test was administered.
The test result will be a single character: P for positive, N for negative, U for unknown.

## Getting Started

### Dependencies

[Java Runtime Environment](https://www.java.com/en/download/manual.jsp) is required to run this program at the very least. 
If code needs to be altered, then [Java Development Kit](https://www.oracle.com/java/technologies/downloads/) would be needed to recompile the program.

### Installation

TestHistory.txt must be in the same directory as the covidchatbot java files. The txt file included in the repository has around ten dummy entries. If the txt file is deleted, a new blank file will be created upon next program launch.

### Usage

Open an instance of command prompt and navigate to the directory where our program is installed. Run the program using:
> java covidchatbot

It should then return the following:

![image](https://user-images.githubusercontent.com/18254925/141607880-0a13afee-861e-4bfa-b5ea-d8d23659e337.png)


Functions themselves are quite intuitive, and within those functions there will be prompts such as the following:

![image](https://user-images.githubusercontent.com/18254925/141607953-13714b69-2058-4765-86c8-0cc77bbd94cf.png)


Upon exiting the program it should display the number of entries added/removed, as well as the total number of entries.

![image](https://user-images.githubusercontent.com/18254925/141607888-1e99fd4e-4c81-4d75-9320-f6b3df5c7c7b.png)

## Notes

1. Input is case insensitive, but other aspects are checked for validity where applicable (numeric, length, date)
2. Duplicate entries are only checked when trying to add a new entry within the program, would recommend against editing txt file manually.
3. When attempting to locate an entry, it follows this order: birthdate->datetaken->surname->firstname->phonenumber. If there are still multiple entries with the same information then the message *SINGULAR ENTRY NOT FOUND...* is printed. This is likely caused by a duplicate entry. Did not ask for further information to search on as it is not logical for a human to have the same birthdate,surname,firstname,phonenumber as someone else who got a covid test on that same day. Also doubt a person would be getting multiple covid tests a day, as the results would usually take over 2 days anyways.
4. This program only uses the actual txt file twice, once when the program is first launched, and once when the program is terminated. This may lead to changes being lost if program is quit unexpectedly.

## Author

Justin Harding  hardingjs@yahoo.ca

[@jhar5338](https://github.com/jhar5338)
