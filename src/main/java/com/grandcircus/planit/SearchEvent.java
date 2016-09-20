package com.grandcircus.planit;

public class SearchEvent {
	private String name;
	private String id;
	private String url;
	private String dateTime;
	private String info = "";
	
	public String getName() {
		return name;
	}
	public void setName(String name) {
		this.name = name;
	}
	public String getId() {
		return id;
	}
	public void setId(String id) {
		this.id = id;
	}
	public String getUrl() {
		return url;
	}
	public void setUrl(String url) {
		this.url = url;
	}
	public String getDateTime() {
		return dateTime;
	}
	public void setDateTime(String dateTime) {
		this.dateTime = dateTime;
	}
	public String getInfo() {
		return info;
	}
	public void setInfo(String info) {
		this.info = info;
	}
	public String toString(){
		return name + " "+ id + " " + url + " " + dateTime +" " + info;
		
	}
}
