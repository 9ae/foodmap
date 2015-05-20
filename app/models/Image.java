package models;

import play.*;
import play.db.jpa.*;
import services.Words;

import javax.persistence.*;

import java.util.*;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

@Entity
public class Image extends Model {
    
	public String providerPictureId;
	
	public String prefix;
	public String suffix;
	
	//public String providerImageUrl;
	
	public boolean processed;
	
	@ManyToOne
	public Venue venue;
	
	public String description;
	public int quality; // 1 - 10
	
	//@OneToMany(mappedBy="venue", cascade=CascadeType.ALL)
	//List<ImageTag> tags;
	
	public Image(Venue venue){
		this.venue = venue;		
		this.processed = true;	
	}
	
	public Image(Venue venue, String providerPictureId){
		this.venue = venue;
		this.providerPictureId = providerPictureId;
		
		this.processed = false;
		this.quality = 0;
		
	}
	/*
	public Image(Venue venue, String url, String description){
		this.venue = venue;
		this.providerImageUrl = url;
		this.description = description;
		
		this.processed = true;	
	}
	*/
	public void yelpImageUrlParser(String imageUrl, String sizeSuffix){
		Pattern p = Pattern.compile("^(http.*)\\/bphoto\\/(.*)\\/"+sizeSuffix+"\\.(jpg|png|gif)$");
		Matcher m = p.matcher(imageUrl);
		if(m.matches()){
			prefix = m.group(1);
			providerPictureId = m.group(2);
			suffix = m.group(3);
		}
	}
	
	public String getImageUrl(){
		if(this.venue.provider.shortname=="yelp"){
			return prefix + "/bphoto/" + providerPictureId + "/l." + suffix;
		} else {
			return "";
		}
	}
	
	public void makeTags(String itemDetailedDescription){
		ImageTag mainTag = new ImageTag(this, description, 0.9);
		mainTag.save();
		
		if(!itemDetailedDescription.isEmpty()){
			String[] words = itemDetailedDescription.split(" ");
			for(int i=0; i<words.length; i++){
				String word = words[i];
				if(Words.isPreposition(word)){
					continue;
				}
				new ImageTag(this, word, 0.5).save();
			}
		}
	}
	
}
