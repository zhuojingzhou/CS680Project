package com.example.findmyprofessor;

public class ProfessRating {
	int id;
	String profName;
	String userEmail;
	String comment;
	int assignments;
	int knowledge;
	int accessible;
//	String created_at;
	
	public ProfessRating () {
		
	}
	
	public ProfessRating(int id, String pName, String uEmail, String comments,
			int assignemnt, int knowledge, int accessible) {
		this.id = id;
		this.profName = pName;
		this.userEmail = uEmail;
		this.comment = comments;
		this.assignments = assignemnt;
		this.knowledge = knowledge;
		this.accessible = accessible;

	}
	
	public ProfessRating(String pName, String uEmail, String comments,
			int assignemnt, int knowledge, int accessible) {
		this.profName = pName;
		this.userEmail = uEmail;
		this.comment = comments;
		this.assignments = assignemnt;
		this.knowledge = knowledge;
		this.accessible = accessible;

	}
	
	public int getId() {
		return this.id;
	}
	
	public void setId(int id) {
		this.id = id;
	}
	
	public String getProfName() {
		return this.profName;
	}
	
	public void setProfName(String name) {
		this.profName = name;
	}
	
	public String getUserEmail() {
		return this.userEmail;
	}
	
	public void setUserEmail(String em) {
		this.userEmail = em;
	}
	
	public String getComment() {
		return this.comment;
	}
	
	public void setComment(String comment) {
		this.comment = comment;
	}
	
	public int getAssignments() {
		return this.assignments;
	}
	
	public void setAssignments(int assi) {
		this.assignments = assi;
	}
	
	public int getKnowledge() {
		return this.knowledge;
	}
	
	public void setKnowledge(int k) {
		this.knowledge = k;
	}
	
	public int getAccessible() {
		return this.accessible;
	}
	
	public void setAccessible(int acce) {
		this.accessible = acce;
	}
	
	
}
