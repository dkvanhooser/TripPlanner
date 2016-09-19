package com.grandcircus.planit;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;

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
	public String welcome(Map<String, Object> model) {
		Search search = new Search();
		model.put("searchForm", search);
		return "home";
	}
	@RequestMapping(value = "/login", method = RequestMethod.GET)
	public String loggingin(Map<String, Object> model) {
		User user = new User();
		model.put("loginForm", user);
		
		return "login";
	}
		
		@RequestMapping(value = "/login", method = RequestMethod.POST)
		public String userlogincheck(Map<String, Object> model, @ModelAttribute("loginForm") User user) {
			if(DAO.userAndPassValidator(user)){
				return "checklogin";
			}else{
			return "loginfailed";
			}
	}
	@RequestMapping(value = "/checklogin", method = RequestMethod.POST)
	public String checklog() {
		
		return "checklogin";
	}
	@RequestMapping(value = "/loginfailed", method = RequestMethod.GET)
	public String failedlogin() {
		
		return "loginfailed";
	}
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String welcomeagain(Map<String, Object> model) {
		Search search = new Search();
		model.put("searchForm", search);
		return "home";
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search()
	{
		return "Search";
	}
}
