// -----------------------------------------------------
// Assignment (2)
// Question: (1)
// Written by: (Sohaib Daami 40260605)
// -----------------------------------------------------

//this program takes input from different csv files serializes them then deserializes them
//and provides the user with a menu to navigate the movie records previously found in the csv files

import java.util.Scanner;
import java.io.File;
import java.io.FileInputStream;
import java.io.FileNotFoundException;
import java.io.PrintWriter;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.ObjectInputStream;
import java.io.ObjectOutputStream;

/**
* The Driver class contains the main method as well as all the other methods
* needed to create the files and navigate the menu
* 
*
* @author Sohaib Daami
* @version 1.0
* @see String
*/
public class Driver {
	
	//attribute used in multiple methods 
	private static String[] genreArr = { "Musical", "Comedy", "Animation", "Adventure", "Drama", "Crime", "Biography",
			"Horror", "Action", "Documentary", "Fantasy", "Mystery", "Sci-fi", "Family", "Romance", "Thriller",
			"Western" };

	/**
	* the main method uses different methods to create the 2d movies array
	* then uses  switch case paired with a while true loop to provide the menu and help navigate it 
	*
	* @param 
	* @return  
	*/
	public static void main(String[] args) {
		Scanner keyin = new Scanner(System.in);

		String part1_manifest = "part1_manifest.txt";
		String part2_manifest = do_part1(part1_manifest);
		String part3_manifest = do_part2(part2_manifest);

		Movie[][] all_movies = do_part3(part3_manifest, part2_manifest);

		String choice = "";
		int s = 0;
		int[] nArr = new int[all_movies.length];
		for (int l = 0; l < nArr.length; l++) {
			nArr[l] = 0;
		}
		choice = display(part3_manifest, s, all_movies);
		while (true) {
			switch (choice) {

			case "s":
			case "S":
				s = displayS(all_movies, part3_manifest);
				choice = display(part3_manifest, s, all_movies);
				break;

			case "n":
			case "N":
				System.out.println(reader(part3_manifest, s).substring(0, reader(part3_manifest, s).indexOf('.'))
						+ " movies (" + arrayLength(all_movies[s]) + ")");
				int n = readInt(keyin);
				nArr[s] = displayN(nArr[s], n, s, all_movies);
				choice = display(part3_manifest, s, all_movies);
				break;

			case "x":
			case "X":
				System.out.println("thank you for using this movie library. ");
				System.exit(0);
			}
		}
	}

	/**
	* this method will read the file names from the 
	* part1_manifest.txt file and create new csv files based on genre with all the movie records in them 
	* as well as a bad movie records text file with all the invalid movie records
	* and it creates a text file with the names of all the new csv files
	*
	* @param String path
	* @return String
	*/
	public static String do_part1(String path) {
		int i = 0;
		String fileName;
		String record;
		String exceptionType = "";
		for (int k = 0; k < genreArr.length; k++) {

			if (createFile(genreArr[k] + ".csv")) {

				write(genreArr[k] + ".csv", "part2_manifest.txt");

			}
		}
		while (reader(path, i) != null) {

			fileName = reader(path, i);
			int j = 0;
			i++;
			while (reader(fileName, j) != null) {
				boolean verify = false;
				record = reader(fileName, j);
				j++;
				try {
					verifyMovie(record);
				} catch (newException e) {
					exceptionType = e.getMessage();
					verify = true;

				}
				if (verify) {
					String str = exceptionType + " " + record + " " + fileName + " " + j;
					write(str, "bad_movie_records.txt");

				} else {
					write(record, createRecordArr(record)[3] + ".csv");

				}
			}

		}
		return "part2_manifest.txt";

	}

