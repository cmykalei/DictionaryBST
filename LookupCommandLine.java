import java.io.*;
import java.util.Scanner;
/**
 * LookupCommandLine class. 
 * 
 * LookupCommandLine is a public class that holds a scanner object, and various
 * public methods which handle and return user input from the command line.
 * 
 * This class has public methods to format Strings in DictionaryLookup style, 
 * relevant to either command line or output/information, and print them to
 * the System. 
 * 
 * This class also holds other public methods to print program information to the
 * System, such as a list of DictionaryLookup commands and welcome instructions.
 * @see #viewCommands()
 * @see #viewWelcome()
 * 
 * This class also holds the method to exit the program:
 * @see #exitLookup()
 * 
 */
public class LookupCommandLine{
	
	Scanner inputScanner;
	int maxSlots;

	/**
	 * LookupCommandLine constructor.
	 * 
	 * Constructs an object to handle user input, by taking the Scanner object
	 * passed in and setting it to this class variable for use throughout
	 * this class's methods.
	 * 
	 * @param sc Specifies the Scanner object to use for this class.
	 */
	public LookupCommandLine(Scanner sc, int max){

		inputScanner = sc;
		maxSlots = max;
	}

	/**
	 * printCommandln(String ln)
	 * 
	 * Prints the String line to the System with an arrow, to denote the text
	 * and a command-line.
	 * 
	 * @param ln Specifies the String command-line to print to the System.
	 */
	public void printCmdln(String item, String task){
		
		String out = String.format("> Enter a %s to %s", item, task);
		System.out.println();
		System.out.println(out);
		System.out.println();

	}

	/**
	 * prinInfoln(String ln)
	 * 
	 * Prints the String line to the System with an indent, to show that the line
	 * contains an output or information about a function.
	 * 
	 * @param ln Specifies the String line of output to print to the System.
	 */
	public void printInfoln(String ln){
		
		System.out.println("          " + ln);
	}


	/**
	 * viewCommands()
	 * 
	 * Prints the list of DictionaryLookup commands to the System.
	 * 
	 * This method calls the following supporting method:
	 * @see #printInfoln()
	 */
	public void viewCommands(){
    
   		printInfoln("");
   		printInfoln("(1) Search for a word");
		printInfoln("(2) Print a definition");	
		printInfoln("(3) Add a word");	
		printInfoln("(4) Remove a word");
		printInfoln("(5) Import a new file");
		printInfoln("(6) Search all dictionaries");
		printInfoln("(7) Print all dictionaries");
		printInfoln("(8) Exit");
		printInfoln("");
	}

	/**
	 * viewWelcome()
	 * 
	 * Prints the weclome instructions for DictionaryLookup to the System.
	 * 
	 * This method calls the following supporting method:
	 * @see #printInfoln()
	 */
	public void viewWelcome(){
    
    	printInfoln("");
   		printInfoln("WELCOME TO DICTIONARY-LOOKUP");
		printInfoln("");
		printInfoln("— Import, modify, and view dictionary.txt files");	
		printInfoln("— Command the Lookup by entering a number when asked");
		printInfoln("— Press return after entering text to confirm your choice");
		printInfoln("— Exiting the program will clear ALL current dictionaries");
		printInfoln("— The limit of files saved at a time is " + String.valueOf(maxSlots));
		printInfoln("— If you exceed the maximum, the oldest file will be deleted!");
		printInfoln("");
	}

	/**
	 * getNum(String task)
	 * 
	 * Gets the next int from the command line and returns if valid, by looping 
	 * until the int entered is within the specified range. Prints a message on 
	 * each iteration where the number entered isn't valid, before attempting to 
	 * get the user's input again.
	 * 
	 * This method calls the following supporting methods:
	 * @see #printInfoln()
	 * @see #printCmdln()
	 * 
	 * @param task Describes the current task as a String.
	 * @param min Specifies the valid minimum, inclusive.
	 * @param max Specifies the valid maximum, inclusive.
	 * @return the next valid int entered by the user.
	 */
	public int getNum(String task, int min, int max){

		int num = 10;
		while(true){
			printCmdln(("number between " + min + " and " + max), task);			
			if(inputScanner.hasNextInt()){
				num = inputScanner.nextInt();
			}
			if(num >= min && num <= max){
				break;
			}
			inputScanner.nextLine();
			printInfoln("The number entered wasn't in range!");		
		}
		return num;
	}

	/**
	 * getAns(String a, String b)
	 * 
	 * Gets the next String input from the command line and returns if it matches
	 * option 'a' or 'b'. If not, prints a message to the System then recursively 
	 * returns this method until a valid input can be returned. 
	 * If the user enters '9' a supporting method is called, which asks the user 
	 * to confirm their choice to close the program.
	 * 
	 * This method calls the following supporting methods:
	 * @see #printCmdln()
	 * @see #exitLookup()
	 * 
	 * @param a Specifies option 'a' for the user to select, for 'true'
	 * @param b Specifies option 'b' for the user to select, for 'false'
	 * @return 'true' if the user enters 'a' or 'false' if the user enters 'b'
	 */
	public boolean getAns(String a, String b){

		printCmdln(("choice (" + a + " or " + b + ")"), "select");

		String str = inputScanner.next();
		if(str.equalsIgnoreCase(a)){
			return true;
		}
		else if(str.equalsIgnoreCase(b)){
			return false;
		}
		else if(str.equals("9")){
			exitLookup();
		}
		return getAns(a, b);
	}

	/**
	 * getStr(String item, String task)
	 * 
	 * Gets the next String input from the command line and returns it if it's 
	 * not empty by looping until something is entered. Prints a message on 
	 * each iteration where the input is still empty, before attempting to get
	 * the user's next input again.
	 * 
     * This method calls the following supporting methods:
	 * @see #printInfoln()
	 * @see #printCmdln()
	 * 
	 * @param task Describes the current task as a String.
	 * @param item Describes the String item the task is being performed on.
	 * @return the next String line entered by the user.
	 */
	public String getStr(String item, String task){

		printCmdln(item, task);

		String str = "";
		while(str.equals("")){
			str = inputScanner.nextLine();	
			if(!(str.equals(""))){
				break;
			}				
		}
		return str;
	}

	/**
	 * exitLookup()
	 * 
	 * Asks the user to confirm their decision to close the program, then calls
	 * System to exit if return is pressed, or returns to previous task if the
	 * user enters 'n' to cancel.
	 * 
	 * This method calls the following supporting methods:
	 * @see #getAns(String a, String b) 
	 * @see #printInfoln()
	 */
	public void exitLookup(){

		printInfoln("This will clear your dictionaries.");
		printInfoln("Are you sure you want to exit?");
		if(getAns("y", "n")){
			printInfoln("GOOD-BYE");
			System.exit(0);
		}
		return;
	}	


}



