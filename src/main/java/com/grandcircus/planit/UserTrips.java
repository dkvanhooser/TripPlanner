package com.grandcircus.planit;

public class UserTrips {
	private int userID;
	private int tripID;
	private String tripName;
	
	public int getTripID() {
		return tripID;
	}
	public void setTripID(int tripID) {
		this.tripID = tripID;
	}
	public String getTripName() {
		return tripName;
	}
	public void setTripName(String tripName) {
		this.tripName = tripName;
	}
	public int getUserID() {
		return userID;
	}
	public void setUserID(String userID) {
		this.userID = Integer.parseInt(userID);
	}
	
	
}
