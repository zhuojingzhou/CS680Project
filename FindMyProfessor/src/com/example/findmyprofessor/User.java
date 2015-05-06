package com.example.findmyprofessor;

public class User {
	int id;
	String email;
	String password;
	
	public User() {
		
	}
	
	public User (String em, String pw) {
		this.email = em;
		this.password = pw;
	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getEmail() {
		return this.email;
	}
	
	public void setEmail(String em) {
		this.email = em;
	}
	
	public String getPassword() {
		return this.password;
	}
	
	public void setPassword(String pw) {
		this.password = pw;
	}
}
