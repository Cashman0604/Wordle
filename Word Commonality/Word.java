
public class Word {
	private String word;
	private long commonality;
	
	public Word(String word, long commonality) {
		this.word = word;
		this.commonality = commonality;
	}
	public String getWord() {
		return word;
	}
	public int getLength() {
		return word.length();
	}
	public long getCommon() {
		return commonality;
	}
}
