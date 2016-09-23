package com.grandcircus.planit;

import java.io.BufferedReader;
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

	public static ArrayList<SearchEvent> fetchAllEvents(TicketmasterKey key) {
		ArrayList<SearchEvent> searchedEvents = new ArrayList<SearchEvent>();
		try {

			URL url = new URL(key.getAPI());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			String strTemp = "";
			while (null != (strTemp = br.readLine())) {

				JSONObject tempJsonObject = new JSONObject(strTemp);
				JSONObject embedded = tempJsonObject.getJSONObject("_embedded");

				JSONArray jsonEventArray = embedded.getJSONArray("events");
				for (int i = 0; i < jsonEventArray.length(); i++) {
					SearchEvent se = new SearchEvent();
					JSONObject jsonEventObject = jsonEventArray.getJSONObject(i);
					try {
						JSONObject temp = (JSONObject) jsonEventObject.get("dates");
						temp = (JSONObject) temp.get("start");

						se.setId((String) jsonEventObject.get("id"));
						se.setUrl((String) jsonEventObject.get("url"));
						se.setName((String) jsonEventObject.get("name"));
						se.setDateTime((String) temp.get("localDate"));
						se.setInfo((String) jsonEventObject.get("info"));
					} catch (JSONException e) {

					}
					searchedEvents.add(se);
				}

			}
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return searchedEvents;
	}

	public static ArrayList<SearchEvent> fetchSavedEvents(TicketmasterKey key, ArrayList<String> event) {
		ArrayList<SearchEvent> searchedEvents = new ArrayList<SearchEvent>();

		for (String s : event) {
			try {
				URL url = new URL("https://app.ticketmaster.com/discovery/v2/events/" + s + ".json?" + key.getAPI());
				BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
				String strTemp = "";
				while (null != (strTemp = br.readLine())) {

					JSONObject jsonEventObject = new JSONObject(strTemp);
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

					}
					searchedEvents.add(se);

				}
			} catch (Exception ex) {
				ex.printStackTrace();
			}
			
		}

		return searchedEvents;
	}

	public static String fetchLngAndLat(GoogleKey key, String city) {
		double lat = 0;
		double lng= 0;
		try {

			URL url = new URL(
					"https://maps.googleapis.com/maps/api/geocode/json?address=" + city + "&key=" + key.getApi());
			BufferedReader br = new BufferedReader(new InputStreamReader(url.openStream()));
			StringBuilder sb = new StringBuilder();
			String strTemp = "";

			while (strTemp != null) {
				sb.append(strTemp);
				strTemp = br.readLine();
			}				JSONObject tempJsonObject = new JSONObject(sb.toString());
				JSONArray jsonGoogleArray = tempJsonObject.getJSONArray("results");
				
					JSONObject jsonGoogleObject = jsonGoogleArray.getJSONObject(0);
					JSONObject temp1 = (JSONObject) jsonGoogleObject.get("geometry");
					JSONObject latnlng = (JSONObject) temp1.get("location");
					lat = (Double) latnlng.get("lat");
					lng = (Double) latnlng.get("lng");

				

			
		} catch (Exception ex) {
			ex.printStackTrace();
		}
		return "lat: "+lat+", lng: "+lng;

	}

}