package com.signinsignup.plug.model;


public class AuthenticationReponse {
	
	private final String username;
	private final String displayName;
	private final String role;
	private final String jwt;

	public AuthenticationReponse(String username, String displayName, String role, String jwt) {
		super();
		this.username = username;
		this.displayName = displayName;
		this.role = role;
		this.jwt = jwt;
	}

	public String getUsername() {
		return username;
	}

	public String getDisplayName() {
		return displayName;
	}

	public String getRole() {
		return role;
	}

	public String getJwt() {
		return jwt;
	}	

}
