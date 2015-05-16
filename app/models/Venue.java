package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Venue extends Model {
    
	public String providerVenueId;
	
	@ManyToOne
	public Provider provider;
	
	public String name;
	
	public Double lat;
	public Double lon;
	
	public Calendar lastImagesUpdate;
	
	public Venue(String providerVenueId, Provider provider, String name, double lat, double lon){
		this.providerVenueId = providerVenueId;
		this.provider = provider;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		
		lastImagesUpdate = Calendar.getInstance();
	}
	
	public static Venue findByProviderId(String providerShortname, String id){
		return Venue.find("provider.shortname=? AND providerVenueId=?", providerShortname, id).first();
	}

	public Image getBestPictureOfTag(String string) {
		// TODO Auto-generated method stub
		return null;
	}
	
	public String getURL(){
		//TODO get provider's url for venue
		return "";
	}
	
	public String getMenuUrl(){
		if (this.provider.shortname.equals("yelp")){
			return "http://www.yelp.com/menu/"+this.providerVenueId;
		} else {
			return null;
		}
	}
	
	public String getPhotosUrl(){
		if (this.provider.shortname.equals("yelp")){
			return "http://www.yelp.com/biz_photos/"+this.providerVenueId;
		} else {
			return null;
		}
	}
	
}
