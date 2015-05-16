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
	
	public boolean processed;
	
	@ManyToOne
	public Venue venue;
	
	public String name;
	public String description;
	
	//@OneToMany(mappedBy="venue", cascade=CascadeType.ALL)
	//List<ImageTag> tags;
	
	public Image(Venue venue, String providerPictureId){
		this.venue = venue;
		this.providerPictureId = providerPictureId;
		
		this.processed = false;
		
	}
	
	public String getImageUrl(){
		//TODO: implement correct image url for different providers
		return "";
	}
	
}
