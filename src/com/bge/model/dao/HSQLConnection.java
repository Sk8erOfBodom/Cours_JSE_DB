package com.bge.model.dao;

import java.sql.Connection;
import java.sql.DriverManager;
import java.sql.SQLException;

import com.bge.exception.DBException;

public class HSQLConnection {
	private final String URL = "jdbc:hsqldb:file:hsqldb/data/bge";
	private final String OPTIONS = "";
	private final String USER = "SA";
	private final String PASS = "";

	private static Connection connection;

	private HSQLConnection() throws DBException {
		try {
			HSQLConnection.connection = DriverManager.getConnection(URL + OPTIONS, USER, PASS);
		} catch (SQLException e) {
			throw new DBException("Error: Connection to database failed.");
		}
	}
	
	public static Connection get() throws DBException {
		if (HSQLConnection.connection == null) {
			new HSQLConnection();
		}
		
		return HSQLConnection.connection;
	}
	
	public static void close() throws DBException {
		try {
			HSQLConnection.connection.close();
			HSQLConnection.connection = null;
		} catch (SQLException e) {
			throw new DBException("Error closing the database: " + e.getMessage());
		}
	}
}
