package com.grandcircus.planit;

import org.hibernate.validator.constraints.NotEmpty;

public class tripDetails {

private int tripID;
String eventID;
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

}