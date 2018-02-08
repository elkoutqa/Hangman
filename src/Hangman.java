import java.util.*;
import java.io.BufferedReader;
import java.io.File;
import java.io.IOException;
import java.io.FileReader;
import java.io.BufferedWriter;
import java.io.FileWriter;

public class Hangman {
	
	static int numberOfAttemptsLeft = 6;
	static String word = "";
	static String progress = "";
	static String difficultyLevel = "";
	
	static ArrayList<Character> wordStorage = new ArrayList<>();
	static ArrayList<Character> progressStorage = new ArrayList<>();
	static ArrayList<Character> letterGuessStorage = new ArrayList<>();
	static ArrayList<String> wordDatabase = new ArrayList<>();
	
	public static void main(String[] args) {
		word = pickWord("easy");
		wordToArray(word);
		runHangman();
		
		//createScoreboard();
	}
	
	public static void createScoreboard() {
		
		try {
			File file = new File("scoreboard.txt");
			file.createNewFile();
		}
			 catch (IOException e) {
		    		System.out.println("Exception Occurred:");
			        e.printStackTrace();
			  }
	}
	
	public static String pickWord(String difficulty) {
		
		difficultyLevel = difficulty;
		
		FileReader file;
		BufferedReader reader;
		
		Random random = new Random();
		try {
			
			file = new FileReader("C:\\Users\\Admin\\Desktop\\eclipse-workspace\\Hangman\\words.txt"); 
			reader = new BufferedReader(file);

			String line = reader.readLine();

			int wordLength;
			while (line!= null) {
				
				line = line.toLowerCase();
				line = line.replaceAll("[^a-zA-Z0-9]", ""); 
				
				wordLength = line.length();
				
				if (wordLength > 2 && wordLength < 6) {
					wordDatabase.add(line);
				} else if (wordLength > 5 && wordLength < 8) {
					wordDatabase.add(line);
				} else if (wordLength > 7) {
					wordDatabase.add(line);
				}
		
				line = reader.readLine();
			}
			
			word = wordDatabase.get(random.nextInt(wordDatabase.size()));

			file.close();
			reader.close();
			
		} catch (IOException e) {
			e.printStackTrace();
			
		}

		return word;
	}
	
	public static void drawHangman() {
		
		if (numberOfAttemptsLeft == 6) {
			System.out.println("   ____\n  |    |\n  |\n  |\n  |\n__|__");
		} else if (numberOfAttemptsLeft == 5) {
			System.out.println("   ____\n  |    |\n  |    o\n  |\n  |\n__|__");
		} else if (numberOfAttemptsLeft == 4) {
			System.out.println("   ____\n  |    |\n  |    o\n  |    |\n  |\n__|__");
		} else if (numberOfAttemptsLeft == 3) {
			System.out.println("   ____\n  |    |\n  |    o\n  |   /|\n  |\n__|__");
		} else if (numberOfAttemptsLeft == 2) {
			System.out.println("   ____\n  |    |\n  |    o\n  |   /|\\ \n  |\n__|__");
		} else if (numberOfAttemptsLeft == 1) {
			System.out.println("   ____\n  |    |\n  |    o\n  |   /|\\ \n  |   /\n__|__");
		} else if (numberOfAttemptsLeft == 0) {
			System.out.println("   ____\n  |    |\n  |    o\n  |   /|\\ \n  |   / \\ \n__|__");
		}
	}
	

	public static void runHangman() {
		
		boolean test = false;
		String winLose;
		FileWriter file;
		BufferedWriter writer;
		
		Scanner scanner = new Scanner(System.in);
		while (test == false) {

			System.out.println("Enter Letter Guess: ");
			String letterGuess = scanner.nextLine();

			letterAttempt(word, letterGuess);
			
			test=gameEnd();
			
		}
		scanner.close();

		try {
			
			file = new FileWriter("C:\\Users\\Admin\\Desktop\\eclipse-workspace\\Hangman\\scoreboard.txt", true); 
			writer = new BufferedWriter(file);
			
			if (numberOfAttemptsLeft == 0) {
				winLose = "lose";
				} else {
					winLose = "win";
				}
			
			writer.write("John\t"+difficultyLevel+"\t"+winLose+"\t"+numberOfAttemptsLeft);
			writer.newLine();
			
			//file.close();
			writer.close();
			
			} catch (IOException e) {
				e.printStackTrace();
			}
		
	}
	
	
	public static int letterAttempt(String word, String letter) {
		
		char letterGuess = letter.charAt(0);
		
		
		
		if (letterGuess >='a' && letterGuess<='z') {
		} else {
			System.out.println(letter+" is not a valid input, input must be a single lower case letter.\n");
			return 0;
		}
		
		if (letter.length() != 1) {
			System.out.println(letter+" is not a valid input, input must be a single lower case letter.\n");
			return 0;
		}
		
		for (char j: letterGuessStorage) {
			if (letterGuess == j) {
				System.out.println(letter +" has already been used, pick another letter.\n");
				return 0;
			}
		}
		letterGuessStorage.add(letterGuess);
		boolean correctLetterGuess = false;

		for (int i = 0; i < word.length(); i++) {
					if (letterGuess == word.charAt(i)) {
						
						progressStorage.set(i, letterGuess);
						correctLetterGuess = true;
					}
				}

		if (correctLetterGuess==false) {
			numberOfAttemptsLeft--;
		}
		drawHangman();
		System.out.println("Guesses: "+letterGuessStorage);
		System.out.println("Number of incorrect guesses left: "+numberOfAttemptsLeft);
		System.out.println("Current Progress: "+progressStorage+"\n");
		
		return 0;
	}
	
	public static void wordToArray(String word) {
		
		for (int i=0; i<word.length(); i++) {
			wordStorage.add(word.charAt(i));
			progressStorage.add('_');
		}
	}
	
	public static boolean gameEnd() {
		
		boolean check = false;
			
		if (numberOfAttemptsLeft == 0) {
			System.out.println("GAME OVER: The word was "+word);
			return true;
		}
		
		for (int i=0; i<word.length(); i++) {
			if (wordStorage.get(i) == progressStorage.get(i)) {
				check = true;
			} else {
				return false;
			}
		}
		
		if (check) {
			System.out.println("YOU WIN!\nThe word was "+word);
			return true;
		}
		else {
			return false;
		}
	}
}
