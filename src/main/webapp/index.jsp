<!DOCTYPE html>

<%@ page language="java" contentType="text/html; charset=UTF-8" pageEncoding="UTF-8"%>
    
<%@ taglib prefix="c" uri="http://java.sun.com/jsp/jstl/core" %>
<%@ taglib prefix="spring" uri="http://www.springframework.org/tags"%>

<%@ page import="java.sql.*" %>
<%
  // Read RDS Connection Information from the Environment
  String dbName = System.getProperty("RDS_DB_NAME");
  String userName = System.getProperty("RDS_USERNAME");
  String password = System.getProperty("RDS_PASSWORD");
  String hostname = System.getProperty("RDS_HOSTNAME");
  String port = System.getProperty("RDS_PORT");
  String jdbcUrl = "jdbc:mysql://" + hostname + ":" +
    port + "/" + dbName + "?user=" + userName + "&password=" + password;
  
  // Load the JDBC Driver
  try {
    System.out.println("Loading driver...");
    Class.forName("com.mysql.jdbc.Driver");
    System.out.println("Driver loaded!");
  } catch (ClassNotFoundException e) {
    throw new RuntimeException("Cannot find the driver in the classpath!", e);
  }

  Connection conn = null;
  Statement setupStatement = null;
  Statement readStatement = null;
  ResultSet resultSet = null;
  String results = "";
  int numresults = 0;
  String statement = null;
  String className = "myClass";
  String fileName = "myFile";
  int lineNumber = 25;
  String question = "This is not a question";

  try {
    // Create connection to RDS instance
    conn = DriverManager.getConnection(jdbcUrl);
    
    // Create a table and write two rows
    setupStatement = conn.createStatement();
    setupStatement.addBatch("INSERT INTO questions "
			+ "(class_name, file_name, line_number, question) "
			+ "VALUES " + "('" + className + "','" + fileName + "',"
			+ lineNumber + ",'" + question + "');");
    
    setupStatement.executeBatch();
    setupStatement.close();
    
  } catch (SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());
    System.out.println("VendorError: " + ex.getErrorCode());
  } finally {
    System.out.println("Closing the connection.");
    if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
  }

  try {
    conn = DriverManager.getConnection(jdbcUrl);
    
    readStatement = conn.createStatement();
    resultSet = readStatement.executeQuery("SELECT question FROM questions "
			+ "WHERE class_name = '" + className + "'"
			+ "AND file_name = '" + fileName + "'"
			+ "ORDER BY id;");

    resultSet.first();
    results = resultSet.getString("question");
    
    resultSet.close();
    readStatement.close();
    conn.close();

  } catch (SQLException ex) {
    // handle any errors
    System.out.println("SQLException: " + ex.getMessage());
    System.out.println("SQLState: " + ex.getSQLState());
    System.out.println("VendorError: " + ex.getErrorCode());
  } finally {
       System.out.println("Closing the connection.");
      if (conn != null) try { conn.close(); } catch (SQLException ignore) {}
  }
  
  String completeResult = jdbcUrl + " " + results;
%>

<html>
	<head>
		<meta charset="utf-8">
		<title>Welcome</title>
	</head> 
	<body>
		<c:url value="/show" var="messageUrl" />
		<a href="${messageUrl}">Click here to enter</a>
		<p>Established connection to RDS. Read first two rows: </p>  <%= completeResult %>
	</body>
</html>
