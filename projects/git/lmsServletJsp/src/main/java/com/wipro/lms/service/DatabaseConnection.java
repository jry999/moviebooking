package com.wipro.lms.service;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

public class DatabaseConnection {
	private static final String HOST = "localhost"; // hostname of database
	private static final String PORT = "3306"; // port for database
	private static final String DB = "library_MS"; // database name
	private static final String USER = "root"; // username for database
	private static final String PASSWORD = "Jerry999@"; // password for the database

	private static Connection connection;

	// Private constructor to prevent instantiation
	private DatabaseConnection() {
	}

	public static Connection getConnection() throws SQLException, ClassNotFoundException {
		if (connection == null) {
			synchronized (DatabaseConnection.class) {
				String jdbcUrl = "jdbc:mysql://" + HOST + ":" + PORT + "/" + DB;
				Class.forName("com.mysql.cj.jdbc.Driver");
				connection = DriverManager.getConnection(jdbcUrl, USER, PASSWORD);
				System.out.println("Successfully connected");
			}
		}
		return connection;
	}

}