package questionsmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

public class AWSInterface {
	private String nameOfDatabase;
	private static String jdbcUrl = null;

	public AWSInterface(String nameOfDatabase) {
		this.nameOfDatabase = nameOfDatabase;
	}

	public HashMap<Integer, ArrayList<String>> getQuestionsAtLine(String className,
			String fileName) {
		HashMap<Integer, ArrayList<String>> result = new HashMap<Integer, ArrayList<String>>();
		
		if (jdbcUrl == null) {
			setJdbcUrl();
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get connection!", e);
		}
		Statement readStatement;
		try {
			readStatement = createStatement(conn);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get statement!", e);
		}
		ResultSet resultSet;
		try {
			resultSet = readStatement
					.executeQuery("SELECT question FROM questions "
							+ "WHERE class_name = '" + className + "'"
							+ "AND file_name = '" + fileName + "'"
							+ "ORDER BY id;");
			if (!resultSet.first())
				return result;
			ArrayList<String> questions = new ArrayList<String>();
			questions.add(resultSet.getString("question"));
			Integer lineNumber = Integer.valueOf(resultSet.getInt("line_number"));
			result.put(lineNumber, questions);
			
			while(resultSet.next()) {
				lineNumber = Integer.valueOf(resultSet.getInt("line_number"));
				String question = resultSet.getString("question");
				
				if(result.containsKey(lineNumber)) {
					result.get(lineNumber).add(question);
				}
				else {
					questions = new ArrayList<String>();
					questions.add(question);
					result.put(lineNumber, questions);
				}
			}

		} catch (SQLException e) {
			throw new RuntimeException("Cannot execute query!", e);
		}

		return result;
	}

	public boolean addQuestionAtLine(String className, String fileName,
			int lineNumber, String question) {

		if (jdbcUrl == null) {
			setJdbcUrl();
		}
		Connection conn;
		try {
			conn = DriverManager.getConnection(jdbcUrl);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get connection!", e);
		}
		Statement writeStatement;
		try {
			writeStatement = createStatement(conn);
		} catch (SQLException e) {
			throw new RuntimeException("Cannot get statement!", e);
		}

		try {
			writeStatement.addBatch("INSERT INTO questions "
					+ "(class_name, file_name, line_number, question) "
					+ "VALUES " + "('" + className + "','" + fileName + "',"
					+ lineNumber + ",'" + question + "');");

		} catch (SQLException e) {
			throw new RuntimeException("Cannot execute query!", e);
		}
		return false;
	}

	private static void setJdbcUrl() {
		String dbName = "ebdb";
		String userName = "CodeHermes";
		String password = "Slipknot";
		String hostname = "aa185mylkjqi4ss.czxamytge1aa.us-west-2.rds.amazonaws.com";
		String port = "3306";
		jdbcUrl = "jdbc:mysql://" + hostname + ":" + port + "/" + dbName
				+ "?user=" + userName + "&password=" + password;

		// Load the JDBC Driver
		try {
			System.out.println("Loading driver...");
			Class.forName("com.mysql.jdbc.Driver");
			System.out.println("Driver loaded!");
		} catch (ClassNotFoundException e) {
			throw new RuntimeException(
					"Cannot find the driver in the classpath!", e);
		}
	}

	private static Statement createStatement(Connection conn)
			throws SQLException {
		return conn.createStatement();
	}
}