	/**
	* this method will read the file names from the 
	* part2_manifest.txt file and create movie objects using the records found in the csv files
	* and serialize them into new ser files 
	* and it creates a text file with the names of all the new ser files
	*
	* @param String path
	* @return String
	*/
	public static String do_part2(String path) {
		for (int i = 0; reader(path, i) != null; i++) {
			String fileName = reader(path, i);
			if (reader(fileName, 0) != null) {
				Movie[] movies = new Movie[findLength(fileName)];
				for (int j = 0; reader(fileName, j) != null; j++) {
					movies[j] = createMovie(reader(fileName, j));
				}
				ObjectOutputStream oos = null;
				FileOutputStream fos = null;

				try {

					fos = new FileOutputStream(fileName.substring(0, fileName.indexOf('.')) + ".ser", true);
					oos = new ObjectOutputStream(fos);
					for (int k = 0; k < movies.length; k++) {
						oos.writeObject(movies[k]);

					}
					oos.flush();
					oos.close();
				} catch (FileNotFoundException e) {
					e.printStackTrace();
				} catch (IOException e) {
					e.printStackTrace();
				}

			}

		}
		for (int k = 0; k < genreArr.length; k++) {
			write(genreArr[k] + ".ser", "part3_manifest.txt");
			if (createFile(genreArr[k] + ".ser")) {
			}

		}
		return "part3_manifest.txt";
	}

	/**
	* this method will deserialize all the movie objects found in the ser files 
	* and put them in a 2D array 
	* 
	* @param String path
	* @param String path2
	* @return Movie[][]
	*/
	public static Movie[][] do_part3(String path, String path2) {
		Movie[][] all_movies = new Movie[17][];

		for (int i = 0; i < 17; i++) {
			FileInputStream fis = null;
			ObjectInputStream ois = null;
			try {

				File file = new File(reader(path, i));
				if (file.length() != 0) {
					fis = new FileInputStream(reader(path, i));
					ois = new ObjectInputStream(fis);

					all_movies[i] = new Movie[findLength(reader(path2, i))];
					for (int j = 0; j < findLength(reader(path2, i)); j++) {
						all_movies[i][j] = (Movie) ois.readObject();

					}

					ois.close();

				}
			}

			catch (ClassNotFoundException e) {
				e.printStackTrace();
			} catch (FileNotFoundException e) {
				e.printStackTrace();
			} catch (IOException e) {
				e.printStackTrace();
			}
		}
		return all_movies;
	}

	/**
	* this method receives a string that is a movie record and it will thrown the respective exception 
	* if the record is considered non valid 
	*
	* @param String record
	* @return void
	*/
	public static void verifyMovie(String record) throws BadYearException, BadTitleException, BadGenreException,
			BadScoreException, BadDurationException, BadRatingException, BadNameException, MissingQuotesException,
			ExcessFieldsException, MissingFieldsException {
		if (!(record == null)) {
			String[] recordString = record.split(",");
			if (countChar(record, '\"') % 2 == 1) {
				throw new MissingQuotesException();
			}
			if ((actualFields(recordString)) > 10) {
				throw new ExcessFieldsException();
			}
			if ((actualFields(recordString)) < 10) {
				throw new MissingFieldsException();
			}
			String[] newRecordString = createRecordArr(record);

			if (newRecordString[0] == null || !isNumeric(newRecordString[0])
					|| Integer.parseInt(newRecordString[0]) < 1990 || Integer.parseInt(newRecordString[0]) > 1999) {
				throw new BadYearException();
			}
			if (newRecordString[1] == null) {
				throw new BadTitleException();
			}
			if (newRecordString[3] == null || !verifyGenre(newRecordString[3])) {
				throw new BadGenreException();
			}
			if (newRecordString[5] == null || !isNumeric(newRecordString[5])
					|| Double.parseDouble(newRecordString[5]) < 0 || Double.parseDouble(newRecordString[5]) > 10) {
				throw new BadScoreException();
			}
			if (newRecordString[2] == null || !isNumeric(newRecordString[2])
					|| Integer.parseInt(newRecordString[2]) < 30 || Integer.parseInt(newRecordString[2]) > 300) {
				throw new BadDurationException();
			}
			if (newRecordString[4] == null || !verifyRating(newRecordString[4])) {
				throw new BadRatingException();
			}
			if (newRecordString[6] == null || newRecordString[7] == null || newRecordString[8] == null
					|| newRecordString[9] == null) {

				throw new BadNameException();
			}

		}

	}

	/**
	* this method counts the amount of appearances a character has in a string
	*
	* @param String str
	* @param char c
	* @return int
	*/
	public static int countChar(String str, char c) {
		int count = 0;

		for (int i = 0; i < str.length(); i++) {
			if (str.charAt(i) == c)
				count++;
		}

		return count;
	}

	
	/**
	* this method reads a string from a file
	*
	* @param String path
	* @param int i
	* @return String
	*/
	public static String reader(String path, int i) {
		Scanner fileScanner = null;
		try {
			fileScanner = new Scanner(new FileInputStream(path));
		} catch (FileNotFoundException e) {
			System.out.println("File " + path + " was not found");
			System.out.println("or could not be opened.");
			System.exit(0);
		}
		for (int j = 0; j < i; j++) {
			fileScanner.nextLine();
		}
		String str = null;
		if (fileScanner.hasNextLine()) {
			str = fileScanner.nextLine();
		}
		fileScanner.close();
		return str;
	}

