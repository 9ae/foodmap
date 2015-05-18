package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Image extends Model {
    
	public String providerPictureId;
	
	public String prefix;
	public String suffix;
	
	public String providerImageUrl;
	
	public boolean processed;
	
	@ManyToOne
	public Venue venue;
	
	public String description;
	
	//@OneToMany(mappedBy="venue", cascade=CascadeType.ALL)
	//List<ImageTag> tags;
	
	public Image(Venue venue, String providerPictureId){
		this.venue = venue;
		this.providerPictureId = providerPictureId;
		
		this.processed = false;
		
	}
	
	public Image(Venue venue, String url, String description){
		this.venue = venue;
		this.providerImageUrl = url;
		this.description = description;
		
		this.processed = true;
		
	}
	
	public String getImageUrl(){
		//TODO: implement correct image url for different providers
		return this.providerImageUrl;
	}
	
}
