package com.bge.model.beans;

public class Employee {
	private int id;
	private String name;
	private String firstName;
	private int age;
	private int salary;
	
	public Employee(String name, String firstName, int age) {
		this(0, name, firstName, age, 0);
	}
	
	public Employee(int id, String name, String firstName, int age) {
		this(id, name, firstName, age, 0);
	}
	
	public Employee(String name, String firstName, int age, int salary) {
		this(0, name, firstName, age, salary);
	}
	
	public Employee(int id, String name, String firstName, int age, int salary) {
		this.id = id;
		this.name = capitalize(name);
		this.firstName = capitalize(firstName);
		this.age = age;
		this.salary = salary;
	}
	
	public int getId() {
		return this.id;
	}

	public String getName() {
		return name;
	}

	public String getFirstName() {
		return firstName;
	}

	public int getAge() {
		return age;
	}

	public int getSalary() {
		return salary;
	}

	public void setName(String name) {
		this.name = capitalize(name);
	}

	public void setFirstName(String firstName) {
		this.firstName = capitalize(firstName);
	}

	public void setAge(int age) {
		this.age = age;
	}

	public void setSalary(int salary) {
		this.salary = salary;
	}
	
	public String toString() {
		String str = "Employee";
		
		if (this.id > 0) {
			str += " " + this.id;
		}
		
		str += ": " + this.firstName + " " + this.name + " (" + this.age + " years old)";
		
		if (this.salary > 0) {
			str += " ; earns " + this.salary + "€ per year.";
		}
		
		return str;
	}
	
	private String capitalize(String str) {
		return str.substring(0, 1).toUpperCase() + str.substring(1).toLowerCase();
	}
}
