package models;

import play.*;
import play.db.jpa.*;

import javax.persistence.*;
import java.util.*;

@Entity
public class Provider extends Model {
	
	@Column(unique=true)
	String shortname;
	
	String baseURL;
	
    
}
