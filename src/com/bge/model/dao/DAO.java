package com.bge.model.dao;

import java.sql.Connection;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.sql.Statement;

import com.bge.exception.DBException;

public abstract class DAO<T> implements DAOInterface<T> {
	protected Connection connection; // The DB connection
	protected String table;

	/**
	 * Constructs a new DAO to manage a table in the database
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public DAO() throws DBException {
		this.connection = HSQLConnection.get();
	}

	/**
	 * Constructs a new DAO to manage a table in the database
	 * 
	 * @param table The name of the table (without the s)
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public DAO(String table) throws DBException {
		this();
		this.table = table;
	}
	
	/**
	 * Counts the number of entries in the database
	 * 
	 * @return The number of entries
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public int count() throws DBException {
		int size = 0;

		try {
			Statement stmt = this.connection.createStatement();
			ResultSet rs = stmt.executeQuery("SELECT COUNT(*) FROM " + this.table + "s");

			if (rs.next()) {
				size = rs.getInt(1);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new DBException("Error getting the " + this.table + " count: " + e.getMessage());
		}

		return size;
	}

	/**
	 * Deletes the item with the given id from the database
	 * 
	 * @param id The id of the item to delete
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public void delete(int id) throws DBException {
		String sql = "DELETE FROM " + this.table + "s WHERE id = ?";

		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setInt(1, id);
			stmt.executeUpdate();
			stmt.close();
		} catch (SQLException e) {
			throw new DBException("Error deleting the " + this.table + ": " + e.getMessage());
		}
	}

	/**
	 * Closes the connection and saves the database
	 * 
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public void close() throws DBException {
		HSQLConnection.close();
		this.connection = null;
	}
}
