package com.grandcircus.planit;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.HashSet;
import java.util.List;
import java.util.Set;
import java.util.TreeSet;

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
		try {
			String query = "FROM User WHERE username='" + user.getUsername() + "'";
			User singleUser = (User) hibernateSession.createQuery(query).getSingleResult();
			BasicPasswordEncryptor passwordEncryptor = new BasicPasswordEncryptor();
			if (passwordEncryptor.checkPassword(user.getPassword(), singleUser.getPassword())) {
				hibernateSession.getTransaction().commit();
				hibernateSession.close();
				return singleUser;
			} else {
				hibernateSession.getTransaction().commit();
				hibernateSession.close();
				return null;
			}
		} catch (Exception e) {
			System.out.println("Exception: " + e);
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
			}
		
		
		return null;
	}

	public static boolean isUsernameTaken(User user) {
		if (factory == null)
			setupFactory();
		//get current session
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		try {
			String query = "FROM User WHERE username='" + user.getUsername() + "'";
			User singleUser = (User) hibernateSession.createQuery(query).getSingleResult();
			System.out.println("Found: " + singleUser.getUsername());
		} catch (Exception e) {

			System.out.println("Exception: " + e);
			hibernateSession.close();  
			return false;
			
		}

		// no exception = there was a single result
		hibernateSession.close(); 
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
		sqlquer = String.format(sqlquer, tripID);
		ArrayList<tripDetails> details = (ArrayList<tripDetails>) hibernateSession
				.createQuery(sqlquer, tripDetails.class).getResultList();
		hibernateSession.getTransaction().commit();
		hibernateSession.close();

		return details;
	}
	
	//saving a user trip 
	public static void addUserTrips(UserTrips t) {
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		try{
			hibernateSession.save(t);
		
		}catch(Exception e){
			hibernateSession.getTransaction().commit();
			hibernateSession.close();
		}
		hibernateSession.getTransaction().commit();
		hibernateSession.close();

		
	}
	public static Set<String> getDistinctGenres(ArrayList<SearchEvent> searchedEvents){
		ArrayList<String> genres = new ArrayList<String>();
		for(SearchEvent s: searchedEvents){
			if (s.getGenre() != null){
				genres.add(s.getGenre());
				
			}
			
		}		
		
		return new TreeSet<String>(genres);
	}
	
	public static void privacyUpdate(int privacy, int tripID){
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		String sqlquer = "UPDATE UserTrips SET privacy = "+privacy+" WHERE tripID = "+tripID;
		Query query  = hibernateSession.createQuery(sqlquer);
		query.executeUpdate();
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
	}
	public static UserTrips getUserTrip(int tripID) {
		if (factory == null)
			setupFactory();
		UserTrips usertrip;
		//get current session
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		try {
			String query = "FROM UserTrips WHERE tripID=" + tripID;
			 usertrip = (UserTrips) hibernateSession.createQuery(query).getSingleResult();
		} catch (Exception e) {

			System.out.println("Exception: " + e);
			hibernateSession.close();  
			return null;
			
		}
		hibernateSession.close(); 
		// no exception = there was a single result
		return usertrip;
	}
	
}
