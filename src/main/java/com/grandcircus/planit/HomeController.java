package com.grandcircus.planit;

import java.text.DateFormat;
import java.util.Date;
import java.util.Locale;
import java.util.Map;

import javax.validation.Valid;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;

import org.springframework.web.bind.annotation.ModelAttribute;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.servlet.ModelAndView;

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
	@RequestMapping(value = "/home", method = RequestMethod.GET)
	public String welcomeagain(Map<String, Object> model) {
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
	@RequestMapping(value = "/search", method = RequestMethod.GET)
	public String search()
	{
		return "Search";
	}
	@RequestMapping(value = "/createaccount", method = RequestMethod.GET)
	public String createAccount(Map<String, Object> model) {
		User user = new User();
		model.put("createAccount", user);
		DAO.isUsernameTaken(user);
		return "createaccount";

	}
	//is the username a valid username (validation)
	//does the username exist in the database call the DAO
	//create new username in database
	
 //   @RequestMapping(value = "/adduser", method=RequestMethod.POST)
    //public ModelAndView checkPersonInfo(@Valid @ModelAttribute("addUser") User addUser, BindingResult bindingResult) {
	
//    if (bindingResult.hasErrors()) {
//    		logger.info("Erroneous form submitted");
//    		logger.info(bindingResult.toString());
//    		ModelAndView result = new ModelAndView("createaccount");
//    		result.addObject("addUser", addUser);
//    		FieldError usernameError = bindingResult.getFieldError("username"); 
//    		String usernameErrString = "";
//    		if (usernameError != null) {
//    			usernameErrString = usernameError.getDefaultMessage();
//    		}
//    			
//    		String passwordErrString = "";
//    		FieldError passwordError = bindingResult.getFieldError("password");
//    		if (passwordError != null) {
//    			passwordErrString = passwordError.getDefaultMessage();
//    		}
//    		String emailErrString = "";
//    		FieldError emailError = bindingResult.getFieldError("email");
//    		if (emailError != null) {
//    			emailErrString = emailError.getDefaultMessage();
//    		}
//        result.addObject("usernameError", usernameErrString);
//        result.addObject("passwordError", passwordErrString);
//        result.addObject("emailError", emailErrString);
//            
//    		return result;
//    }
//
//    logger.info("correct form submitted");
//    return new ModelAndView ("result");
//}

	   @RequestMapping(value = "/adduser", method=RequestMethod.POST)    
    public ModelAndView checkPersonInfo(@ModelAttribute("addUser") User addUser) {
    		
    //validate user info?

        logger.info("form submitted");
        logger.info("Username: " + addUser.getUsername());
        logger.info("Email: " + addUser.getEmail());
        logger.info("Password: " + addUser.getPassword());
        
        //check if the username alreadys exist (DAO)
        DAO.isUsernameTaken(addUser);
        //now call the DAO to store this user (addUser)
        DAO.addUser(addUser);
       
        //return a success page
        return new ModelAndView ("result");
    }
//    @RequestMapping(method=RequestMethod.GET)
//    public String add(@Valid @ModelAttribute("adduser") User addUser, BindingResult bindingResult) {
//    return "Account Created";
//    }
    
}
