package com.grandcircus.planit;

import javax.validation.constraints.NotNull;
import javax.validation.constraints.Size;
import org.hibernate.validator.constraints.Email;
import org.hibernate.validator.constraints.NotEmpty;

public class User {
	
	@Size(min = 6, max = 20, message="Username should be between 6-20 characters without spaces")
	@NotEmpty(message = "Cannot be left blank")
	private String username;
	
	
	@Size(min = 6, max = 30, message="Your password should be between 6-30 characters with 1 uppercase, 1 lowercase, and a number/special character")
	@NotEmpty(message = "Cannot be left blank")
	private String password;
	
	
	@Email(message="Please enter a valid email address")
	@NotEmpty(message = "Cannot be left blank")
	private String email;
	
	private int ID;
	
	
	public int getID() {
		return ID;
	}
	public void setID(int ID) {
		this.ID = ID;
	}
	public String getUsername() {
		return username;
	}
	public void setUsername(String username) {
		this.username = username;
	}
	public String getPassword() {
		return password;
	}
	public void setPassword(String password) {

		this.password = password;
	}
	
	public String getEmail() {
		return email;
	}
	public void setEmail(String email) {
		this.email = email;
	}


}