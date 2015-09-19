package parsermodel;

import java.util.Map;

public class ParserResult {
	private Map <String, LinePair> commentToLineNumbers;
	private String newFile;
	
	public ParserResult(Map<String, LinePair> commentToLineNumbers,
			String newFile) {
		super();
		this.commentToLineNumbers = commentToLineNumbers;
		this.newFile = newFile;
	}

	public Map<String, LinePair> getCommentToLineNumbers() {
		return commentToLineNumbers;
	}

	public void setCommentToLineNumbers(Map<String, LinePair> commentToLineNumbers) {
		this.commentToLineNumbers = commentToLineNumbers;
	}

	public String getNewFile() {
		return newFile;
	}

	public void setNewFile(String newFile) {
		this.newFile = newFile;
	}
	
}
