package questionsmodel;
import java.util.ArrayList;

public class AWSInterface {
	private String nameOfDatabase;

	public AWSInterface(String nameOfDatabase) {
		this.nameOfDatabase = nameOfDatabase;
	}
	
	public ArrayList<String> getQuestionsAtLine( String className, String fileName, int lineNumber) {
		ArrayList<String> result = new ArrayList<String>();
		return result;
	}
	
	public boolean addQuestionAtLine(String className, String fileName, int lineNumber, String question) {
		return false;
	}
}
