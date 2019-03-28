package com.bge.model.beans;

public class Job {
	private int id;
	private String label;
	private int idEmp;
	
	public Job(String label) {
		this(0, label, 0);
	}
	
	public Job(String label, int idEmp) {
		this(0, label, idEmp);
	}
	
	public Job(int id, String label) {
		this(id, label, 0);
	}
	
	public Job(int id, String label, int idEmp) {
		this.id = id;
		this.label = label;
		this.idEmp = idEmp;
	}

	public int getId() {
		return id;
	}

	public String getLabel() {
		return label;
	}

	public int getIdEmp() {
		return idEmp;
	}

	public void setLabel(String label) {
		this.label = label;
	}

	public void setIdEmp(int idEmp) {
		this.idEmp = idEmp;
	}
	
	public String toString() {
		String str = "Job";
		
		if (this.id > 0) {
			str += " " + this.id;
		}
		
		return str + ": " + this.label;
	}
}
