/**
 * Class for solving a game of Wordle 
 * 
 * @author Cash Belknap
 */

import java.util.Scanner;
import java.util.ArrayList;
import java.io.File;

public class Solver {

	private boolean single; // Solving for most common word and all = true; Solving for all valid words = false.
	private Letter[] known; // Known/green letters in the word. Non-known are null
	private ArrayList<Letter> possible; // Yellow letters that could be in the word.
	private ArrayList<Letter> letters; // All eliminated letters.
	private ArrayList<Word> finalSolutions; // All solutions.

	/**
	 * Constructor
	 * 
	 * @param k - green letters
	 * @param p - yellow letters
	 * @param l - grey letters
	 * @param s - solving for the most common word
	 */
	public Solver(Letter[] k, ArrayList<Letter> p, ArrayList<Letter> l, boolean s) {
		known = k;
		possible = p;
		letters = l;
		finalSolutions = new ArrayList<Word>();
		single = s;
	}

	/**
	 * Constructor 
	 * Takes an array of Letter objects and sorts them
	 * 
	 * @param array
	 */
	public Solver(Letter[] array) {
		// instantiating variables
		known = new Letter[] { null, null, null, null, null };
		possible = new ArrayList<Letter>();
		letters = new ArrayList<Letter>();
		finalSolutions = new ArrayList<Word>();
		// sorts through all the letters
		for (Letter l : array) {
			if (l.getCondition() == 1) { // adds grey letters
				letters.add(new Letter(l));
			} else if (l.getCondition() == 2) { // adds yellow letters
				possible.add(new Letter(l));
			} else if (l.getCondition() == 3) { // adds green letters
				// puts green letters in the correct index of known
				for (int x = 0; x < l.getGreenSpaces().length; x++) {
					if (l.getOneGreenSpace(x)) {
						known[x] = new Letter(l);
					}
				}
			}
		}
	}

	/**
	 * Finds and adds all valid words to finalSolutions
	 * 
	 * pre: fiveLetterWords.txt is formatted correctly 
	 * post: Adds all valid words to finalSolutions
	 * @throws Exception
	 */
	private void elim() throws Exception {
		Word temp; // hold current word properties
		Scanner i = new Scanner(new File("Version 1.1\\files\\wordleList.txt"));
		// parses through entire dictionary
		while (i.hasNext()) {
			temp = new Word(i.nextLine());
			// checks if the word is valid
			if (checker(temp.getWord()) && greenCheck(temp.getWord())) {
				if (!(checkDupe(temp))) {
					finalSolutions.add(temp);
				}
			}
		}
	}

	/**
	 * Checks if known green letters are in the possible valid word p
	 * 
	 * @param p.length() = 5
	 * @return If the word contains all green letters returns true, if missing one 
	 * returns false.
	 * @throws Exception
	 */
	private boolean greenCheck(String p) throws Exception {
		// if t isn't null and doesn't have the green letter
		// at the given index return false, else true.
		if (known[0] != null && !((p.substring(0, 1).equalsIgnoreCase(known[0].getLetter())))) {
			return false;
		}
		if (known[1] != null && !((p.substring(1, 2).equalsIgnoreCase(known[1].getLetter())))) {
			return false;
		}
		if (known[2] != null && !((p.substring(2, 3).equalsIgnoreCase(known[2].getLetter())))) {
			return false;
		}
		if (known[3] != null && !((p.substring(3, 4).equalsIgnoreCase(known[3].getLetter())))) {
			return false;
		}
		if (known[4] != null && !((p.substring(4, 5).equalsIgnoreCase(known[4].getLetter())))) {
			return false;
		}
		return true;
	}

	/**
	 * Checks if the possible valid word p contains yellow letters or eliminated
	 * letters
	 * 
	 * @param p.length() = 5
	 * @return If the word contains all yellow letters and no eliminated letters
	 */
	private boolean checker(String p) {
		// checks if doesn't contain yellow letters
		if (possible != null && possible.size() > 0) {
			for (int i = 0; i < possible.size(); i++) {
				// if it doesn't contain a yellow letter, return false
				if (!p.contains(possible.get(i).getLetter())) {
					return false;
				}
				for (int x = 0; x < 5; x++) {
					// if the current letter is the same as a possible letter,
					// and the possibleSpot at this index is false
					if (p.substring(x, x + 1).equals(possible.get(i).getLetter()) && !(possible.get(i).spotVal(x))) {
						return false;
					}
				}
			}
		}
		// checks if word contains eliminated letters
		if (letters != null && letters.size() > 0) {
			for (int j = 0; j < letters.size(); j++) {
				if (p.contains(letters.get(j).getLetter())) {
					return false;
				}
			}
		}
		// checks if green letters are in an eliminated spot for that green letter
		if (known.length > 0) {
			for (int k = 0; k < known.length; k++) {
				for (int y = 0; y < 5; y++) {
					// if the current letter is the same as a green letter,
					// and the possibleSpot at this index is false
					if (known[k] != null && p.substring(y, y + 1).equals(known[k].getLetter())
							&& !(known[k].spotVal(y))) {
						return false;
					}
				}
			}
		}
		return true;
	}

	/**
	 * Checks if the given word is already within finalSolutions
	 * 
	 * @param check != null
	 * @return If the word is already in finalSolutions return true, else return
	 *         false.
	 */
	private boolean checkDupe(Word check) {
		// if finalSolutions is empty the word is not a duplicate
		if (finalSolutions.size() <= 0) {
			return false;
		}
		// returns true if duplicate word found
		for (Word w : finalSolutions) {
			if (w.getWord().equals(check.getWord())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * Returns the most common valid word 
	 * 
	 * pre: finalSolutions.size() > 0
	 * @return Returns the most common word in finalSolutions
	 */
	private String mostCommon() {
		// precondition check
		if (finalSolutions.size() <= 0) {
			throw new IndexOutOfBoundsException("No valid words found, check you data.");
		}
		long currMax = 0; // temp max value
		Word max = finalSolutions.get(0); // highest max value
		for (Word c : finalSolutions) {
			if (c.getCommon() > currMax) {
				currMax = c.getCommon();
				max = c;
			}
		}
		return max.getWord();
	}

	/**
	 * Runs the Solver program 
	 * 
	 * post: Prints list of most common solution and all other valid solutions
	 * @throws Exception
	 */
	public void run() throws Exception {
		elim(); // eliminates all invalid words
		if (single) { // prints out the most common solution and then all valid
			System.out.print("Most Common Solution: " + mostCommon() + "\n");
			System.out.println("All " + toString());
		} else { // prints finalSolutions
			System.out.println(toString());
		}
	}

	/**
	 * Prints the final solutions, each on its own line
	 * 
	 * pre: finalSolutions.size() > 0
	 * post: Prints all valid solutions
	 */
	public String toString() {
		StringBuilder output = new StringBuilder();
		output.append("Solutions:\n");
		for (Word c : finalSolutions) {
			output.append(c.getWord()).append("\n");
		}
		return output.toString();
	}

	/**
	 * Runs the program then returns only the most common word Used for the Accuracy
	 * Tester methods
	 * 
	 * @return mostCommon();
	 * @throws Exception
	 */
	public String onlyOneString() throws Exception {
		elim();
		return mostCommon();
	}
}