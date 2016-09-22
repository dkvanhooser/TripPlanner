package com.grandcircus.planit;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.servlet.http.Cookie;
import javax.servlet.http.HttpServletResponse;
import javax.servlet.http.HttpSession;
import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

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
		if(DAO.userAndPassValidator(user)){
			//set the model's session
			//session.setAttribute("loggedin", "true");
			Cookie username = new Cookie ("username", user.getUsername());
			Cookie userID = new Cookie("userid", "" + user.getID());
			response.addCookie(username);
			response.addCookie(userID);
			model.addAttribute("username", user.getUsername());
			return "checklogin";
		}else{
			return "loginfailed";
		}
}

//	@RequestMapping(value = "/checklogin", method = RequestMethod.POST)
//	public String checklog() {
//		
//		return "checklogin";
//	}
//	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
//	public String failedlogin() {
//		
//		return "loginfailed";
//	}
	

	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public ModelAndView search(Map<String, Object> model,@RequestParam("search") String city,@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") String dateTo){
		TicketmasterKey key = new TicketmasterKey(city,dateFrom,dateTo);
		GoogleKey gkey = new GoogleKey();	
		model.put("gKey", gkey.getApi());
		model.put("latAndLng",FetchURLData.fetchLngAndLat(gkey, city));
		model.put("eventList", FetchURLData.fetchAllEvents(key));
		
		return new ModelAndView("Search","events",model);
	}
	@RequestMapping(value = "/search", method = RequestMethod.POST)
	public ModelAndView filterSearch(Map<String, Object> model,@RequestParam("search") String city,@RequestParam("dateFrom") String dateFrom,@RequestParam("dateTo") String dateTo){
		TicketmasterKey key = new TicketmasterKey(city,dateFrom,dateTo);
		GoogleKey gkey = new GoogleKey();		
		model.put("latAndLng",FetchURLData.fetchLngAndLat(gkey, city));
		model.put("eventList", FetchURLData.fetchAllEvents(key));

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
    public ModelAndView checkPersonInfo(Map<String, Object> model, @ModelAttribute("addUser") User addUser) {
    		
    //validate user info?

        logger.info("form submitted");
        logger.info("Username: " + addUser.getUsername());
        logger.info("Email: " + addUser.getEmail());
        logger.info("Password: " + addUser.getPassword());
        
        if(!DAO.isUsernameTaken(addUser)){
        	DAO.addUser(addUser);
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
	   public String Trips()
	   {
		   return "Trips";
	   }

		/*@RequestMapping(value = "/addEvent", method = RequestMethod.POST)
		public ModelAndView filterSearch1(Map<String, Object> model,@RequestParam("trip") String tripId,@RequestParam("eventId") String eventId){
			DAO.addEvent(tripId, eventId);
			
			return new ModelAndView("Search");
<<<<<<< HEAD
		} */

		
		@RequestMapping(value = "/logout", method = RequestMethod.GET)
		public String loggingout(Model model) {
			User user = new User();
			model.addAttribute("logout", user);
			
			return "logout";
		}

}
//is the username a valid username (validation)
//does the username exist in the database call the DAO
//create new username in database

//   @RequestMapping(value = "/adduser", method=RequestMethod.POST)
//public ModelAndView checkPersonInfo(@Valid @ModelAttribute("addUser") User addUser, BindingResult bindingResult) {

//if (bindingResult.hasErrors()) {
//		logger.info("Erroneous form submitted");
//		logger.info(bindingResult.toString());
//		ModelAndView result = new ModelAndView("createaccount");
//		result.addObject("addUser", addUser);
//		FieldError usernameError = bindingResult.getFieldError("username"); 
//		String usernameErrString = "";
//		if (usernameError != null) {
//			usernameErrString = usernameError.getDefaultMessage();
//		}
//			
//		String passwordErrString = "";
//		FieldError passwordError = bindingResult.getFieldError("password");
//		if (passwordError != null) {
//			passwordErrString = passwordError.getDefaultMessage();
//		}
//		String emailErrString = "";
//		FieldError emailError = bindingResult.getFieldError("email");
//		if (emailError != null) {
//			emailErrString = emailError.getDefaultMessage();
//		}
//    result.addObject("usernameError", usernameErrString);
//    result.addObject("passwordError", passwordErrString);
//    result.addObject("emailError", emailErrString);
//        
//		return result;
//}
//
//logger.info("correct form submitted");
//return new ModelAndView ("result");
//}
