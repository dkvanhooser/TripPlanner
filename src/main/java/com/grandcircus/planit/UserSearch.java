package com.grandcircus.planit;

import java.util.ArrayList;

import org.jasypt.util.password.BasicPasswordEncryptor;

public class UserSearch {

	public static User checkUserAndPass(ArrayList<User> users, String username, String password){
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		for(User u: users){
			if(u.getUsername().equalsIgnoreCase(username)){
				System.out.println(passwordEncryptor.encryptPassword(password));
				if(passwordEncryptor.checkPassword(password, u.getPassword())){
					return u;
				}
			}
		}
		
		return null;
	}
	
	
}