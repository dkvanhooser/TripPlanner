package com.grandcircus.planit;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.json.JSONArray;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.CookieValue;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.servlet.ModelAndView;
//import com.grandcircus.planit.TicketmasterKey;

import com.grandcircus.planit.resources.GoogleKey;
import com.grandcircus.planit.resources.TicketmasterKey;

/**
 * Handles requests for the application home page.
 */
@Controller
public class HomeController {
	
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	//front page of site
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "home";
		
	}
	//front page of site (home.jsp)
	@RequestMapping(value = "/home", method = RequestMethod.GET)

	public String welcomeagain() {
		return "home";
	}
	//sends to login.jsp when login button on home page is clicked, 
	//returns back to login page and posts user information
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loggingin(Model model, @CookieValue(value="username", required=false) Cookie username, @CookieValue(value="userid", required=false) Cookie userid) {
		model.addAttribute("loginForm", new User());
	//	if(!(username.getValue().equals("none"))){
	//		return "accessdenied";
	//	}else if(!(userid.getValue().equals("none"))){
	//		return "accessdenied";
	//	}else
		if (username == null || userid == null)
			return "login";
		return "alreadylogged";
	}
	//gets login information typed by user
	@RequestMapping(value = "/login", method = RequestMethod.POST)
	public String userlogincheck(HttpSession session, 
			Model model,
			@ModelAttribute("loginForm") User user, 
			HttpServletResponse response) {
	    //checking to see if username/password match in database
		
		User checkedUser = DAO.userAndPassValidator(user);
		if(checkedUser != null){
			//making cookies for username + userID
			Cookie username = new Cookie ("username", checkedUser.getUsername());
			Cookie userID = new Cookie("userid", ("" + checkedUser.getID()));
			//adding cookies for both username and ID 
			response.addCookie(username);
			response.addCookie(userID);
			//adding values to model 
			model.addAttribute("userid", checkedUser.getID());
			model.addAttribute("username", checkedUser.getUsername());
			
			//sends to checklogin.jsp
			return "checklogin";
		}else{
			//sends to loginfailed.jsp
			return "loginfailed";
		}
}

	//mapping for search page on website

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(Map<String, Object> model,@RequestParam("search") String city,@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") String dateTo,@CookieValue(value="userid", required=false) Cookie userid){
		//creating new  ticketmaster key variable 
		TicketmasterKey key = new TicketmasterKey(city,dateFrom,dateTo);
		//creating new google key variable 
		GoogleKey gkey = new GoogleKey();
		//retrieving API key from java file
		ArrayList<SearchEvent> al = FetchURLData.fetchAllEvents(key);
		model.put("genres", DAO.getDistinctGenres(al));
		model.put("gKey", gkey.getApi());
		//putting searched city and events from search into model
		model.put("latAndLng",FetchURLData.fetchLngAndLat(gkey, city));
		model.put("eventList", al);
		//if cookie exists adds saved trips to the model from the DAO 
		if(userid instanceof Cookie){
			model.put("trips", DAO.findUserTrips(Integer.parseInt(userid.getValue())));
		}
		
		//returning to search.jsp adding events and model made in search method 
		return new ModelAndView("Search","events",model);
	}
	
	//mapping to create account page 
	@RequestMapping(value = "/createaccount", method = RequestMethod.GET)
	public String createAccount(Map<String, Object> model) {
		User user = new User();
		//adding new user to model
		model.put("addUser", user);
		DAO.isUsernameTaken(user);
		//sending to create account form page 
		return "createaccount";
	}
	//mapping for retrieving user input into 
	@RequestMapping(value = "/adduser", method=RequestMethod.POST)    
    public ModelAndView checkPersonInfo(HttpSession session, Model model, @ModelAttribute("addUser") User addUser,HttpServletResponse response) {
        //testing to see if username taken
        if(!DAO.isUsernameTaken(addUser)){
        	DAO.addUser(addUser);
        	//creating new cookie with username + user id
        	Cookie username = new Cookie ("username", addUser.getUsername());
    		Cookie userid = new Cookie("userid", ("" + addUser.getID()));
    		response.addCookie(username);
    		response.addCookie(userid);
    		//adding values to model with ID/Username
    		model.addAttribute("userid", addUser.getID());
    		model.addAttribute("username", addUser.getUsername());
        }else{
        	//please try again re-route to create account (loginfailed.jsp)
        	return new ModelAndView ("loginfailed");
        }
        //return a success page(userprofile.jsp)
        return new ModelAndView ("userProfile");
    }
		//mapping to saved trips page from user profile
	   @RequestMapping(value = "/savedtrips", method = RequestMethod.GET)
		public ModelAndView savedtrips(Map<String, Object> model,
				   @CookieValue(value = "username", required = false) Cookie username,
				   @CookieValue(value = "userid", required = false) Cookie userid, @RequestParam("tripID") String tripid)
		{ 
		   UserTrips ut = DAO.getUserTrip(Integer.parseInt(tripid));
		   if(ut.getPrivacy() == 0 ){
			   if(ut.getUserID() != Integer.parseInt(userid.getValue())){
				   return new ModelAndView("accessdenied");
			   }
		   }
			   //creating new key variables for TM and google
			    TicketmasterKey key = new TicketmasterKey();
				GoogleKey gkey = new GoogleKey();
				//need to pass in google places arraylist of Strings still.(current)
				//putting API key into model so it can be passed into JSP page
				model.put("gKey", gkey.getApi());
				ArrayList<tripDetails> ls = DAO.getTripEvents(Integer.parseInt(tripid));
				model.put("events", FetchURLData.fetchSavedEvents(key,  ls));

				ArrayList<PlacesDetails> savedPlacesDetails = FetchURLData.fetchPlaceDetails(gkey, ls);
				model.put("jsonPlaces", new JSONArray(savedPlacesDetails));
				model.put("places", savedPlacesDetails);
				//putting searched events into model 
				model.put("savedtrip",tripid);
	           
	           return new ModelAndView("savedtrips","listevents",model);
		}
	   //mapping at user profiles page 
	   @RequestMapping(value = "/userProfile", method = RequestMethod.GET)
	   //creating model taking cookies
	   public ModelAndView Trips(Map<String, Object> model,
			   @CookieValue("username") Cookie username,
			   @CookieValue("userid") Cookie userid){

		   //adding saved trips and user searches to model 
		   model.put("tripsearch", new UserTrips());
		   model.put("savedtrips",DAO.findUserTrips(Integer.parseInt(userid.getValue())));
		   //returns back to user profile page, and gives user profile with saved trips information
		   return new ModelAndView("userProfile","Profile",model);  

	   }

	   //mapping for information fed back to userProfile page
	   @RequestMapping(value = "/userProfile", method = RequestMethod.POST)
	   public ModelAndView Tripslist(Map<String, Object> model,
			   @CookieValue("username") Cookie username,
			   @CookieValue("userid") Cookie userid, 
			   @ModelAttribute("tripsearch") UserTrips trips){
		   if(trips.getUserID() != Integer.parseInt(userid.getValue()))
			   return new ModelAndView("accessdenied","tripsearch",model);
		   model.put("tripsearch", new UserTrips());
		   //creating new key variables for TM and google
		    TicketmasterKey key = new TicketmasterKey();
			GoogleKey gkey = new GoogleKey();
			//need to pass in google places arraylist of Strings still.(current)
			//putting API key into model so it can be passed into JSP page
			model.put("gKey", gkey.getApi());
			ArrayList<tripDetails> ls = DAO.getTripEvents(trips.getTripID());
			model.put("events", FetchURLData.fetchSavedEvents(key,  ls));

			ArrayList<PlacesDetails> savedPlacesDetails = FetchURLData.fetchPlaceDetails(gkey, ls);
			model.put("jsonPlaces", new JSONArray(savedPlacesDetails));
			model.put("places", savedPlacesDetails);
			//putting searched events into model
			trips = DAO.getUserTrip(trips.getTripID());
			model.put("savedtrip",trips);
			
		   if(trips.getUserID() != Integer.parseInt(userid.getValue()))
			   return new ModelAndView("accessdenied","tripsearch",model);
           
           return new ModelAndView("savedtrips","listevents",model);
	   }
	   

	  //here's a handler for mapping logout request
		@RequestMapping("/logout")
		public ModelAndView accessLogout(@CookieValue("username") Cookie username,@CookieValue("userid") Cookie userid, HttpServletResponse response){
			//as long as username destroys made cookie
			if(!(username.getValue().equals("null"))){
				username.setMaxAge(0);
				response.addCookie(username);
		}
			//as long as id exists destroys made cookie
			if(!(userid.getValue().equals("null"))){
				userid.setMaxAge(0);
				response.addCookie(userid);
		}
			//sends to logout page 
			return new ModelAndView("logout");
		}
		
		//mapping for user making new trip + receiving trip information 
		@RequestMapping(value = "/createTrip", method = RequestMethod.POST)
		public ModelAndView createTrip(Map<String, Object> model, @RequestParam("tripName") String tripName, @CookieValue("userid") Cookie userid){
			//makes new trip, adds name and users ID
			UserTrips ut = new UserTrips();
			ut.setTripName(tripName);
			ut.setUserID(Integer.parseInt(userid.getValue()));
			//combines user id to user trips  
			DAO.addUserTrips(ut);
			//sends back to home page 
			return new ModelAndView("home");
		}
		//mapping to get to create trip page 
		@RequestMapping(value = "/createTrip", method = RequestMethod.GET)
		public ModelAndView createsTrip(Map<String, Object> model,@RequestParam("tripName") String tripName,@CookieValue("userid") Cookie userid){
			UserTrips ut = new UserTrips();
			ut.setTripName(tripName);
			ut.setUserID(Integer.parseInt(userid.getValue()));
			DAO.addUserTrips(ut);
			//send back to home page 
			return new ModelAndView("home");
		}
		//mapping to add event to tripID
		@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
		public String filterSearch(HttpServletRequest request, HttpServletResponse response){
			tripDetails addedEvent = new tripDetails();
			//getting eventID, trip ID, and event type 
			addedEvent.setEventID(request.getParameter("eventID"));
			addedEvent.setTripID(Integer.parseInt(request.getParameter("tripID")));
			addedEvent.setTypeOfEvent(request.getParameter("typeOfEvent"));
			if(request.getParameter("date")!=null)
				addedEvent.setDateOfEvent(request.getParameter("date"));
			DAO.addEvent(addedEvent);
			return "Search";
		}
		@RequestMapping(value = "/deleteEvent", method = RequestMethod.POST)
		public String deleteEvent(HttpServletRequest request, HttpServletResponse response){
			//creating new variable to add event  
			tripDetails event = new tripDetails();
			//getting eventID, trip ID, and event type 
			
			event.setEventID(request.getParameter("eventID"));
			event.setTripID(Integer.parseInt(request.getParameter("tripID")));
			if(request.getParameter("typeOfEvent") != null)
				event.setTypeOfEvent(request.getParameter("typeOfEvent"));
			
			//putting event into tripDetails
			DAO.deleteEvent(event);
			//sending back to search page 
			return "Search";
		}
		@RequestMapping(value = "/updatePrivacy", method = RequestMethod.POST)
		public String filterSedarch(HttpServletRequest request, HttpServletResponse response){
			//getting eventID, trip ID, and event type 
			DAO.privacyUpdate(Integer.parseInt(request.getParameter("privacy")),Integer.parseInt(request.getParameter("tripID")));
			return "Search";
		}
		//mapping to allow user to change saved trips 
