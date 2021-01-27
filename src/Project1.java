/**
 * COP 3538: Project 1 -- Array Searches and Sorts
 * <p>
 * This class opens the file Countries1.csv using methods outside of the main method,
 * and formats the information about them into an array of Country objects. This program
 * then interacts with the user, giving them the options to print the Country data, as well
 * as to sort the Country data by name, GDP per capita, and happiness.
 * 
 * @author Hailey Francis (n01402670)
 * @version September 13, 2019
 */
import java.io.File;
import java.io.FileNotFoundException;
import java.util.InputMismatchException;
import java.util.Scanner;

public class Project1 {	
	/**
	 * MAIN FUNCTION
	 * @param args
	 */
	public static void main(String[] args) {
		Scanner input = new Scanner(System.in);
		boolean exitProgram = false; //exits program when changed to true
		boolean inputValid = false; //will remain false until user's input is acceptable
		boolean sortedByName = false; //becomes true when array is sorted by name; used later to choose search method
		String userInput = ""; //String that receives user input
		Country countries[] = new Country[0]; //initializing countries array
		
		System.out.println("COP3538 Project 1");
		System.out.println("Instructor: Xudong Liu\n");
		
		System.out.println("Array Search and Sorts");
		System.out.print("Please enter the name of the file: ");
		String fileName = input.nextLine();
		
		// getting information from file and making array of Country objects //
		int count = findCount(fileName);
		try{ //in the case if a file is not found, count will become -1, throwing a NegativeArraySizeException
			countries = new Country[count];
		}catch(NegativeArraySizeException n) {
			System.out.println("Since the file has not been found, a NegativeArraySizeException has also been thrown, because findCount returns count - 1");
			count = 0;
		}
		readFile(fileName, count, countries);
		System.out.println("There were " + count + " records read.\n");
		
		// user continues making choices until exitProgram == true //
		while(exitProgram == false) {
			if(count <= 0) {
				System.out.println("Since there is no file, the program will be terminated.");
				break;
			}
			System.out.println("1. Print a countries report.\n2. Sort by Name\n3. Sort by Happiness\n4. Sort by GDP per capita\n5. Find and print a given country\n6. Quit");
			inputValid = false;
			while(inputValid == false) { // makes sure user input is valid; either 1, 2, 3, 4, 5, or 6.
				System.out.print("Enter your choice: ");
				userInput = input.next();
				if(userInput.equals("1") || userInput.equals("2") || userInput.equals("3") || userInput.equals("4") || userInput.equals("5") || userInput.equals("6")) {
					inputValid = true;
				}else {
					System.out.println("This input is not valid. Please enter a number 1-6 to make your choice.");
				}
			} //end while -- inputValid
			
			// program uses method based on user's decision //
			switch(userInput) {
			case "1": printReport(countries, count); break; //prints report
			case "2": sortName(countries, count); sortedByName = true; break; //sorts by name; bubble sort
			case "3": sortRank(countries, count); sortedByName = false; break; // sorts by Happiness Rank; selection sort
			case "4": sortGDPperCapita(countries, count); sortedByName = false; break;//sorts by GDP per capita; insertion sort
			case "5": // chooses sequential or binary search based on if sorted by name //
				System.out.print("Please enter the name of the country you want to find: ");
				String targetName = input.next(); //I can't figure out how to make user input include whitespace, so in the event the user enters a country with spaces between the name, it will not find the Country. If it a single name, however, it will.
				if(sortedByName == false) {
					findCountrySequential(countries, count, targetName);
				}else {
					findCountryBinary(countries, count, targetName);
				}
				break;
			case "6": exitProgram = true; break;
			}
		} //end while -- exitProgram
		
		System.out.println("You have successfully exited the program.");
		input.close();
	} // END OF MAIN
	
