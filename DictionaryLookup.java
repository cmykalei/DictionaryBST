import java.io.*;
import java.util.Scanner;

/**
 * DictionaryLookup class.
 * 
 * DictionaryLookup is a program that takes user input to import, view
 * and modify dictionary text files.
 * 
 * DictionaryLookup implements a nested class 'Slot', so that the user
 * may store multiple dictionary files. A 'slot' contains a DictionaryBST, 
 * initialised as a new dictionary by default, a name, an index, and a link
 * to the next slot.
 * @see Slot#Slot(int i)
 * 
 * This class also utilises an supporting class to handle user input and command-
 * line output. 
 * @see LookupCommandLine#LookupCommandLine(Scanner sc, int max)
 * 
 * This class also has multiple instances of the following public classes:
 * @see DictionaryBST#DictionaryBST()
 */
public class DictionaryLookup{

	Slot head;
	Scanner scanner;
	LookupCommandLine commands;
	int maxSlots;

	/**
	 * DictionaryLookup constructor.
	 * 
	 * Constructs a new lookup, setting the class scanner and max slots to the
	 * values passed in. 
	 * 
	 * Sets up a queue of slots with empty dictionaries, then prints a welcome 
	 * message to the command-line.
	 * 
	 * @param sc Specifies the scanner object to use throughout the program.
	 * @param max Specifies the maximum amount of slots for this look up.
	 */
	public DictionaryLookup(Scanner sc, int max){

		scanner = sc;
		maxSlots = max;
		queueSlots(maxSlots);	

		commands = new LookupCommandLine(scanner, maxSlots);
		commands.viewWelcome();			
	}

	/**
	 * Entry point main.
	 * 
	 * Creates a new scanner to pass to the program's classes, and creates a new
	 * instance of DictionaryLookup. Runs until the method to run the lookup
	 * returns false, then closes the scanner.
	 */
	public static void main(String[] args){

		Scanner input = new Scanner (System.in);
		DictionaryLookup currentLookup = new DictionaryLookup(input, 5);
		boolean running = true;
		while(running){
			running = currentLookup.runLookup();
		}
		input.close();		
	}

	/**
	 * runLookup()
	 * 
	 * Checks each int the user inputs, then performs the function matching the
	 * number. 
	 * 
	 * @return 'true' until there is an error, false on catch.
	 */
	private boolean runLookup(){

		try{
			viewSlots();
			commands.viewCommands();
			int cmd = commands.getNum("continue", 1, 8);
			if(cmd == 1){
				searchWords();
			}
			else if(cmd == 2){
				printWords();
			}
			else if(cmd == 3){
				addWords();
			}
			else if(cmd == 4){
				removeWords();
			}
			else if(cmd == 5){
				importFile();
			}
			else if(cmd == 6){
				searchSlots();
			}
			else if(cmd == 7){
				printSlots();
			}	
			else if(cmd == 8){
				commands.exitLookup();
			}		
			return true;	
		}
		catch(Exception e){
			commands.printInfoln("Error running look-up: " + e);
			if(commands.getAns("y","n")){
				return true;
			}
			return false;
		}
		
	}

	/**
	 * Slot class.
	 * 
	 * Slot is an object that stores a single dictionary in the DictionaryLookup.
	 * 
	 * The Slot class implements a 'slot' of memory which stores a DictionaryBST
	 * and its name. It is designed so that there may be more than one dictionary 
	 * file stored in a look up.
	 * 
	 * This is a private nested class which supports the following class:
	 * @see DictionaryLookup#DictionaryLookup()
	 */
	private class Slot{

		public DictionaryBST dictionary = new DictionaryBST();
		public Slot next;
		public String name = "empty.txt";
		public int index;

		public Slot(int i){

			index = i;
			next = null;
		}	
	}

	/**
	 * queueSlots(DictionaryBST d, String n)
	 * 
	 * Adds slots, allocated for dictionaries, each to the front of the queue of 
	 * slots in the current lookup session. Sets the new Slot as the 'head slot'
	 * and previous head slot the next, so to extend the link.
	 * 
	 * @param max Specifies the amount of slots to add.
	 */
	private void queueSlots(int max){

		int i = 1;
		while(i < max+1){
			Slot newSlot = new Slot(i);
			newSlot.next = head;
			head = newSlot;
			i++;
		}
	}

