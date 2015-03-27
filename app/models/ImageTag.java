package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class ImageTag extends Model {
    
	@ManyToOne
	Image image;
	
	String name;
	double confidence;
	
	public ImageTag(Image image, String name, double confidence){
		this.image = image;
		this.name = name;
		this.confidence = confidence;
	}
	
}