	/**
	* this method writes a string in a  file
	*
	* @param String record
	* @param String path
	* @return void
	*/
	public static void write(String record, String path) {
		PrintWriter pw = null;
		try {
			pw = new PrintWriter(new FileOutputStream(path, true));
		} catch (FileNotFoundException e) {
			System.out.println("Error opening the file: " + path);
			System.exit(0);
		}
		pw.println(record);
		pw.close();

	}

	/**
	* this method checks if a string can be turned into a double or no
	*
	* @param String str
	* @return boolean
	*/
	public static boolean isNumeric(String str) {
		try {
			Double.parseDouble(str);
			return true;
		} catch (NumberFormatException e) {
			return false;
		}
	}

	/**
	* this method takes a record String and turns it into a valid array with 10 fields
	* this method only works for records with no syntax errors
	*
	* @param String record
	* @return String[]
	*/
	public static String[] createRecordArr(String record) {
		String[] recordString = record.split(",");
		String[] newRecordString = new String[10];

		int k = 0;
		for (int i = 0; i < 10; i++) {
			if (recordString[k].length() == 0) {
				newRecordString[i] = null;
				k++;
			} else if (recordString[k].charAt(0) != '\"') {
				newRecordString[i] = recordString[k];
				k++;

			} else {
				newRecordString[i] = "";
				int j = k;
				for (j = k; j < recordString.length; j++) {
					newRecordString[i] += recordString[j];
					if (recordString[j].charAt(recordString[j].length() - 1) == '\"') {
						break;
					} else {
						newRecordString[i] += ",";
					}
				}
				k = j + 1;

			}

		}
		return newRecordString;
	}

	

	/**
	* this method takes a string genre and verifies if its a valid genre
	*
	* @param String genre
	* @return boolean
	*/
	public static boolean verifyGenre(String genre) {

		for (int i = 0; i < genreArr.length; i++) {
			if (genre.equalsIgnoreCase(genreArr[i]))
				return true;
		}
		return false;

	}

	/**
	* this method takes a string rating and verifies if its a valid rating
	*
	* @param String rating
	* @return boolean
	*/
	public static boolean verifyRating(String rating) {
		String[] ratingArr = { "PG", "Unrated", "G", "R", "PG-13", "NC-17" };
		for (int i = 0; i < ratingArr.length; i++) {
			if (rating.equals(ratingArr[i]))
				return true;
		}
		return false;
	}

	/**
	* this method takes a record String and creates a movie object with it
	*
	* @param String record
	* @return Movie
	*/
	public static Movie createMovie(String Record) {
		String[] record = createRecordArr(Record);
		Movie movie = new Movie(Integer.parseInt(record[0]), record[1], Integer.parseInt(record[2]), record[3],
				record[4], Double.parseDouble(record[5]), record[6], record[7], record[8], record[9]);
		return movie;
	}

	/**
	* this method find the length of a text file
	*
	* @param String path
	* @return int
	*/
	public static int findLength(String path) {
		int i = 0;
		while (reader(path, i) != null) {
			i++;
		}
		return i;
	}

	/**
	* this method find the length of an array and returns 0 if the array is null
	* 
	*
	* @param Movie[] movies
	* @return int
	*/
	public static int arrayLength(Movie[] movies) {
		if (movies == null) {
			return 0;
		}
		return movies.length;
	}

	/**
	* this method displays the main menu and returns the user's choice
	*
	* @param String path
	* @param int n
	* @param Movie[][] movies
	* @return String
	*/
	public static String display(String path, int n, Movie[][] movies) {
		Scanner keyin = new Scanner(System.in);
		String choice = "";
		do {
			System.out.print("-----------------------------\r\n" + "Main Menu\r\n" + "-----------------------------\r\n"
					+ "s Select a movie array to navigate\r\n" + "n Navigate "
					+ reader(path, n).substring(0, reader(path, n).indexOf('.')) + " movies (" + arrayLength(movies[n])
					+ " records)\r\n" + "x Exit\r\n" + "-----------------------------\r\n" + "Enter Your Choice: ");
			choice = keyin.next();
		} while (!(choice.equalsIgnoreCase("s") || choice.equalsIgnoreCase("n") || choice.equalsIgnoreCase("x")));

		return choice;
	}

