package com.grandcircus.planit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.Iterator;
import org.json.JSONException;

import org.json.JSONArray;
import org.json.JSONObject;
import org.json.simple.parser.JSONParser;

/**
 * @author Crunchify.com
 */

public class FetchURLData {

	public static void FetchURL() {
		JSONParser jsonParser = new JSONParser();
		ArrayList<SearchEvent> searchArray = new ArrayList<SearchEvent>();
		try {
			URL url = new URL(
					"https://app.ticketmaster.com//discovery/v2/events.json?apikey=9GWQl0TyQA2GKd6qcHEAMxL3VkwldGx3&radius=1&startDateTime=2016-09-20T00%3A00%3A00Z&endDateTime=2016-09-26T00%3A00%3A00Z&city=Detroit HTTP/1.1");
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				ArrayList<SearchEvent> searchedEvents = new ArrayList<SearchEvent>();
				JSONObject tempJsonObject = new JSONObject(strTemp); 
				JSONObject embedded = tempJsonObject.getJSONObject ("_embedded");
				
				 JSONArray jsonEventArray = embedded.getJSONArray("events");
				    for (int i = 0; i < jsonEventArray.length(); i++) {
				    	SearchEvent se = new SearchEvent();
				        JSONObject jsonEventObject = jsonEventArray.getJSONObject(i);
				        try{
				        	JSONObject temp = (JSONObject)jsonEventObject.get("dates");
				        	temp = (JSONObject)temp.get("start");
				        
				        	se.setId((String)jsonEventObject.get("id"));
					        se.setUrl((String)jsonEventObject.get("url"));
					        se.setName((String)jsonEventObject.get("name"));
					        se.setDateTime((String)temp.get("localDate"));
					        se.setInfo((String)jsonEventObject.get("info"));
					        System.out.println(temp.get("localDate"));
				        }catch(JSONException e){
				        
				        }
				        System.out.println(se.toString());
				        searchedEvents.add(se);
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
	}
}