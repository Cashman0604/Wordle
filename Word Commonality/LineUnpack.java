import java.util.ArrayList;

public class LineUnpack {
	private String line;
	String[] splitLine;
	
	public LineUnpack(String line) {
		this.line = line;
		splitLine = this.line.split("\t");
	}
	public String getWord() {
		return splitLine[0];
	}
	public long getCommon() {
		if(splitLine.length != 2) {
			System.out.print("oopsie");
			return 0;
		}
		return (Long.valueOf(splitLine[1]));
	}
}
