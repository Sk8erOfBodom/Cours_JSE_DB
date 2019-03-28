package com.bge.model.dao;

import java.sql.PreparedStatement;
import java.sql.ResultSet;
import java.sql.SQLException;
import java.util.ArrayList;

import com.bge.exception.DBException;
import com.bge.model.beans.Job;

/**
 * This class manages jobs in the database
 */
public class JobDAO extends DAO<Job> {
	/**
	 * Constructs a new DAO to manage jobs in the database
	 * 
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public JobDAO() throws DBException {
		super("job");
	}

	/**
	 * Inserts a new job in the database
	 * 
	 * @param Job The job to insert in the database
	 * @throws DBException Exception is thrown in case there is a database error
	 *                     during the insertion
	 */
	public void insert(Job job) throws DBException {
		String sql = "INSERT INTO jobs(label, id_emp) VALUES(?, ?)";

		try {
			PreparedStatement pstmt = this.connection.prepareStatement(sql);
			pstmt.setString(1, job.getLabel());
			pstmt.setInt(2, (job.getIdEmp() > 0) ? job.getIdEmp() : null);
			pstmt.executeUpdate();
			pstmt.close();
		} catch (SQLException e) {
			throw new DBException("Error adding the job: " + e.getMessage());
		}
	}

	/**
	 * Get all jobs in the database
	 * 
	 * @return An array containing all of the jobs in the database
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public Job[] getAll() throws DBException {
		PreparedStatement stmt;
		ArrayList<Job> jobs;

		try {
			stmt = this.connection.prepareStatement("SELECT * FROM jobs");
		} catch (SQLException e) {
			throw new DBException("Error getting the list of jobs: " + e.getMessage());
		}

		jobs = find(stmt);

		return jobs.toArray(new Job[jobs.size()]);
	}

	/**
	 * Finds a list of jobs corresponding to the SQL statement
	 * 
	 * @param stmt The statement to execute
	 * @return An ArrayList of corresponding jobs
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	private ArrayList<Job> find(PreparedStatement stmt) throws DBException {
		ArrayList<Job> jobs = new ArrayList<Job>();

		try {
			ResultSet rs = stmt.executeQuery();

			while (rs.next()) {
				Job job = new Job(rs.getInt("id"), rs.getString("label"), rs.getInt("id_emp"));

				jobs.add(job);
			}

			rs.close();
			stmt.close();
		} catch (SQLException e) {
			throw new DBException("Error getting the jobs: " + e.getMessage());
		}

		return jobs;
	}

	/**
	 * Finds a list of jobs corresponding to the value in the selected field
	 * 
	 * @param field The field to test
	 * @param value The value to match
	 * @return An array of jobs that match the given value
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public <T> Job[] find(String field, T value) throws DBException {
		String sql = "SELECT * FROM jobs WHERE " + field + " = ?";
		PreparedStatement stmt;
		ArrayList<Job> jobs;

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
			throw new DBException("Error getting the jobs: " + e.getMessage());
		}

		jobs = find(stmt);

		return jobs.toArray(new Job[jobs.size()]);
	}

	/**
	 * Finds the first job corresponding to the value in the given field
	 * 
	 * @param field The field to test
	 * @param value The value to match
	 * @return A job that matches the given value
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public <T> Job findOne(String field, T value) throws DBException {
		Job[] jobs = find(field, value);

		return (jobs.length > 0) ? jobs[0] : null;
	}

	/**
	 * Finds a job by their id
	 * 
	 * @param id The id of the job
	 * @return The job corresponding to the given id
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public Job find(int id) throws DBException {
		return findOne("id", id);
	}

	/**
	 * Deletes the given job from the database
	 * 
	 * @param emp The job to delete
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public void delete(Job job) throws DBException {
		delete(job.getId());
	}

	/**
	 * Updates the job in the database
	 * 
	 * @param job The job with updated values
	 * @throws DBException Exception is thrown in case there is a database error
	 */
	public void update(Job job) throws DBException {
		String sql = "UPDATE jobs SET label = ?, id_emp = ? WHERE id = ?";

		try {
			PreparedStatement stmt = this.connection.prepareStatement(sql);
			stmt.setString(1, job.getLabel());
			stmt.setInt(2, (job.getIdEmp() > 0) ? job.getIdEmp() : null);
			stmt.setInt(3, job.getId());

			if (stmt.executeUpdate() == 0) {
				throw new DBException("Error: The job was not present in the database and thus cannot be updated.");
			}

			stmt.close();
		} catch (SQLException e) {
			throw new DBException("Error updating the job: " + e.getMessage());
		}
	}
}
