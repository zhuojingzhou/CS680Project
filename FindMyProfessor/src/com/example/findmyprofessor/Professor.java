package com.example.findmyprofessor;

public class Professor {
	int id;
	String name;
	String bioAddress;
	String department;
	
	public Professor () {
		
	}
	
	public Professor (int id, String name, String bioAdd, String dpt) {
		this.id = id;
		this.name = name;
		this.bioAddress = bioAdd;
		this.department = dpt;
	}
	
	public Professor (String name, String bioAdd, String dpt) {
		this.name = name;
		this.bioAddress = bioAdd;
		this.department = dpt;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getName() {
		return this.name;
	}
	
	public void setName(String name) {
		this.name = name;
	}
	
	public String getBioAddress() {
		return this.bioAddress;
	}
	
	public void setBioAddress(String bioAdd) {
		this.bioAddress = bioAdd;
	}
	
	public String getDepartment() {
		return this.department;
	}
	
	public void setDepartment(String dpt) {
		this.department = dpt;
	}
	
}