	/**
	* this method displays the genre menu and returns the user's choice
	*
	* @param Movie[][] movies
	* @param String path
	* @return int
	*/
	public static int displayS(Movie[][] movies, String path) {
		Scanner keyin = new Scanner(System.in);
		int choice = 0;
		System.out.println(
				"------------------------------\r\n" + "Genre Sub-Menu\r\n" + "------------------------------");
		do {
			for (int i = 0; i < movies.length; i++) {
				
				System.out.println(i + " " + reader(path, i).substring(0, reader(path, i).indexOf('.'))
						+ "					(" + arrayLength(movies[i]) + " movies )");
				
			}

			System.out.println("18 Exit\n------------------------------\n");
			choice = readInt(keyin);
		} while (choice < 0 || choice > 18);
		if (choice == 18)
			choice = 0;
		return choice;
	}

	/**
	* this method makes sure that the user enters an integer when prompted
	*
	* @param Scanner keyin
	* @return int
	*/
	public static int readInt(Scanner keyin) {
		System.out.print("Enter Your Choice: ");
		while (!keyin.hasNextInt()) {
			String badInput = keyin.next();
			System.out.println("Bad input : " + badInput + "\nEnter Your Choice: ");
		}
		int choice = keyin.nextInt();
		return choice;
	}

	/**
	* this method displays the amount of movies that user want to see and it keeps track of the pointer used when displaying the movies
	*
	* @param int previousN
	* @param int n
	* @param int s
	* @param Movie[][] movies
	* @return int
	*/
	public static int displayN(int previousN, int n, int s, Movie[][] movies) {
		int k = 0;
		if (movies[s] == null) {
			System.out.println("there are no movies in this genre.");
			return k;
		}
		if (n < 0) {
			for (int i = (previousN - Math.abs(n) + 1); i <= previousN; i++) {
				if ((previousN - Math.abs(n) + 1) <= 0) {
					System.out.println("BOF has been reached");
					for (int j = 0; j <= previousN; j++) {
						System.out.println(movies[s][j]);

						k = 0;

					}
					break;
				}
				System.out.println(movies[s][i]);
				k = (previousN - Math.abs(n) + 1);
			}
		} else if (n > 0) {
			for (int i = previousN; i <= (previousN + Math.abs(n) - 1); i++) {
				if ((previousN + Math.abs(n) - 1) >= movies[s].length) {
					System.out.println("EOF has been reached");
					for (int j = previousN; j < movies[s].length; j++) {
						System.out.println(movies[s][j]);
						k = j;
						
					}
					break;
				}
				System.out.println(movies[s][i]);
				k = i;
			}
		}
		return k;

	}

	/**
	* this method returns the actual amount of fields in a a record String array
	*
	* @param String[] record
	* @return int
	*/
	public static int actualFields(String[] record) {
		int sum = 0;
		for (int i = 0; i < record.length; i++) {
			if (record[i].length() == 0) {
				sum++;
			} else if (record[i].charAt(0) != '\"' && record[i].charAt(record[i].length() - 1) != '\"') {
				sum++;
			} else if (record[i].charAt(0) == '\"' && record[i].charAt(record[i].length() - 1) == '\"') {
				sum++;
			} else if (record[i].charAt(0) == '\"' && record[i].charAt(record[i].length() - 1) != '\"') {

				for (int j = i + 1; j < record.length; j++) {

					if (record[j].charAt(record[j].length() - 1) == '\"') {
						sum++;
						i = j;
					}
				}
			}
		}
		return sum;
	}

	/**
	* this method creates a file if that file doesn't already exist
	*it returns false if the file already exists
	*
	* @param String path
	* @return boolean
	*/
	public static boolean createFile(String path) {
		try {
			File myObj = new File(path);
			if (myObj.createNewFile()) {
				return true;
			} else {
				return false;
			}
		} catch (IOException e) {
			System.out.println("An error occurred.");
			e.printStackTrace();
		}
		return false;
	}

}
