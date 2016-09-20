package com.grandcircus.planit;

import java.io.BufferedReader;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import org.json.JSONException;
import com.grandcircus.planit.resources.TicketmasterKey;
import org.json.JSONArray;
import org.json.JSONObject;

public class FetchURLData {

	public static ArrayList<SearchEvent> fetchEvents() {
		ArrayList<SearchEvent> searchedEvents = new ArrayList<SearchEvent>();
		try {
			URL url = new URL(TicketmasterKey.getAPI());
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				
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
				        searchedEvents.add(se);
				}
				
			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return searchedEvents;
	}
}