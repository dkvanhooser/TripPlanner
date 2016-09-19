package com.grandcircus.planit;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
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
	public String log() {
		return "login";
	}
	@RequestMapping(value = "/checklogin", method = RequestMethod.GET)
	public String checklog() {
		return "checkloginlogin";
	}
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search()
	{
		return "Search";
	}
}
