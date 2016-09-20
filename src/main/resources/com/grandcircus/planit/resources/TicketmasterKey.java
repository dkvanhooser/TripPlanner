package com.grandcircus.planit.resources;

public class TicketmasterKey {
	private static String city;
	private static String dateFrom;
	private static String dateTo;
	
	
	
	final private static String api = "https://app.ticketmaster.com//discovery/v2/events.json?apikey=9GWQl0TyQA2GKd6qcHEAMxL3VkwldGx3&radius=1&startDateTime="+dateFrom+"T00%3A00%3A00Z&endDateTime="+dateTo+"T00%3A00%3A00Z&city="+city+" HTTP/1.1";
	public static String getAPI(){
		return api;
	}
	public static String getCity() {
		return city;
	}
	public static void setCity(String city) {
		TicketmasterKey.city = city;
	}
	public String getDateFrom() {
		return dateFrom;
	}
	public static void setDateFrom(String dateFrom) {
		TicketmasterKey.dateFrom = dateFrom;
	}
	public static String getDateTo() {
		return dateTo;
	}
	public static void setDateTo(String dateTo) {
		TicketmasterKey.dateTo = dateTo;
	}
	
	
	
}