	/**
	 * This method is used to find int count. This is the int used for the size of the
	 * countries array. It reads through the file, and every time it reaches a new line, it
	 * increments an int count by 1. Once all of the file is read, the method returns the
	 * int count to later be used as the size of the countries array.
	 *
	 * 	@param fileName -- name of the file as a String
	 *  @return int count -- to be used as the size of the countries array
	 */
	public static int findCount(String fileName) {
		int count = 0;
		try { //every time there is a new line, count will increase by 1
			Scanner c = new Scanner(new File(fileName));
			c.useDelimiter("\r\n|\n");
			while(c.hasNext()) { 
				c.next();
				count++;
			}
			c.close();
			
		}
		catch(FileNotFoundException f) {
			System.out.println("File not found. "
					+ "Please make sure you entered the name of the file correctly "
					+ "and that the file is accessable the next time you run this program.\n"
					+ "(This project was submitted with a file called Countries1.csv.) [Error thrown by findCount method]");
		}
		return count - 1;
	} //end findCount
	
	/**
	 * This method reads through a file, with the fileName that was given by the user, to form an array 
	 * of Country objects. This array is copied to the parameter array so it can be used in the main method.
	 * 
	 * @param fileName -- the name of the file used
	 * @param count -- the number of elements in the array, found by the findCount method
	 * @param countries[] -- array that receives copy of array formed by readFile method to be used in main method
	 */
	public static void readFile(String fileName, int count, Country countries[]) {
		String name, code, capital; 
		int population, rank;
		double GDP; // doubles will all be read as exponentials
		int countTemp = 0; //increments to assign values to new array
		Country countriesTemp[] = new Country[count];
		Scanner r;
		// OPENING FILE //
		try {
			r = new Scanner(new File(fileName));
			r.useDelimiter("\\,|\r\n");
		    // READING FILE //
			for(int i = 0; i < 6; i++) { //meant to cycle through first line of file, which presumably is labels such as in Countries1.csv
				r.next();
			}
			while(r.hasNext()) { //assigns values from file to variables, then forming Country object added to Country array
			name = r.next();
			code = r.next();
			capital = r.next();
			population = r.nextInt();
			GDP = r.nextDouble();
			rank = r.nextInt();
			
			countriesTemp[countTemp] = new Country(name, code, capital, population, GDP, rank);
			countTemp++;
			}
		r.close();
		System.arraycopy(countriesTemp, 0, countries, 0, count); //copies temporary Country array to array that will be used in main
		}
		catch(InputMismatchException i) {
			System.out.println("Input Mismatch Exception. Program likely tried assigning value to wrong variable due to format of file.");
		}
		catch(NumberFormatException j) {
			System.out.println("Number format exception. Program likely tried assigning value to wrong variable due to format of file.");
		}catch(FileNotFoundException e) {
			System.out.println("File not found. "
					+ "Please make sure you entered the name of the file correctly "
					+ "and that the file is accessable the next time you run this program.\n"
					+ "(This project was submitted with a file called Countries1.csv.) [This error was thrown by the readFile method]");
		}
	} //end readFile
	
	/**
	 * This method sorts the Country objects in the countries[] array by name (alphabetically). It uses the compareNameTo method
	 * in the Country.java class to find out where a name fits alphabetically compared to another.
	 * 
	 * @param countries[] -- array of Country objects from main
	 * @param count -- size of countries array
	 */
	public static void sortName(Country countries[], int count) {
		for(int o = 0; o < count - 1; o++) {
			for(int i = count - 1; i > o; i--) {
				if(countries[i].compareNameTo(countries[i - 1]) < 0) {
					Country temp = countries[i];
					countries[i] = countries[i - 1];
					countries[i - 1] = temp;
				}
			}
		}
		System.out.println("\nCountries sorted alphabetically by name using Bubble Sort.\n");
	} //end sortName
	
	/**
	 * This method sorts the countries array by each country's Happiness Rank, ordered from least to
	 * greatest. This method uses Selection Sort to sort the countries in the array.
	 * 
	 * @param countries[] -- array of Country objects from main
	 * @param count -- size of countries array
	 */
	public static void sortRank(Country countries[], int count) {
		for(int o = 0; o < count - 1; o++) {
			int smallest = o;
			for(int i = o + 1; i < count; i++) {
				if(countries[i].getRank() < countries[smallest].getRank()) {
					smallest = i;
				}
			}
			if(smallest != o) {
				Country temp = countries[smallest];
				countries[smallest] = countries[o];
				countries[o] = temp;
			}
		}
		System.out.println("\nCountries sorted by happiness rank, using selection sort.\n");
	}//end sortRank
	
