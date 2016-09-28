package com.grandcircus.planit;

import org.hibernate.validator.constraints.NotEmpty;

public class tripDetails {

private int tripID;
private String eventID;
private String dateOfEvent;
private String typeOfEvent;

public int getTripID() {
	return tripID;
}
public void setTripID(int tripID) {
	this.tripID = tripID;
}
public String getEventID() {
	return eventID;
}
public void setEventID(String eventID) {
	this.eventID = eventID;
}

public String getTypeOfEvent() {
	return typeOfEvent;
}
public void setTypeOfEvent(String typeOfTrip) {
	this.typeOfEvent = typeOfTrip;
}
public String getDateOfEvent() {
	return dateOfEvent;
}
public void setDateOfEvent(String dateOfEvent) {
	this.dateOfEvent = dateOfEvent;
}



}