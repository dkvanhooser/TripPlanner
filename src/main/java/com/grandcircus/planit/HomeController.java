package com.grandcircus.planit;

import java.util.ArrayList;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletRequest;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
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
	
	private static final Logger logger = LoggerFactory.getLogger(HomeController.class);
	
	/**
	 * Simply selects the home view to render by returning its name.
	 */
	@RequestMapping(value = "/", method = RequestMethod.GET)
	public String welcome() {
		return "home";
		
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)

	public String welcomeagain() {
		return "home";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loggingin(Model model) {
		User user = new User();
		model.addAttribute("loginForm", user);
		
		return "login";
	}
		
	@RequestMapping(value = "/login", method = RequestMethod.POST)

	public String userlogincheck(HttpSession session, 
			Model model,
			@ModelAttribute("loginForm") User user, 
			HttpServletResponse response) {
	    
		User checkedUser = DAO.userAndPassValidator(user);
		if(checkedUser != null){
			//set the model's session
			//session.setAttribute("loggedin", "true");
			Cookie username = new Cookie ("username", checkedUser.getUsername());
			Cookie userID = new Cookie("userid", ("" + checkedUser.getID()));
			response.addCookie(username);
			response.addCookie(userID);
			model.addAttribute("userid", checkedUser.getID());
			model.addAttribute("username", checkedUser.getUsername());
			return "checklogin";
		}else{
			return "loginfailed";
		}
}

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(Map<String, Object> model,@RequestParam("search") String city,@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") String dateTo,@CookieValue(value="userid", required=false) Cookie userid){
		TicketmasterKey key = new TicketmasterKey(city,dateFrom,dateTo);
		GoogleKey gkey = new GoogleKey();	
		model.put("gKey", gkey.getApi());
		model.put("latAndLng",FetchURLData.fetchLngAndLat(gkey, city));
		model.put("eventList", FetchURLData.fetchAllEvents(key));
		if(userid instanceof Cookie){
			model.put("trips", DAO.findUserTrips(Integer.parseInt(userid.getValue())));
		}
		
		
		return new ModelAndView("Search","events",model);
	}

	@RequestMapping(value = "/createaccount", method = RequestMethod.GET)
	public String createAccount(Map<String, Object> model) {
		User user = new User();
		model.put("addUser", user);
		DAO.isUsernameTaken(user);
		return "createaccount";
	}

	@RequestMapping(value = "/adduser", method=RequestMethod.POST)    
    public ModelAndView checkPersonInfo(HttpSession session, Model model, @ModelAttribute("addUser") User addUser,HttpServletResponse response) {
        
        if(!DAO.isUsernameTaken(addUser)){
        	DAO.addUser(addUser);
        	Cookie username = new Cookie ("username", addUser.getUsername());
    		Cookie userID = new Cookie("userid", ("" + addUser.getID()));
    		response.addCookie(username);
    		response.addCookie(userID);
    		model.addAttribute("userid", addUser.getID());
    		model.addAttribute("username", addUser.getUsername());
        }else{//please try again re-route to create account
        
        	return new ModelAndView ("loginfailed");
        }
        //return a success page
        return new ModelAndView ("userProfile");
    }

	   @RequestMapping(value = "/savedtrips", method = RequestMethod.GET)
		public String savedtrips()
		{ 
			return "savedtrips";
		}
	   
	   @RequestMapping(value = "/userProfile", method = RequestMethod.GET)

	   public ModelAndView Trips(Map<String, Object> model,
			   @CookieValue("username") Cookie username,
			   @CookieValue("userid") Cookie userid){
	 //need userID from session
		   model.put("tripsearch", new UserTrips());
		   model.put("savedtrips",DAO.findUserTrips(Integer.parseInt(userid.getValue())));
		   
		   return new ModelAndView("userProfile","Profile",model);  

	   }
	   @RequestMapping(value = "/userProfile", method = RequestMethod.POST)
	   public ModelAndView Tripslist(Map<String, Object> model,
			   @CookieValue("username") Cookie username,
			   @CookieValue("userid") Cookie userid, 
			   @ModelAttribute("tripsearch") UserTrips trips){
		   TicketmasterKey key = new TicketmasterKey();
			GoogleKey gkey = new GoogleKey();
			//need to pass in google places arraylist of Strings still.
			model.put("gKey", gkey.getApi());
			ArrayList<tripDetails> ls = DAO.getTripEvents(trips.getTripID());
			model.put("events", FetchURLData.fetchSavedEvents(key,  ls));
	 //need userID from session

		   model.put("savedtrips",DAO.findUserTrips(Integer.parseInt(userid.getValue())));
		   
		   return new ModelAndView("savedtrips","listevents",model);

	   }
	 //here's a handler for the logout request
		@RequestMapping("/logout")
		public ModelAndView accessLogout(@CookieValue("username") Cookie username,@CookieValue("userid") Cookie userid, HttpServletResponse response){
			if(!(username.getValue().equals("null"))){
				username.setMaxAge(0);
				//loggedIn.setValue("false");
				response.addCookie(username);
		}

			if(!(userid.getValue().equals("null"))){
				userid.setMaxAge(0);
				//loggedIn.setValue("false");
				response.addCookie(userid);
		}
			return new ModelAndView("logout");
		}
		
		@RequestMapping(value = "/createTrip", method = RequestMethod.POST)
		public ModelAndView createTrip(Map<String, Object> model, @RequestParam("tripName") String tripName, @CookieValue("userid") Cookie userid){

			UserTrips ut = new UserTrips();
			ut.setTripName(tripName);
			ut.setUserID(Integer.parseInt(userid.getValue()));
			
			DAO.addUserTrips(ut);
			return new ModelAndView("home");
		}
		@RequestMapping(value = "/createTrip", method = RequestMethod.GET)
		public ModelAndView createsTrip(Map<String, Object> model,@RequestParam("tripName") String tripName,@CookieValue("userid") Cookie userid){
			UserTrips ut = new UserTrips();
			ut.setTripName(tripName);
			ut.setUserID(Integer.parseInt(userid.getValue()));
			DAO.addUserTrips(ut);
			return new ModelAndView("home");
		}
		
		@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
		public String filterSearch(HttpServletRequest request, HttpServletResponse response){
			 tripDetails addedEvent = new tripDetails();
			addedEvent.setEventID(request.getParameter("eventID"));
			addedEvent.setTripID(Integer.parseInt(request.getParameter("tripID")));
			addedEvent.setTypeOfEvent(request.getParameter("typeOfEvent"));
			DAO.addEvent(addedEvent);
			
			return "Search";
		}

		@RequestMapping(value = "/modifyTrip", method = RequestMethod.GET)
		public ModelAndView viewAndModifyTrip(Map<String, Object> model, @ModelAttribute("tripsearch") UserTrips trips,@CookieValue("userid") Cookie userid){
			TicketmasterKey key = new TicketmasterKey();
			GoogleKey gkey = new GoogleKey();
			//need to pass in google places arraylist of Strings still.
			model.put("gKey", gkey.getApi());
			model.put("savedTripUserID", trips.getUserID());
			ArrayList<tripDetails> ls = DAO.getTripEvents(trips.getTripID());
			model.put("events", FetchURLData.fetchSavedEvents(key,  ls));
			return new ModelAndView("savedtrips","listevents",model);
		}
		
}