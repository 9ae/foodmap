package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;

import java.util.*;

@Entity
public class Venue extends Model {
    
	String providerVenueId;
	
	@ManyToOne
	Provider provider;
	
	String name;
	
	Double lat;
	Double lon;
	
	Calendar lastImagesUpdate;
	
	public Venue(String providerVenueId, Provider provider, String name, double lat, double lon){
		this.providerVenueId = providerVenueId;
		this.provider = provider;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		
		lastImagesUpdate = Calendar.getInstance();
	}
	
}
