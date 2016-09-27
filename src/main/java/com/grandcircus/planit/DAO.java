package com.grandcircus.planit;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.SharedSessionContract;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;
import org.jasypt.util.password.BasicPasswordEncryptor;

import com.grandcircus.planit.User;
import com.grandcircus.planit.UserTrips;


public class DAO {
	private static SessionFactory factory;
	
	//connection to mysql program
	private static void setupFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			
		}	
		//connecting to databases made in mysql 
		Configuration configuration = new Configuration();
		// matching POJO created to hibernate
		configuration.configure("hibernate.cfg.xml");
		configuration.addResource("usertrip.hbm.xml");
		configuration.addResource("tripdetails.hbm.xml");
		configuration.addResource("user.hbm.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	//hibernate session for user login 
	public static User userAndPassValidator(User user){
		if (factory == null)
			setupFactory();
		//get current session 
		Session hibernateSession = factory.openSession();
		//Transaction begins
		hibernateSession.getTransaction().begin();
		//searching users table
		List<User> users = hibernateSession.createQuery("FROM User ").list();
		return UserSearch.checkUserAndPass((ArrayList<User>)users, user.getUsername(), user.getPassword());
	}// rework to search for username = username that is passed in and then check is the received password is the same as the one in the database
	//hibernate session to test if username is taken
	public static boolean isUsernameTaken(User user){
		if (factory == null)
			setupFactory();
		//get current session
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		try{
			//searching users table to get matching username
			String query = "FROM User WHERE username='"+ user.getUsername()+"'";
			User singleUser = (User) hibernateSession.createQuery(query).getSingleResult();
		}catch(Exception e){
			System.out.println("Exception: " + e);
			return false;
		}
		
		//no exception = there was a single result
		return true;
	}
	
	//adding user to database and encrypting their password
	public static String addUser(User u) {
		if (factory == null)
			setupFactory();
		BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
		u.setPassword(passwordEncryptor.encryptPassword(u.getPassword()));
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 hibernateSession.save(u);  
		 hibernateSession.getTransaction().commit();
		 hibernateSession.close();  
				    
		 return null;  
	}
	//adding an event into trip details table
	public static boolean addEvent(tripDetails newEvent){
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 hibernateSession.save(newEvent); 
		 hibernateSession.getTransaction().commit();
		 hibernateSession.close();  
		
		return false;
	}
	//deleting an event out of users tripdetails table
	public static boolean deleteEvent(tripDetails eventToDelete){
		 if (factory == null)
			setupFactory();
		 Session hibernateSession;
		 try {
			hibernateSession = factory.openSession();
			hibernateSession.getTransaction().begin();
			String sqlquer = "DELETE FROM tripDetails WHERE tripID =" + eventToDelete.getTripID()
					+ " AND eventID = '" + eventToDelete.getEventID() + "'";
			Query query = hibernateSession.createQuery(sqlquer);
			query.executeUpdate();
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
			 
		} finally {
			
			
		}
		return true;
	}
	
	// session to list all user's saved trips in database
	public static List<UserTrips> findUserTrips(int userId){
		if (factory == null)
			setupFactory();
		
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 String sqlquer = "FROM "+UserTrips.class.getName() +" WHERE userID=%s";
		 System.out.println(userId);
		 sqlquer = String.format(sqlquer,userId);
		 List<UserTrips> userTrips= (List<UserTrips>)hibernateSession.createQuery(sqlquer, UserTrips.class).getResultList();
			hibernateSession.getTransaction().commit();
			hibernateSession.close();  
		//gets list of all trips for specific user 
		return userTrips;
	}
	//getting trip details (different events) from user saved trips table 
	public static ArrayList<tripDetails> getTripEvents(int tripID){
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 String sqlquer = "FROM tripDetails WHERE tripID=%s";
		 sqlquer = String.format(sqlquer,tripID);
		 ArrayList<tripDetails> details = (ArrayList<tripDetails>)hibernateSession.createQuery(sqlquer, tripDetails.class).getResultList();
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
		
		return details;
	}
	
	//saving a user trip 
	public static String addUserTrips(UserTrips t) {
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 hibernateSession.save(t);  
		 hibernateSession.getTransaction().commit();
		 hibernateSession.close();  
				    
		 return null;  
	}

	//checking for associated google places from tripDetails list 
	public static ArrayList<String> getPlacesList(ArrayList<tripDetails> events){
		ArrayList<String> listOfPlaces = new ArrayList<String>();
		for(tripDetails e: events){
		//adding to array list of google places
		if (e.getTypeOfEvent().equals("place")) {
			listOfPlaces.add(e.getEventID());
			}
		}
		//returns an array of google places
		return listOfPlaces;
	}
	
}