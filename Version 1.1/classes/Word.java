/**
 * Class for creating and storing a Word object
 * 
 * @author Cash Belknap
 */

public class Word {
	private String line; // String with word and commonality
	private String[] splitLine; // word and commonality as separate Strings
	private String word; // independent word
	private long commonality; // commonality of the word in English

	/**
	 * Constructor
	 * 
	 * @param line != null
	 */
	public Word(String line) {
		// precondition check
		if (line == null) {
			throw new IllegalArgumentException("line can't be null");
		}

		this.line = line;
		splitLine = this.line.split(" ");
		word = splitLine[0];
		commonality = Long.valueOf(splitLine[1]); // converts commonality String to long
	}

	/**
	 * Returns the word
	 * 
	 * @return word
	 */
	public String getWord() {
		return word;
	}

	/**
	 * Returns the commonality
	 * 
	 * @return commonality
	 */
	public long getCommon() {
		return commonality;
	}
}
