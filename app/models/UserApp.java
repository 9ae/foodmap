package models;

import play.db.jpa.JPA;
import play.db.jpa.Model;

import javax.persistence.Entity;
import javax.persistence.NoResultException;
import javax.persistence.TypedQuery;

import java.util.UUID;

@Entity
public class UserApp extends Model{
	
	public String aii;
	public String authToken;

	public UserApp(String aii){
		this.aii = aii;
		//TODO: check against database it is unique
		this.authToken = UUID.randomUUID().toString();

	}
	
	public static UserApp findByAuthToken(String token){
		TypedQuery<UserApp> query = JPA.em().createQuery("SELECT ua FROM UserApp ua WHERE ua.authToken=:token", UserApp.class);
		query.setParameter("token", token);
		query.setMaxResults(1);
		try{
			return query.getSingleResult();
		} catch (NoResultException ex){
			return null;
		}
		
	}

}