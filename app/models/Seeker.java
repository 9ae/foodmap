package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;

import java.util.*;

import jobs.SeekerJob;

@Entity
public class Seeker extends Model {
	
	public Calendar created_at;
	
	@ManyToOne
	public UserApp created_by;
	
	public String tag;
	
	public double lat;
	public double lon;
	public String city;
	
	public boolean completed;
	
	@Transient
	public String coords;
	
	public Seeker(UserApp user, String city, String lat, String lon){
		
		created_at = Calendar.getInstance();
		tag = "food";
		completed = false;
		
		created_by = user;
		
		this.city = city;
		
		this.lat = Double.parseDouble(lat);
		this.lon = Double.parseDouble(lon);
		
		this.coords = lat+","+lon;
		
	}
	
	public JsonObject toJson(){
		JsonObject jay = new JsonObject();
		jay.addProperty("id", this.id);
		return jay;
	}
	
	public JsonArray getResults(){
		JsonArray results = new JsonArray();
		TypedQuery<SeekerResult> query = JPA.em().createQuery("SELECT sr FROM SeekerResult sr WHERE seeker.id=:seeker_id AND retrieved=false", SeekerResult.class);
		query.setParameter("seeker_id", this.id);
		List<SeekerResult> seekerResults = query.getResultList();
		for(SeekerResult sr : seekerResults){
			JsonObject result = new JsonObject();
			Venue venue = sr.image.venue;
			result.addProperty("image_url", sr.image.getImageUrl());
			result.addProperty("venue_name", venue.name);
			result.addProperty("venue_url", venue.getURL());
			results.add(result);
			sr.retrieved = true;
			sr.save();
		}
		return results;
	}
    
}
