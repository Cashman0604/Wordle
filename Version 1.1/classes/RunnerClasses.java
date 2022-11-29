/**
 * Class for running different functions of the 
 * Wordle and Wordle Solver Solver programs
 * 
 * @author Cash Belknap
 */

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Scanner;

public class RunnerClasses {

	// possible spots
	private static boolean[] construct = new boolean[] { true, true, true, true, true };
	// green spots
	private static boolean[] green = new boolean[] { false, false, false, false, false };
	// alphabet as Letter objects
	final private static Letter[] alphabet = new Letter[] {
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

	/**
	 * Plays a game of Wordle with word length 5
	 * 
	 * @throws Exception
	 */
	public static void play() throws Exception {
		// Wordle game
		Wordle obj = new Wordle(5);
		obj.addWords(); // start dictionary
		Scanner kb = new Scanner(System.in);
		String cont = "y"; // y to continue; anything else to not
		System.out.println(obj.welcome());
		while (cont.equals("y")) {
			obj.runner();
			System.out.print("Continue? (y/n) : ");
			cont = kb.next();
			System.out.println("");
		}
		kb.close();
	}

	/**
	 * Tests the accuracy of the Solver 2^iterations times for each word in words
	 * 
	 * @param iterations   >= 0
	 * @param words.length >= 1
	 * @throws Exception
	 */
	public static void tester(int iterations, String[] words) throws Exception {
		// precondition check
		if (iterations < 0 || words.length < 1) {
			throw new IllegalArgumentException("iterations must be >= 0. words must contain 1 or more word");
		}
		Stopwatch s = new Stopwatch(); // timer for the program
		double bestRate = 0; // stores the overall best success rate
		String bestWord = words[0]; // stores the overall best word
		for (String w : words) {
			int x = (int) Math.pow(2, iterations); // number of times run
			System.out.println("Testing word: " + w);
			double rate = bestRate; // current rate

			// test the word w, x times
			s.start();
			rate = AccuracyTested.testSolverAccuracy(x, w);
			s.stop();

			// prints results
			System.out.println("Ran " + x + " test with a " + rate + "% success rate");
			System.out.println(s.toString());

			// if this rate is better than the current bestRate, change bestRate and bestWord
			if (rate > bestRate) {
				bestRate = rate;
				bestWord = w;
			}
		}
		// prints final results
		System.out.println("\n\n The best word for the solver is " + bestWord + " with a success rate of " + bestRate + "%");
	}

	/**
	 * Starts a Solve based on user input
	 * 
	 * @throws Exception
	 */
	public static void solve() throws Exception {
		// copy of the full alphabet
		Letter[] alpha = Arrays.copyOf(alphabet, alphabet.length);
		Scanner kb = new Scanner(System.in);
		// instructions
		System.out.println("Write every word you've guessed in this format: 1e 2a 3r 1t 1h\n"
				+ "Where 1 are greyed out letters, 2 are yellow letters,and 3\n"
				+ "are green letters. After each full word press enter. Once\n" + "finished, enter in '!'");
		String input = kb.nextLine();
		// takes the user input and updates alpha based on it
		while (!input.equals("!")) {
			// splits the letters into individual parts
			String[] word = input.split(" ");
			for (int i = 0; i < word.length; i++) {
				solveHelper(word[i], i, alpha); // processes one letter
			}
			input = kb.nextLine();
		}
		// runs the Solver
		Solver s = new Solver(alpha);
		s.run();
		kb.close();
	}

	/**
	 * Processes a single letter and it data
	 * 
	 * @param let   != null
	 * @param index >= 0
	 * @param alpha != null
	 */
	private static void solveHelper(String let, int index, Letter[] alpha) {
		// precondition check
		if (let == null || index < 0 || alpha == null) {
			throw new IllegalArgumentException("letter can't be null, index must be >= 0, alpha can't be null");
		}
		String[] letter = let.split(""); // stores condition and actual letter
		int c = letter[1].toLowerCase().charAt(0) - 97; // index of the letter in alpha
		if (letter[0].equals("1")) { // handles grey letters
			if (alpha[c].getCondition() == 3) { // if the letter is already green
				// all non green spots are eliminated
				for (int i = 0; i < alpha[c].getGreenSpaces().length; i++) {
					if (!alpha[c].getGreenSpaces()[i]) {
						alpha[c].changeSpot(i, false);
					}
				}
			} else if (alpha[c].getCondition() == 2) { // if the letter is yellow
				alpha[c].changeSpot(index,  false); // current index is false
			}else { // if the letter isn't green, set it to a grey letter
				alpha[c] = new Letter(new boolean[5], 1, letter[1], new boolean[5]);
			}
		} else if (letter[0].equals("2")) { // handles yellow letters
			alpha[c].changeSpot(index, false); // current spot can't be used
			if (alpha[c].getCondition() != 3) { // if it isn't green, set to yellow
				alpha[c].changeCondition(2);
			}
		} else if (letter[0].equals("3")) { // handles green letters
			alpha[c].changeCondition(3); // condition is green
			alpha[c].setGreenSpace(index); // current index is green
			alpha[c].changeSpot(index, true); // current spot is valid
		} else {
			// only reached if instructions not followed. Doesn't break
			// program, but the results will be incorrect
			System.out.println("Invalid Letter Given. Output will be improper.");
		}
	}

	/**
	 * Manually solves a Wordle through editing the values in the method, instead of
	 * in the console. Used for debugging as this method works. Used for testing
	 * against solve() to check for issues.
	 * 
	 * @throws Exception
	 */
	public static void manualSolve() throws Exception {
		// "2 a 123"; (0= non-eliminated; 1 = eliminated; 2 = yellow; 3 = green); a =
		// letter; 123 = indeces where letter can't be
		String p = ""; // yellow letters
		String l = "1 e, 1 a, 1 t, 1 h, 1 o, 1 n, 1 d, 1 s"; // grey letter
		// instantiation for a new green letter
		// possible spots green spots
		// new Letter(new boolean[] {true,true,true,true,true},3,"", new boolean[]
		// {false, false, false, false, false})
		Letter[] known = { null, null, null, null, null }; // green values
		ArrayList<Letter> possible = stringSplit(p);
		ArrayList<Letter> letters = stringSplit(l);
		Solver s = new Solver(known, possible, letters, true); // solves with given values
		s.run();
	}

	/**
	 * Splits a string of formatted letters into an ArrayList of Letter objects
	 * 
	 * @param s
	 * @return ArrayList of letters
	 */
	private static ArrayList<Letter> stringSplit(String s) {
		ArrayList<Letter> list = new ArrayList<Letter>();
		if (s.length() > 0) {
			String[] letters = s.split(", ");
			for (String j : letters) {
				if (j.substring(0, 1).equals("1")) {
					list.add(makeLetter(0, 1, j.substring(2, 3), new boolean[5]));
				} else {
					list.add(makeLetter(Integer.valueOf(j.substring(4)), Integer.valueOf(j.substring(0, 1)),
							j.substring(2, 3), new boolean[5]));
				}
			}
		}
		return list;
	}

	/**
	 * Makes a Letter object based on the given values
	 * 
	 * @param elimSlot - indices of eliminated spots
	 * @param condit   - condition of the letter
	 * @param let      - actual letter
	 * @param green    - green spaces
	 * @return Letter object
	 */
	private static Letter makeLetter(int elimSlot, int condit, String let, boolean[] green) {
		int e = elimSlot; // eliminated indices
		boolean[] elimSlots; // boolean array of eliminated indices

		if (condit == 0) {
			elimSlots = new boolean[] { false, false, false, false, false };
		} else {
			elimSlots = new boolean[] { true, true, true, true, true };
			// sets all eliminated slots to false
			while (e > 0) {
				elimSlots[(e % 10) - 1] = false;
				e /= 10;
			}
		}
		return new Letter(elimSlots, condit, let, green);
	}
}
