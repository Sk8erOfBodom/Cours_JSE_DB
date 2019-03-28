package com.bge.app;

import com.bge.exception.DBException;
import com.bge.model.beans.*;
import com.bge.model.dao.EmployeeDAO;
import com.bge.model.dao.JobDAO;

public class DatabaseApp {

	public static void main(String[] args) {
		try {
			JobDAO daoJ = new JobDAO();
			EmployeeDAO daoE = new EmployeeDAO();
			Job[] jobs;
			Employee[] emps;

			try {
				if (daoE.findByName("Cena") == null) {
					daoE.insert(new Employee("Cena", "John", 41));
				}
				if (daoE.findByName("Cage") == null) {
					daoE.insert(new Employee("Cage", "Nicolas", 55));
					daoJ.insert(new Job("Actor", daoE.findByName("Cage").getId()));
				}
				if(daoE.findByName("Norris") == null) {
					daoE.insert(new Employee("Norris", "Chuck", 79, 420000000));
					daoJ.insert(new Job("Chuck Norris", daoE.findByName("Norris").getId()));
				}
			} catch (DBException e) {
				System.out.println(e.getMessage());
			}

			emps = daoE.getAll();

			for (Employee emp : emps) {
				System.out.println(emp);
			}

			jobs = daoJ.getAll();

			for (Job job : jobs) {
				String str = "";

				str += job;

				if (job.getIdEmp() > 0)
					str += " -> " + daoE.find(job.getIdEmp());

				System.out.println(str);
			}

			daoJ.close();
			daoE.close();
		} catch (DBException e) {
			System.out.println(e.getMessage());
		}
	}

}
