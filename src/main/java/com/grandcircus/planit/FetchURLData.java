package com.grandcircus.planit;

import java.io.BufferedReader;
import java.io.FileNotFoundException;
import java.io.InputStreamReader;
import java.net.URL;
import java.util.ArrayList;
import java.util.stream.Stream;
import java.util.List;
import org.json.JSONException;

import com.grandcircus.planit.resources.GoogleKey;
import com.grandcircus.planit.resources.TicketmasterKey;

import org.json.JSONArray;
import org.json.JSONObject;

public class FetchURLData {
	
	//retrieving events from TicketMaster + creating JSON objects
	public static ArrayList<SearchEvent> fetchAllEvents(TicketmasterKey key) {
		ArrayList<SearchEvent> searchedEvents = new ArrayList<SearchEvent>();
		try {
			//getting API key from java class
			URL url = new URL(key.getAPI());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {
				//creating new JSON object and retrieving from ticketmaster API
				JSONObject tempJsonObject = new JSONObject(strTemp);
				JSONObject embedded = tempJsonObject.getJSONObject("_embedded");
				//filtering based on list of events
				JSONArray jsonEventArray = embedded.getJSONArray("events");
				for (int i = 0; i < jsonEventArray.length(); i++) {
					//getting list of all events 
					SearchEvent se = new SearchEvent();
					JSONObject jsonEventObject = jsonEventArray.getJSONObject(i);
					try {
						JSONArray ja = jsonEventObject.getJSONArray("classifications");
						
						
						//getting information about each event 
						se.setId((String) jsonEventObject.get("id"));
						se.setName((String) jsonEventObject.get("name"));
						
						se.setUrl((String) jsonEventObject.get("url"));
						JSONObject temp = (JSONObject) jsonEventObject.get("dates");
						temp = (JSONObject) temp.get("start");
						se.setDateTime((String) temp.get("localDate"));
						se.setGenre(ja.getJSONObject(0).getJSONObject("segment").getString("name").replace("&", "").replace(" ",""));
						se.setInfo((String) jsonEventObject.get("info"));
					} catch (JSONException e) {
						System.out.println("file not found");
					}
					searchedEvents.add(se);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//giving list of all events
		return searchedEvents;
	}
	
	//retrieving saved events from ticketmaster 
	public static ArrayList<SearchEvent> fetchSavedEvents(TicketmasterKey key, ArrayList<tripDetails> event) {
		ArrayList<SearchEvent> searchedEvents = new ArrayList<SearchEvent>();
		//searching tripDetails ArrayList
		for (tripDetails s : event) {
			//if events found retrieving trip details from TicketMaster
			if (s.getTypeOfEvent().equals("event")) {
				try {
					URL url = new URL(
							"https://app.ticketmaster.com/discovery/v2/events/" + s.getEventID() + ".json?" + key.getAPI());
					BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
					String strTemp = "";
					while (null != (strTemp = br.readLine())) {
						//creating JSONObject
						JSONObject jsonEventObject = new JSONObject(strTemp);
						//creating new SearchEvents variable
						SearchEvent se = new SearchEvent();

						try {
							JSONObject temp = (JSONObject) jsonEventObject.get("dates");
							temp = (JSONObject) temp.get("start");
							se.setId((String) jsonEventObject.get("id"));
							se.setUrl((String) jsonEventObject.get("url"));
							se.setName((String) jsonEventObject.get("name"));
							se.setDateTime((String) temp.get("localDate"));
							se.setInfo((String) jsonEventObject.get("info"));
						} catch (JSONException e) {
							e.printStackTrace();
						}
						searchedEvents.add(se);

					}
				} catch (FileNotFoundException ex) {
					System.out.println("File not Found");
				} catch (Exception ex) {
					ex.printStackTrace();
				}
			}
		}
		//giving list of all events saved in a user trip 
		return searchedEvents;
	}

	public static String fetchLngAndLat(GoogleKey key, String city) {
		//instantiating location variables
		double lat = 0;
		double lng = 0;
		try {

			URL url = new URL(
					"https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&key=" + key.getApi());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder();
			String strTemp = "";

			while (strTemp != null) {
				sb.append(strTemp);
				strTemp = br.readLine();
			}
			//creating new JSONObject
			JSONObject tempJsonObject = new JSONObject(sb.toString());
			//creating array filtering by results in google search
			JSONArray jsonGoogleArray = tempJsonObject.getJSONArray("results");
			
			JSONObject jsonGoogleObject = jsonGoogleArray.getJSONObject(0);
			//creating JSONObjects to filter by searched location
			JSONObject temp1 = (JSONObject) jsonGoogleObject.get("geometry");
			JSONObject latnlng = (JSONObject) temp1.get("location");
			//creating variables for latitude + longitude
			lat = (Double) latnlng.get("lat");
			lng = (Double) latnlng.get("lng");
			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		//returning location of searched city 
		return "lat: " + lat + ", lng: " + lng;

	}

	public static ArrayList<PlacesDetails> fetchPlaceDetails(GoogleKey key, ArrayList<tripDetails> places){
		ArrayList<PlacesDetails> listOfPlaces = new ArrayList<PlacesDetails>();
		for (tripDetails s: places){
			PlacesDetails pd = new PlacesDetails();
		try {
			if(s.getTypeOfEvent().equals("event")){
				continue;
			}
			URL url = new URL(
					"https://maps.googleapis.com/maps/api/place/details/json?placeid=" + s.getEventID() + "&key=" + key.getApi());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder();
			String strTemp = "";

			while (strTemp != null) {
				sb.append(strTemp);
				strTemp = br.readLine();
			}
			//creating new JSONObject
			JSONObject resultJsonObject = new JSONObject(sb.toString()).getJSONObject("result");

			//creating array filtering by results in google search
			
			pd.setAddress((String)resultJsonObject.get("formatted_address"));
			pd.setName((String)resultJsonObject.get("name"));
			pd.setPlaceID(s.getEventID());
			pd.setDate(s.getDateOfEvent());
			//creating JSONObjects to filter by searched location
			JSONObject geometry = (JSONObject) resultJsonObject.get("geometry");
			JSONObject latnlng = (JSONObject) geometry.get("location");
			//creating variables for latitude + longitude
			pd.setLat((Double)latnlng.get("lat"));
			pd.setLng((Double)latnlng.get("lng"));
			 listOfPlaces.add(pd);
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		
		}
		//returning location of searched city 

		
		return listOfPlaces;
	}
	
	
}