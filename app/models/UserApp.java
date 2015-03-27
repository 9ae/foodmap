package models;

import play.db.jpa.Model;
import javax.persistence.Entity;

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

}