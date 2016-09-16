package com.grandcircus.planit;

import java.util.ArrayList;

public class UserSearch {

	public static boolean checkUserAndPass(ArrayList<User> users, String username, String password){
		
		for(User u: users){
			if(u.getUsername().equalsIgnoreCase(username)){
				if(u.getPassword().equals(password)){
					return true;
				}
			}
		}
		
		return false;
	}
	
	
}
//DigestUtils.md5Hex(