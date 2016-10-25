package com.grandcircus.planit.resources;

public class TicketmasterKey {
	private String city;
	private String dateFrom;
	private String dateTo;
	private String api;
	private String eventId;
	
	public TicketmasterKey(String city, String dateFrom, String dateTo){
		setCity(city);
		setDateFrom(dateFrom);
		setDateTo(dateTo);
		this.api = "https://app.ticketmaster.com/discovery/v2/events.json?apikey=9GWQl0TyQA2GKd6qcHEAMxL3VkwldGx3&radius=1&startDateTime="+dateFrom+"T00%3A00%3A00Z&endDateTime="+dateTo+"T23%3A59%3A59Z&city="+city.replace(" ", "%20");
	}
	public TicketmasterKey(){
		this.api = "apikey=9GWQl0TyQA2GKd6qcHEAMxL3VkwldGx3";
	}
	public String getEventId() {
		return eventId;
	}
	public void setEventId(String eventId) {
		this.eventId = eventId;
	}
	
	public String getAPI(){
		return api;
	}
	public String getCity() {
		return city;
	}
	public void setCity(String city) {
		this.city = city;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public void setDateFrom(String dateFrom) {
		this.dateFrom = dateFrom;
	}
	public String getDateTo() {
		return dateTo;
	}
	public void setDateTo(String dateTo) {
		this.dateTo = dateTo;
	}
	
	
	
}