//		@RequestMapping(value = "/modifyTrip", method = RequestMethod.GET)
//
//		public ModelAndView viewAndModifyTrip(Map<String, Object> model, @ModelAttribute("tripsearch") UserTrips trips,@CookieValue("userid") Cookie userid){
//			//creating new variables to hold API keys
//			TicketmasterKey key = new TicketmasterKey();
//			GoogleKey gkey = new GoogleKey();
//			//need to pass in google places arraylist of Strings still.(current)
//		
//			//adding Keys to models 
//			model.put("gKey", gkey.getApi());
//			//putting savedTripUserID into model and setting value to UserID
//			model.put("savedTripUserID", trips.getUserID());
//			//creating arraylist with trip events using specified tripID
//			ArrayList<tripDetails> ls = DAO.getTripEvents(trips.getTripID());
//			model.put("events", FetchURLData.fetchSavedEvents(key,  ls));
//			if(trips.getUserID() != Integer.parseInt(userid.getValue())){			
//				return new ModelAndView("accessdenied","tripsearch",model);
//			}
//			
//			model.put("events", FetchURLData.fetchSavedEvents(key, DAO.getTripEvents(trips.getTripID())));
//			
//			//sending back to saved trips page with list of events 
//			return new ModelAndView("savedtrips","listevents",model);
//		}
		@RequestMapping(value = "/alreadylogged", method = RequestMethod.GET)
		public String alreadylogged() {
			return "alreadylogged";
		}
		

		
}