	/**
	 * searchWords()
	 * 
	 * Gets a word from the user to search a chosen dictionary for,
	 * prints the result to the System.
	 * 
	 * This method calls the following supporting methods:
	 * @see LookupCommandLine#printInfoln(ln)
	 * @see LookupCommandLine#getStr(String item, String task)
	 * @see getSlot()
	 */
	private void searchWords(){

		commands.printInfoln("You selected———(1) Search for a word");
		String word = commands.getStr("word", "search");
		Slot current = getSlot();

		if(current.dictionary.search(word)){
			commands.printInfoln("'" + word + "' was found!");
			return;
		}
		commands.printInfoln("'" + word + "' was not found.");
		return;
	}

	/**
	 * printWords()
	 * 
	 * Gets the selected dictionary to print from, then asks if the user would
	 * like to print all words. If not, asks the user to enter a word to print.
	 * 
	 * This method calls the following supporting methods:
	 * @see LookupCommandLine#printInfoln(ln)
	 * @see LookupCommandLine#getStr(String item, String task)
	 * @see LookupCommandLine#getAns(String a, String b)
	 * @see DictionaryBST#printDictionaryItem(String w)
	 * @see DictionaryBST#printDictionary()
	 * @see getSlot()
	 */
	private void printWords(){
		
		commands.printInfoln("You selected———(2) Print a definition");	
		Slot current = getSlot();
		commands.printInfoln("Would you like to print all words?");
		boolean all = commands.getAns("y", "n");
		if(all){
			current.dictionary.printDictionary();
			return;
		}

		String word = commands.getStr("word", "print");	
		if(current.dictionary.search(word)){
			current.dictionary.printDictionaryItem(word);
			return;
		}
		commands.printInfoln("'" + word + "' was not found.");
		return;
	}


	/**
	 * addWords()
	 * 
	 * Gets the word and defintion to add from the user, then asks which
	 * dictionary to add it to. If the dictionary name is still default, asks
	 * the user to give it a new name.
	 * 
	 * This method calls the following supporting methods:
	 * @see LookupCommandLine#printInfoln(ln)
	 * @see LookupCommandLine#getStr(String item, String task)
	 * @see getSlot()
	 */
	private void addWords(){
	
		commands.printInfoln("You selected———(3) Add a word");	
		String word = commands.getStr("word", "add");
		String defintion = commands.getStr("defintion", "continue");
		if(word.equals(" ") || defintion.equals(" ")){
			commands.printInfoln("Can't add a blank word/defintion!");
			return;
		}

		Slot current = getSlot();
		if(current.dictionary.root == null){
			String newName = commands.getStr("name", "give new dictionary");
			current.name = newName;
		}		
		if(current.dictionary.search(word)){
			commands.printInfoln("The word already exists here!");
			return;
		}
		current.dictionary.insert(word, defintion);
		commands.printInfoln("Word was successfully added!");
		return;
	}

	/**
	 * removeWords()
	 * 
	 * Gets the word to remove from the user, then asks which dictionary to remove 
	 * it from. Checks that the dictionary has the word, then removes if so.
	 * 
	 * This method calls the following supporting methods:
	 * @see LookupCommandLine#printInfoln(ln)
	 * @see LookupCommandLine#getStr(String item, String task)
	 * @see getSlot()
	 */
	private void removeWords(){

		commands.printInfoln("You selected———(4) Remove a word");
		String word = commands.getStr("word", "remove");
		if(word.equals(" ")){
			commands.printInfoln("Can't remove a blank word!");
			return;
		}

		Slot current = getSlot();		
		if(!current.dictionary.search(word)){
			commands.printInfoln("The word doesn't exist here!");
			return;
		}
		current.dictionary.remove(word);
		commands.printInfoln("Word was successfully removed");
		return;	
	}


