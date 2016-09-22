package com.grandcircus.planit;

import java.util.ArrayList;
import java.util.List;


import javax.persistence.NoResultException;
import javax.validation.ConstraintViolationException;

import org.hibernate.NonUniqueResultException;
import org.hibernate.Query;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.boot.registry.StandardServiceRegistryBuilder;
import org.hibernate.cfg.Configuration;
import org.hibernate.service.ServiceRegistry;

import com.grandcircus.planit.User;
import com.grandcircus.planit.UserTrip;


public class DAO {
	private static SessionFactory factory;

	private static void setupFactory() {
		try {
			Class.forName("com.mysql.jdbc.Driver");
		} catch (Exception e) {
			
		}

		Configuration configuration = new Configuration();
		// modify these to match your XML files
		configuration.configure("hibernate.cfg.xml");
		configuration.addResource("usertrip.hbm.xml");
		configuration.addResource("tripdetails.hbm.xml");
		configuration.addResource("user.hbm.xml");
		ServiceRegistry serviceRegistry = new StandardServiceRegistryBuilder()
				.applySettings(configuration.getProperties()).build();
		factory = configuration.buildSessionFactory(serviceRegistry);
	}
	
	public static User userAndPassValidator(User user){
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		List<User> users = hibernateSession.createQuery("FROM User ").list();
		hibernateSession.getTransaction().commit();
		hibernateSession.close();
		return UserSearch.checkUserAndPass((ArrayList<User>)users, user.getUsername(), user.getPassword());
	}
	public static boolean isUsernameTaken(User user){
		if (factory == null)
			setupFactory();
		Session hibernateSession = factory.openSession();
		hibernateSession.getTransaction().begin();
		try{
			String query = "FROM User WHERE username='"+ user.getUsername()+"'";
			System.out.println(query);
			User singleUser = (User) hibernateSession.createQuery(query).getSingleResult();
			System.out.println("Found: " + singleUser.getUsername());
		}catch(Exception e){
			System.out.println("Exception: " + e);
			return false;
		}
		
		//no exception = there was a single result
		return true;
	}
	
	public static String addUser(User u) {
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 hibernateSession.save(u);  
		 hibernateSession.getTransaction().commit();
		 hibernateSession.close();  
				    
		 return null;  
	}
	public static boolean addEvent(String tripId, String eventId){
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 String sqlquer = "INSERT INTO tripDetails(tripID) VALUES("+tripId + " , "+ eventId +")";
		 Query query = hibernateSession.createQuery(sqlquer);
		 query.executeUpdate();
		 hibernateSession.getTransaction().commit();
		 hibernateSession.close();  
		
		return false;
	}

	public static List<UserTrip> findUserTrips(String userId){
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 String sqlquer = "SELECT * FROM userTrips WHERE userID =%s";
		 sqlquer = String.format(sqlquer,userId);
		 List<UserTrip> userTrips= hibernateSession.createQuery(sqlquer).getResultList();
			hibernateSession.getTransaction().commit();
			hibernateSession.close();  
		
		return userTrips;
	}
	public static List<tripDetails> getUserTrips(String tripID){
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 String sqlquer = "FROM tripDetails WHERE tripID =%s";
		 sqlquer = String.format(sqlquer,tripID);
		 List<tripDetails> details= hibernateSession.createQuery(sqlquer, tripDetails.class).getResultList();
			hibernateSession.getTransaction().commit();
			hibernateSession.close();  
		
		return details;
	}

	public static String addUserTrips(UserTrip t) {
		if (factory == null)
			setupFactory();
		 Session hibernateSession = factory.openSession();
		 hibernateSession.getTransaction().begin();
		 hibernateSession.save(t);  
		 hibernateSession.getTransaction().commit();
		 hibernateSession.close();  
				    
		 return null;  
	}
} 