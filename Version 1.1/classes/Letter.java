/**
 * Class for creating and storing a Letter object
 * 
 * @author Cash Belknap
 */

public class Letter {
	private boolean[] possibleSpot;// If value can go in that slot, slot = true; else false
	private int condition;// 0 = non-eliminated; 1 = eliminated; 2 = yellow; 3 = green
	private boolean[] greenSpaces;// Spaces where green letters are
	private String letter;

	/**
	 * Constructor
	 * 
	 * @param p != null
	 * @param 0 <= c <= 3
	 * @param l != null, l.length() == 1
	 * @param spaces != null
	 */
	public Letter(boolean[] p, int c, String l, boolean[] spaces) {
		// precondition check
		if (p == null || spaces == null || l == null || l.length() != 1 || c < 0 || c > 3) {
			throw new IllegalArgumentException("p can't be null, spaces can't be null,"
					+ " l can't be null, l must be 1 letter long, c must be in the range 0 <= c <=3");
		}
		possibleSpot = p;
		condition = c;
		letter = l;
		greenSpaces = spaces;
	}

	/**
	 * Constructor
	 * 
	 * @param i != null
	 */
	public Letter(Letter i) {
		// precondition check
		if (i == null) {
			throw new IllegalArgumentException("i can't be null");
		}
		possibleSpot = new boolean[] { i.spotVal(0), i.spotVal(1), i.spotVal(2), i.spotVal(3), i.spotVal(4) };
		condition = i.getCondition();
		letter = i.getLetter();
		greenSpaces = i.getGreenSpaces();
	}

	/**
	 * Returns letter as a String
	 * 
	 * @return letter
	 */
	public String getLetter() {
		return letter;
	}

	/**
	 * Returns condition as an integer
	 * 
	 * @return condition
	 */
	public int getCondition() {
		return condition;
	}

	/**
	 * Returns the boolean array of where the green spaces are
	 * 
	 * @return greenSpaces
	 */
	public boolean[] getGreenSpaces() {
		return greenSpaces;
	}

	/**
	 * Returns the boolean at greenSpaces[x]
	 * 
	 * @param 0 <= x < greenSpaces.length;
	 * @return greenSpaces[x]
	 */
	public boolean getOneGreenSpace(int x) {
		// precondition check
		if (x < 0 || x >= greenSpaces.length) {
			throw new IllegalArgumentException("x must be in range 0 <= x < " + greenSpaces.length);
		}
		return greenSpaces[x];
	}

	/**
	 * Assigns greenSpaces[x] true, making x a green space
	 * 
	 * @param 0 <= x < greenSpaces.length;
	 */
	public void setGreenSpace(int x) {
		// precondition check
		if (x < 0 || x >= greenSpaces.length) {
			throw new IllegalArgumentException("x must be in range 0 <= x < " + greenSpaces.length);
		}
		greenSpaces[x] = true;
	}

	/**
	 * Gives whether a letter can be in the given spot i
	 * 
	 * @param 0 <= i < possibleSpot.length
	 * @return boolean value at i
	 */
	public boolean spotVal(int i) {
		// precondition check
		if (i < 0 || i >= possibleSpot.length) {
			throw new IllegalArgumentException("i must be in range 0 <= x < " + possibleSpot.length);
		}
		return possibleSpot[i];
	}

	/**
	 * Updates condition to x
	 * 
	 * @param 0 <= x <= 3 
	 * post: getCondition() = x
	 */
	public void changeCondition(int x) {
		// precondition check
		if (x < 0 || x > 3) {
			throw new IllegalArgumentException("x must be in range 0 <= x <= 3");
		}
		condition = x;
	}

	/**
	 * Updates changeSpot to be b at the index x
	 * 
	 * @param 0 <= x < possibleSpot.length
	 * @param b 
	 * post: spotVal(x) = b
	 */
	public void changeSpot(int x, boolean b) {
		// precondition check
		if (x < 0 || x >= possibleSpot.length) {
			throw new IllegalArgumentException("x must be in range 0 <= x < " + possibleSpot.length);
		}
		possibleSpot[x] = b;
	}
}
