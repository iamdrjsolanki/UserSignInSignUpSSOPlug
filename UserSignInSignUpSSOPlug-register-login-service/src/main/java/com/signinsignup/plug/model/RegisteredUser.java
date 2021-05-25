package com.signinsignup.plug.model;

public class RegisteredUser {
	
	private String email;
	private String username;
	private String role;
	private String displayName;
	
	public RegisteredUser(String email, String username, String role, String displayName) {
		super();
		this.email = email;
		this.username = username;
		this.role = role;
		this.displayName = displayName;
	}

	public String getEmail() {
		return email;
	}

	public void setEmail(String email) {
		this.email = email;
	}

	public String getUsername() {
		return username;
	}

	public void setUsername(String username) {
		this.username = username;
	}

	public String getRole() {
		return role;
	}

	public void setRole(String role) {
		this.role = role;
	}

	public String getDisplayName() {
		return displayName;
	}

	public void setDisplayName(String displayName) {
		this.displayName = displayName;
	}
	
}
