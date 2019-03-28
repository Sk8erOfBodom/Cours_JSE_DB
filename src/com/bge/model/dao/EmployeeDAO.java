package com.bge.model.dao;

import java.util.ArrayList;
import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;

import com.bge.exception.DBException;
import com.bge.model.beans.Employee;

/**
 * This class manages employees in the database
 */
public class EmployeeDAO extends DAO<Employee> {
	/**
	 * Constructs a new DAO to manage employees in the database
	 * 
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public EmployeeDAO() throws DBException {
		super("employee");
	}

	/**
	 * Inserts a new employee in the database
	 * 
	 * @param Employee The employee to insert in the database
	 * @throws DBException Exception is thrown in case there is a database error
	 *                     during the insertion
	 */
	public void insert(Employee emp) throws DBException {
		String sql = "INSERT INTO employees(name, first_name, age, salary) VALUES(?, ?, ?, ?)";

		try {
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			pstmt.setString(1, emp.getName());
			pstmt.setString(2, emp.getFirstName());
			pstmt.setInt(3, emp.getAge());
			pstmt.setInt(4, emp.getSalary());
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new DBException("Error adding the employee: " + e.getMessage());
		}
	}

	/**
	 * Get all employees in the database
	 * 
	 * @return An array containing all of the employees in the database
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public Employee[] getAll() throws DBException {
		PreparedStatement stmt;
		ArrayList<Employee> emps;

		try {
			stmt = this.connection.prepareStatement("SELECT * FROM employees");
		} catch (SQLException e) {
			throw new DBException("Error getting the list of employees: " + e.getMessage());
		}

		emps = find(stmt);

		return emps.toArray(new Employee[emps.size()]);
	}

	/**
	 * Finds a list of employees corresponding to the SQL statement
	 * 
	 * @param stmt The statement to execute
	 * @return An ArrayList of corresponding employees
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	private ArrayList<Employee> find(PreparedStatement stmt) throws DBException {
		ArrayList<Employee> employees = new ArrayList<Employee>();

		try {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				int id = rs.getInt("id");
				String name = rs.getString("name");
				String firstName = rs.getString("first_name");
				int age = rs.getInt("age");
				int salary = rs.getInt("salary");

				Employee emp = new Employee(id, name, firstName, age, salary);

				employees.add(emp);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new DBException("Error getting the employees: " + e.getMessage());
		}

		return employees;
	}

	/**
	 * Finds a list of employees corresponding to the value in the selected field
	 * 
	 * @param field The field to test
	 * @param value The value to match
	 * @return An array of employees that match the given value
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public <T> Employee[] find(String field, T value) throws DBException {
		String sql = "SELECT * FROM employees WHERE " + field + " = ?";
		PreparedStatement stmt;
		ArrayList<Employee> emps;

		try {
			stmt = this.connection.prepareStatement(sql);

			if (value.getClass().equals(String.class)) {
				stmt.setString(1, (String) value);
			} else if (value.getClass().equals(Integer.class)) {
				stmt.setInt(1, (int) value);
			} else {
				stmt.setObject(1, value);
			}
		} catch (SQLException e) {
			throw new DBException("Error getting the employees: " + e.getMessage());
		}

		emps = find(stmt);

		return emps.toArray(new Employee[emps.size()]);
	}

	/**
	 * Finds the first employee corresponding to the value in the given field
	 * 
	 * @param field The field to test
	 * @param value The value to match
	 * @return An employee that matches the given value
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public <T> Employee findOne(String field, T value) throws DBException {
		Employee[] emps = find(field, value);

		return (emps.length > 0) ? emps[0] : null;
	}

	/**
	 * Finds an employee by their id
	 * 
	 * @param id The id of the employee
	 * @return The employee corresponding to the given id
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public Employee find(int id) throws DBException {
		return findOne("id", id);
	}

	/**
	 * Finds an employee by their name
	 * 
	 * @param name The name of the employee
	 * @return The employee with the given name
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public Employee findByName(String name) throws DBException {
		return findOne("name", name);
	}

	/**
	 * Deletes the given employee from the database
	 * 
	 * @param emp The employee to delete
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public void delete(Employee emp) throws DBException {
		delete(emp.getId());
	}

	/**
	 * Updates the employee in the database
	 * 
	 * @param emp The employee with updated values
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public void update(Employee emp) throws DBException {
		String sql = "UPDATE employees SET name = ?, first_name = ?, age = ?, salary = ? WHERE id = ?";

		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, emp.getName());
			stmt.setString(2, emp.getFirstName());
			stmt.setInt(3, emp.getAge());
			stmt.setInt(4, (emp.getSalary() > 0) ? emp.getSalary() : null);
			stmt.setInt(5, emp.getId());

			if (stmt.executeUpdate() == 0) {
				throw new DBException(
						"Error: The employee was not present in the database and thus cannot be updated.");
			}

			stmt.close();
		} catch (SQLException e) {
			throw new DBException("Error updating the employee: " + e.getMessage());
		}
	}
}