	/**
	 * importFile()
	 * 
	 * Creates a new scanner object to scan a file, then processes it removing
	 * duplicates. Gets the selected dictionary (slot) to add the file to, 
	 * then saves it.
	 * 
	 * This method calls the following supporting methods:
	 * @see LookupCommandLine#printInfoln(ln)
	 * @see LookupCommandLine#getStr(String item, String task)
	 * @see getSlot()
	 */
	private void importFile(){

		commands.printInfoln("You selected———(5) Import a new file");	
		try{
			DictionaryBST temp = new DictionaryBST();
			String filename = commands.getStr("file name", "import");
			File file = new File(filename);			
			if(!file.exists()){
				throw new Exception("'" + filename + "'' doesn't exist");
			}

			commands.printInfoln("Reading file...");
			Scanner scanFile = new Scanner(file);	
			while(scanFile.hasNextLine()){	
				String line = scanFile.nextLine();				
				Scanner split = new Scanner(line);
				
				split.useDelimiter(":.");
				while(split.hasNext()){

					String word = split.next().trim();
					String defintion = split.next().trim();

					if(temp.search(word)){
						commands.printInfoln("removing duplicate: " + word + "'");
						temp.remove(word);	
					} 
					temp.insert(word, defintion);
				}				
			}	
			Slot slot = getSlot();
			slot.dictionary = temp;
			slot.name = filename;
			commands.printInfoln("Saving dictionary...");
			commands.printInfoln("...");
			commands.printInfoln(filename + " was saved in " + slot.index);
			return;								
		}
		catch(Exception e){
			commands.printInfoln("Error processing file: " + e);
			commands.printInfoln("Would you like to try again?");	
			if(commands.getAns("y", "n")){
				importFile();
			} 
			return;
		}
	}

	/**
	 * getSlot()
	 * 
	 * Gets a dictionary slot selection from the user.
	 */
	private Slot getSlot(){

		if(head.next == null){
			return head;
		}		
		int i = commands.getNum("select dictionary", 1, this.maxSlots);
		Slot current = head;
		while(current != null){

			if(i == current.index){
				return current;
			}
			current = current.next;			
		}
		return getSlot();
	}	
	
	/**
	 * viewSlot()
	 * 
	 * Prints the current slots.
	 */
	private void viewSlots(){
		
		if(head == null){
			commands.printInfoln("No slots!");
			return;
		}

		commands.printInfoln("╔══════╦══════════════════════════════════");
		commands.printInfoln("║ SLOT ║ DICTIONARY NAME ");
		commands.printInfoln("╠══════╬══════════════════════════════════");
		if(head.next == null){
			commands.printInfoln("║  #" + head.index + "  ║ " + head.name);
			commands.printInfoln("╠══════╬═══════════════════════════════════");
			return;
		}

		Slot current = head;
		while(current != null){
			commands.printInfoln("║  #" + current.index + "  ║ " + current.name);
			commands.printInfoln("╠══════╬═══════════════════════════════════");
			current = current.next;		
		}
	}

	/**
	 * searchSlots()
	 * 
	 * Searches all slot dictionries for this word.
	 */
	private void searchSlots(){

		commands.printInfoln("You selected———(6) Search all dictionaries");
		String word = commands.getStr("word", "search");
		commands.printInfoln("'" + word + "' was found in:");
		int i = 0;
		Slot current = head;
		while(current.next != null){
			if(current.dictionary.search(word)){
				i++;
				commands.printInfoln("'" + current.name + "'");
			}
			current = current.next;
		}
		commands.printInfoln(String.valueOf(i) + " dictionaries.");
		return;
	}

	/**
	 * printSlots()
	 * 
	 * Prints all dictionaries in slots.
	 */
	private void printSlots(){

		Slot current = head;
		while(current.next != null){
			commands.printInfoln("");
			commands.printInfoln("---DICTIONARY START---");
			commands.printInfoln("");
			if(current.dictionary.root != null){				
				current.dictionary.printDictionary();
			}	
			if(current.dictionary.root == null){
				commands.printInfoln("[empty dictionary]");
			}
			commands.printInfoln("");
			commands.printInfoln("---DICTIONARY END---");
			commands.printInfoln("");		
			current = current.next;
		}
		return;
	}

}