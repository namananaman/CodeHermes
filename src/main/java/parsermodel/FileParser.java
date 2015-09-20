package parsermodel;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

public class FileParser {
	private static String START_COMMENT_TOKEN = "/\\*\\*";
	private static String END_COMMENT_TOKEN = "\\*/";
	private static int START_COMMENT_LENGTH = 3;
	private static int END_COMMENT_LENGTH = 2;

	private static String INLINE_COMMENT_TOKEN = "//";
	
	public static ParserResult parseFile(String file) {
		String newFile = "";
		Map<String, LinePair> commentsToLines = new HashMap<String, LinePair>();
		ParserResult result = new ParserResult();
		
		ArrayList<Integer> startMatchings = getMatchings(START_COMMENT_TOKEN, file, true);
		ArrayList<Integer> endMatchings = getMatchings(END_COMMENT_TOKEN, file, false);
		
		if(startMatchings.size() == 0) return result;
		
		ArrayList<Integer> lineNumbers = getMatchings("\n", file, true);
		
		ArrayList<Integer> startLineNumbers = getCorrespondingLineNumbers(startMatchings, lineNumbers);
		ArrayList<Integer> endLineNumbers = getCorrespondingLineNumbers(endMatchings, lineNumbers);
		
		int size = startLineNumbers.size();
		int sumOfCommentLines = 0, previousIndex = 0;
		int startLine = startMatchings.get(0).intValue();
		
		ArrayList<String> comments = new ArrayList<String>();
		ArrayList<LinePair> linePairs = new ArrayList<LinePair>();
		
		for (int i = 0; i < size; i++) {
			int endLine = endMatchings.get(i).intValue();
			
			String comment = removeAsteriskAndTabs(file.substring(startLine, endLine).trim());
			comments.add(comment);
			
			newFile += file.substring(previousIndex, startLine);
			
			previousIndex = endLine;
			
			if (i != (size-1)) {
				startLine = startMatchings.get(i+1).intValue();
			}			
		}
		newFile += file.substring(previousIndex);
		
		startLine = startLineNumbers.get(0).intValue();
		for (int i = 0; i < size; i++) {
			int endLine = endLineNumbers.get(i).intValue();
			
			int codeStart = startLine - sumOfCommentLines;
			sumOfCommentLines += (endLine - startLine);
			
			int codeEnd = lineNumbers.size() - sumOfCommentLines;
			
			if (i != (size-1)) {
				startLine = startLineNumbers.get(i+1).intValue();
				codeEnd = startLine - sumOfCommentLines;
			}
			
			linePairs.add(new LinePair(codeStart, codeEnd));
		}
		
		for (int i = 0; i < size; i++) {
			commentsToLines.put(comments.get(i), linePairs.get(i));
		}
		
		result.setCommentToLineNumbers(commentsToLines);
		result.setNewFile(newFile);
		result.setBlockComments(comments);
		
		return getInlineComments(result, newFile);
	}
	
	private static ParserResult getInlineComments(ParserResult currentResult, String file) {		
		ArrayList<String> comments = new ArrayList<String>();
		ArrayList<Integer> indices = new ArrayList<Integer>();
		ArrayList<Integer> newLineIndices = getMatchings("\n", file, true);
		int indexAfterComment, indexOfNewLine;
		int indexOfComment = file.indexOf(INLINE_COMMENT_TOKEN);
		
		while(indexOfComment != (-1)) {
			indexAfterComment = indexOfComment + INLINE_COMMENT_TOKEN.length();
			indexOfNewLine = file.substring(indexOfComment).indexOf("\n") + indexOfComment;
			comments.add(file.substring(indexAfterComment, indexOfNewLine));
			indices.add(Integer.valueOf(indexOfComment));
			indexOfComment = file.indexOf(INLINE_COMMENT_TOKEN);
		}
		
		ArrayList<Integer> lineNumbers = getCorrespondingLineNumbers(indices,newLineIndices);
		currentResult.setInlineComments(comments);
		int size = comments.size();
		HashMap<String, Integer> inlineCommentsToLineNumbers = new HashMap<String, Integer>();
		for(int i = 0; i < size; i++) {
			inlineCommentsToLineNumbers.put(comments.get(i), lineNumbers.get(i));
		}
		currentResult.setInlineCommentToLineNumber(inlineCommentsToLineNumbers);
		return currentResult;
	}
	
	private static ArrayList<Integer> getMatchings(String matchWithToken,
			String file, boolean isStart) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		Pattern pattern = Pattern.compile(matchWithToken);
		Matcher matcher = pattern.matcher(file);
		while (matcher.find()) {
			result.add(isStart ? Integer.valueOf(matcher.start()) : Integer
					.valueOf(matcher.end()));
		}
		return result;
	}

	private static String removeAsteriskAndTabs(String s) {
		return s.replaceAll("\\*", "").replaceAll("\t", " ").replaceAll("/", "");
	}

	private static ArrayList<Integer> getCorrespondingLineNumbers(
			List<Integer> indices, List<Integer> lineNumberIndices) {
		ArrayList<Integer> result = new ArrayList<Integer>();
		int currentLine = 0, currentIndex = 0;
		for (Integer index : indices) {
			int i = index.intValue();
			while (currentIndex <= i) {
				currentIndex = lineNumberIndices.get(currentLine).intValue();
				currentLine++;
			}
			result.add(Integer.valueOf(currentLine));
		}
		return result;
	}
	
}
