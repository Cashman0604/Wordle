/**
 * Class for testing the Accuracy of the Wordle Solver
 * 
 * @author Cash Belknap
 */

public class AccuracyTested {

	/**
	 * Runs accuracy() iterations number of times
	 * 
	 * @param iterations > 0
	 * @param word       != null
	 * @return percentage of iterations that succeeded
	 * @throws Exception
	 */
	public static double testSolverAccuracy(int iterations, String word) throws Exception {
		// precondition check
		if (iterations <= 0 || word == null) {
			throw new IllegalArgumentException("iterations must be > 0 and word cannot be null");
		}
		int success = 0; // number of successful runs
		for (int i = 0; i < iterations; i++) {
			if (accuracy(word)) {
				success++;
			}
		}
		// number of successful runs as a percentage
		return 100 * ((double) success / (double) iterations);
	}

	/**
	 * Runs a single round of the Solver
	 * 
	 * @param word
	 * @return Solver success
	 * @throws Exception
	 */
	private static boolean accuracy(String word) throws Exception {
		// create a Wordle object
		Wordle w = new Wordle(5);
		w.addWords();
		// begin the accuracy runner in w
		w.startAccRunner();
		// create a solver based off of the Wordle game
		Solver s = new Solver(w.accRunner(word));
		// solution given by s
		String temp = s.onlyOneString();
		// total guesses
		int guesses = w.getGuesses();
		// runs a Wordle game with the Solver playing it
		while (guesses > 0 && !(w.getAnswer().equals(temp))) {
			s = new Solver(w.accRunner(temp));
			guesses--;
			temp = s.onlyOneString();
		}

		// if the Solver ran out of guesses it lost
		if (guesses > 0) {
			return true;
		} else {
			return false;
		}
	}
}
