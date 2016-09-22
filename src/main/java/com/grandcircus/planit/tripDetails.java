package com.grandcircus.planit;

import org.hibernate.validator.constraints.NotEmpty;

public class tripDetails {
@NotEmpty
private int tripID;
@NotEmpty int eventID;
public int getTripID() {
	return tripID;
}
public void setTripID(int tripID) {
	this.tripID = tripID;
}
public int getEventID() {
	return eventID;
}
public void setEventID(int eventID) {
	this.eventID = eventID;
}
}