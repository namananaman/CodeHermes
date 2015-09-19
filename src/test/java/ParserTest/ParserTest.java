package ParserTest;

import java.io.IOException;
import java.io.PrintWriter;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Map;

import org.junit.Test;

import parsermodel.FileParser;
import parsermodel.LinePair;
import parsermodel.ParserResult;

public class ParserTest {

	@Test
	public void test() throws IOException {
		String content = new String(Files.readAllBytes(Paths.get("src/test/java/ParserTest/TestClass.java")));
		ParserResult result = FileParser.parseFile(content);
		Map<String, LinePair> commentToLines  = result.getCommentToLineNumbers();
		/*@SuppressWarnings("resource")
		PrintWriter writer = new PrintWriter("src/test/java/ParserTest/test-parser.java", "UTF-8");
		writer.print(result.getNewFile());*/
		for(String s : commentToLines.keySet()) {
			System.out.println(s);
			LinePair pair = commentToLines.get(s);
			System.out.println(pair.getStart() + " " + pair.getEnd());
		}
		
		System.out.print(result.getNewFile());
	}

	public static String[] stringArray = { "	/**",
			"	 * yo man mah comment sao cool. ", "	 */",
			"	public void firstFunction() {", "", "	}", "", "	/**",
			"	 * this is my commentary on the situation", "	 */",
			"	public void secondFunction() {", "", "	}" };

/*	*//**
	 * yo man mah comment sao cool.
	 *//*
	public void firstFunction() {

	}

	*//**
	 * this is my commentary on the situation
	 *//*
	public void secondFunction() {

	}*/

}
