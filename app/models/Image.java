package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Image extends Model {
    
	String providerPictureId;
	
	String prefix;
	String suffix;
	
	boolean processed;
	
	@ManyToOne
	Venue venue;
	
	//@OneToMany(mappedBy="venue", cascade=CascadeType.ALL)
	//List<ImageTag> tags;
	
	public Image(Venue venue, String providerPictureId){
		this.venue = venue;
		this.providerPictureId = providerPictureId;
		
		this.processed = false;
		
	}
	
}
