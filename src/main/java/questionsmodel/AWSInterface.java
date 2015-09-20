package questionsmodel;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;
import java.util.ArrayList;
import java.util.HashMap;

import org.json.JSONArray;

public class AWSInterface {
	private String nameOfDatabase;
	private static String jdbcUrl = null;

	public AWSInterface(String nameOfDatabase) {
		this.nameOfDatabase = nameOfDatabase;
	}

	public JSONArray getQuestionsAtLine(String className, String fileName) {

		JSONArray result;
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
					.executeQuery("SELECT question, line_number FROM questions "
							+ "WHERE class_name = '"
							+ className
							+ "'"
							+ "AND file_name = '"
							+ fileName
							+ "'"
							+ "ORDER BY id DESC;");
			if (!resultSet.first())
				return new JSONArray();
			result = new JSONArray();
			JSONArray questions = new JSONArray();
			questions.put(resultSet.getString("question"));
			Integer lineNumber = Integer.valueOf(resultSet
					.getInt("line_number"));
			result.put(lineNumber, questions);

			while (resultSet.next()) {
				lineNumber = Integer.valueOf(resultSet.getInt("line_number"));
				String question = resultSet.getString("question");
				try {
					questions = result.getJSONArray(lineNumber);
					questions.put(question);

				} catch (Exception e) {
					questions = new JSONArray();
					questions.put(question);
					result.put(lineNumber, questions);
				}

			}
			resultSet.close();
			readStatement.close();
			conn.close();

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
			String query = "INSERT INTO questions "
					+ "(class_name, file_name, line_number, question) "
					+ "VALUES " + "('" + className + "','" + fileName + "',"
					+ lineNumber + ",'" + question + "');";
			System.out.println(query);
			writeStatement.addBatch(query);
			writeStatement.executeBatch();
			writeStatement.close();
			conn.close();
		} catch (SQLException e) {
			throw new RuntimeException("Cannot execute query!", e);
		}
		return true;
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
