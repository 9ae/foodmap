package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Provider extends Model {
	
	@Column(unique=true)
	public String shortname;
	
	public String baseURL;
	
	public static Provider findByShortname(String name){
		return Provider.find("shortname=?", name).first();
	}
    
}
