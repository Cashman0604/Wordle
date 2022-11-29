/**
 * Class for running a game of Wordle 
 * 
 * @author Cash Belknap
 */

import java.util.Scanner;
import java.util.Arrays;
import java.util.ArrayList;
import java.io.File;

class Wordle {
	private int wordLength; // length of words in the active dictionary
	private int guesses; // number of guesses that can be taken
	private String[] answer; // default answer
	private ArrayList<Letter> guessStr; // green, yellow, grey letters of current guess
	private ArrayList<Word> dictionary;
	private Letter[] alphabet; // All Letters and their values

	/**
	 * Constructor
	 * 
	 * @param l > 0
	 * post: instantiates all instance variables
	 */
	public Wordle(int l) {
		// precondition check
		if (l <= 0) {
			throw new IllegalArgumentException("The word length l must be > 0");
		}

		wordLength = l;
		guesses = l + 1; // guesses are 1 more than wordLength
		guessStr = new ArrayList<Letter>();
		dictionary = new ArrayList<Word>();

		// instantiates the alphabet
		boolean[] construct = new boolean[] { true, true, true, true, true }; // possible spots for a letter to be in
		boolean[] green = new boolean[] { false, false, false, false, false }; // green spaces
		alphabet = new Letter[] {
				new Letter(Arrays.copyOf(construct, construct.length), 0, "a", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "b", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "c", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "d", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "e", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "f", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "g", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "h", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "i", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "j", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "k", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "l", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "m", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "n", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "o", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "p", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "q", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "r", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "s", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "t", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "u", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "v", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "w", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "x", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "y", Arrays.copyOf(green, green.length)),
				new Letter(Arrays.copyOf(construct, construct.length), 0, "z", Arrays.copyOf(green, green.length)) };
	}

	/**
	 * Fills dictionary with all words
	 * 
	 * pre: wordleList.txt is formatted correctly
	 * post: dictionary is filled with all letter obj from fiveLetterWords.txt
	 * @throws Exception
	 */
	public void addWords() throws Exception {
		Scanner file = new Scanner(new File("files\\wordleList.txt"));
		// add all the Word objects in file
		while (file.hasNext()) {
			Word t = new Word(file.nextLine());
			dictionary.add(t);
		}
	}

	/**
	 * Checks the player's guess against the actual answer
	 * 
	 * @param guess != null, guess.size() == wordLength
	 * post: adds Letters and whether they're green, yellow or
	 * eliminated to alphabet
	 */
	private void checkWord(ArrayList<Letter> guess) {
		// precondition check
		if (guess == null || guess.size() != wordLength) {
			throw new IllegalArgumentException("guess cannot be null and must be " + wordLength + "letters long");
		}

		// Checks green, yellow, grey letters
		for (int i = 0; i < guess.size(); i++) {
			Letter temp = checkYellowGrey(guess, i); // checks if the letter is yellow or grey
			// checks if the letter is green
			if (temp.getLetter().equals(answer[i])) {
				temp.changeCondition(3);
				temp.changeSpot(i, true);
				temp.setGreenSpace(i);
				guess.set(i, temp);
			}
		}

		// replaces alphabet letters with their new letters
		for (int x = 0; x < guess.size(); x++) {
			int c = guess.get(x).getLetter().toLowerCase().charAt(0) - 97; // index of the letter
			alphabet[c] = guess.get(x);
		}

		guessStr = guess;
	}

	/**
	 * Checks if a letter would be yellow or grey
	 * 
	 * @param guess
	 * @param 0 <= i < wordLength
	 * @return Letter object with properties of guess.get(i)
	 */
	private Letter checkYellowGrey(ArrayList<Letter> guess, int i) {
		// precondition check
		if (i < 0 || i >= wordLength) {
			throw new IllegalArgumentException("i must be in range");
		}

		Letter l = guess.get(i); // letter that is being checked
		int temp = 0; // checks if it is in the word
		for (int x = 0; x < answer.length; x++) {
			if (!(l.getLetter().equals(answer[x]))) {
				temp++;
			}
		}
		if (temp >= answer.length) { // sets it to grey
			l.changeCondition(1);
			l.changeSpot(0, false);
			l.changeSpot(1, false);
			l.changeSpot(2, false);
			l.changeSpot(3, false);
			l.changeSpot(4, false);
		} else { // sets it to yellow
			l.changeCondition(2);
			l.changeSpot(i, false);
		}
		return l;
	}