	/**
	 * This method sorts the Country objects in the countries[] array by GDP per capita.
	 * It uses the calcGDPperCapita method in Country.java to get the GDP per capita 
	 * values for each Country.
	 * 
	 * @param countries
	 * @param count
	 */
	public static void sortGDPperCapita(Country countries[], int count) {
		int in, out;
		for(out = 1; out < count; out++) {
			Country temp = countries[out];
			in = out - 1;
			while(in >= 0 && countries[in].calcGDPperCapita() > temp.calcGDPperCapita()) {
				countries[in + 1] = countries[in];
				in--;
			}
			countries[in + 1] = temp;
		}
		System.out.println("\nCountries sorted by GDP per Capita, using insertion sort.\n");
	} //end sortGDPperCapita
	
	/**
	 * This method uses a Sequential Search to find a Country in the countries array,
	 * the name being given by the user. This method is only used when the boolean sortedByName == false.
	 * This means that sequential search will not be used if the countries[] array was sorted by name.
	 * 
	 * @param countries[] -- array of Country objects from main
	 * @param count -- size of countries array
	 * @param targetName -- name of Country trying to be found, given by user
	 */
	public static void findCountrySequential(Country countries[], int count, String targetName) {
		boolean found = false; //becomes true if the requested country is found
		System.out.println("\nSequential Search has been used.\n");
		for(int i = 0; i < count; i++) {
			if(countries[i].getName().equals(targetName)) { 
				countries[i].print();
				System.out.println();
				found = true;
				break;
			}
		}
		if(found == false) {
			System.out.println("Sorry, the country you were looking was not found. Please make sure you entered the name in correctly.\n");
		}
	}
	
	/**
	 * This method uses a binary search to find a target country.
	 * This method is only used if the boolean sortedByName == true. This binary search 
	 * relies on the names being in order.
	 * 
	 * @param countries[] -- array of Country objects from main
	 * @param count -- size of countries array
	 * @param targetName -- name of Country trying to be found, given by user
	 */
	public static void findCountryBinary(Country countries[], int count, String targetName) {
		int low = 0; //lowerbound
		int up = count - 1; //upperbound
		int mid; //middle
		boolean found = false; //becomes true if the requested country is found
		
		System.out.println("\nBinary Sort has been used.\n");
		while(low <= up) {
			mid = (low + up) / 2;
			if(countries[mid].getName().compareTo(targetName) == 0) {
				countries[mid].print();
				found = true;
				break;
			}else if(countries[mid].compareNameTo(targetName) == 1) {
				up = mid - 1;
			}else {
				low = mid + 1;
			}
		}
		if(found == false) {
			System.out.println("Sorry, the country you were looking was not found. Please make sure you entered the name in correctly.\n");
		}
	}
	/**
	 * This method prints out a countries report for the user. It takes in the array of countries as well as the countries array
	 * size, and goes through a for loop to print out information about all of the countries. It prints in the order of the array,
	 * so if and when the user sorts the array using other methods, it will print the report in their desired order.
	 * 
	 * @param countries[] -- array of Country objects from main
	 * @param count -- size of countries array
	 */
	public static void printReport(Country countries[], int count) {
		System.out.println("\nName                              Code                Capital                  Population                GDP                           HappinessRank ");
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------ ");
		for(int i = 0; i < count; i++) {
			System.out.printf("%-35s", countries[i].getName());
			System.out.printf("%-20s", countries[i].getCode());
			System.out.printf("%-25s", countries[i].getCapital());
			System.out.printf("%-25s", countries[i].getPopulation());
			System.out.printf("%-30s", countries[i].getGDP());
			System.out.println(countries[i].getRank());
		}
		System.out.println("------------------------------------------------------------------------------------------------------------------------------------------------------ ");
		System.out.println();
	}
	
}
