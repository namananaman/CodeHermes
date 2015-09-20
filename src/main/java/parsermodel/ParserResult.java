package parsermodel;

import java.util.ArrayList;
import java.util.Map;

public class ParserResult {
	private Map <String, LinePair> commentToLineNumbers;
	private String newFile;
	private ArrayList<String> blockComments;
	private ArrayList<String> inlineComments;
	private Map<String, Integer> inlineCommentToLineNumber;
	
	public ArrayList<String> getBlockComments() {
		return blockComments;
	}

	public void setBlockComments(ArrayList<String> blockComments) {
		this.blockComments = blockComments;
	}

	public ArrayList<String> getInlineComments() {
		return inlineComments;
	}

	public void setInlineComments(ArrayList<String> inlineComments) {
		this.inlineComments = inlineComments;
	}

	public Map<String, Integer> getInlineCommentToLineNumber() {
		return inlineCommentToLineNumber;
	}

	public void setInlineCommentToLineNumber(
			Map<String, Integer> inlineCommentToLineNumber) {
		this.inlineCommentToLineNumber = inlineCommentToLineNumber;
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