	/**
	 * Runs a single game
	 */
	public void runner() {
		int guessesLeft = guesses; // guesses the player still has
		Scanner kb = new Scanner(System.in);
		String guess; // player's guess

		answer = (dictionary.get((int) (Math.random() * dictionary.size())).getWord()).split(""); // gets random word
		guessStr = new ArrayList<Letter>(); // resets value of guessStr

		ArrayList<String> letterString; // list of guess letters as a string
		ArrayList<Letter> guessLetters; // list of guess letters as a Letter obj

		// while the the guess isn't all green and you have enough guesses
		while (!outputCondition(guessStr).equals("33333") && guessesLeft != 0) {
			System.out.print("Enter a word: ");
			guess = kb.nextLine();

			// takes new guess and converts to an ArrayList<Letter>
			letterString = new ArrayList<String>(Arrays.asList(guess.split("")));
			guessLetters = new ArrayList<Letter>();
			// adds letters to the guess letter list
			for (int i = 0; i < letterString.size(); i++) {
				int c = letterString.get(i).toLowerCase().charAt(0) - 97; // index of the letter
				guessLetters.add(alphabet[c]);
			}

			// checks the guess and continues game
			checkWord(guessLetters);
			System.out.println(toString());
			guessesLeft--;
		}

		// Gives the correct answer
		System.out.println("The correct answer is: " + this.getAnswer());
	}

	/**
	 * Starts the accuracy test runner
	 */
	public void startAccRunner() {
		answer = (dictionary.get((int) (Math.random() * dictionary.size())).getWord()).split("");// gets random word
		guessStr = new ArrayList<Letter>();// resets value of guessStr
	}

	/**
	 * Runs accuracy test
	 * 
	 * @param guess != null, guess.length == wordLength
	 * @return alphabet array of all the letters
	 */
	public Letter[] accRunner(String guess) {
		// precondition check
		if (guess == null || guess.length() != wordLength) {
			throw new IllegalArgumentException("guess must not be null and must be " + wordLength + " letters long.");
		}

		// takes new guess and converts to an ArrayList<Letter>
		ArrayList<String> letterString = new ArrayList<String>(Arrays.asList(guess.split("")));
		ArrayList<Letter> guessLetters = new ArrayList<Letter>();
		// adds letters to the guess letter list
		for (int i = 0; i < letterString.size(); i++) {
			int c = letterString.get(i).toLowerCase().charAt(0) - 97; // index of the letter
			guessLetters.add(alphabet[c]);
		}
		// checks the player guess
		checkWord(guessLetters);
		return alphabet;
	}

	/**
	 * Opening comments/rules
	 */
	public String welcome() {
		return "-+*+- java Wordle -+*+-\n\nWelcome to java Wordle, my poor attempt at creating Wordle"
				+ " in java!\nTo play enter a 5 letter word. Press enter and you will see a String "
				+ "\nthat looks like GGBYB. \n\"B\" means that the letter in that index is not in the "
				+ "correct answer. \n\"Y\" means that the letter is in the correct answer but is in the"
				+ " wrong place \n \n\"G\" means the letter is in the answer and in the right spot. "
				+ "\nIf you don't understand, that's rough. Good luck!\n\n ";
	}

	/**
	 * Gives the output as a String
	 */
	public String toString() {
		StringBuilder outputStr = new StringBuilder();

		// adds GYB to the string to denote if the letter is
		// Green, Yellow, or Grey. E represents an error.
		for (int i = 0; i < guessStr.size(); i++) {
			if (guessStr.get(i).spotVal(i) && guessStr.get(i).getCondition() == 3) {
				outputStr.append("G");
			} else if (guessStr.get(i).getCondition() == 2) {
				outputStr.append("Y");
			} else if (guessStr.get(i).getCondition() == 1) {
				outputStr.append("B");
			} else {
				outputStr.append("E");
			}
		}
		outputStr.append("\n");
		// adds the actual guess given
		for (Letter l : guessStr) {
			outputStr.append(l.getLetter());
		}

		return outputStr.toString();
	}

	/**
	 * Gives 5 character long output of all the letter conditions
	 * 
	 * @param output: guess Letters
	 * @return string of 1s 2s and 3s representing the guess's condition
	 */
	private String outputCondition(ArrayList<Letter> output) {
		String outputStr = "";

		// adds condition values to the string
		for (int i = 0; i < output.size(); i++) {
			outputStr += output.get(i).getCondition();
		}

		return outputStr;
	}

	/**
	 * Gives the correct answer
	 * Accuracy Tester Only
	 * 
	 * @return answer as a string
	 */
	public String getAnswer() {
		StringBuilder output = new StringBuilder();
		for (String c : answer) {
			output.append(c);
		}
		return output.toString();
	}

	/**
	 * Returns total guesses
	 * Accuracy Tester Only
	 * 
	 * @return guesses
	 */
	public int getGuesses() {
		return guesses;
	}
}