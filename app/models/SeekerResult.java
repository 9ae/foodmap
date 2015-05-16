package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class SeekerResult extends Model {
	
	@ManyToOne
	public Seeker seeker;
	
	@ManyToOne
	public Image image;
	
	public boolean retrieved;
	
	public SeekerResult(Seeker seeker, Image image){
		this.seeker = seeker;
		this.image = image;
		this.retrieved = false;
	}
    
}
