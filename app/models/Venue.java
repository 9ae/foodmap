package models;

import play.*;
import play.db.jpa.*;
import services.Providers;

import javax.persistence.*;

import org.jsoup.Jsoup;
import org.jsoup.nodes.Element;
import org.jsoup.select.Elements;

import java.io.IOException;
import java.util.*;

@Entity
public class Venue extends Model {
    
	public String providerVenueId;
	
	public int provider;
	
	public String name;
	
	public Double lat;
	public Double lon;
	
	public Calendar lastImagesUpdate;
	
	public Venue(String providerVenueId, int provider, String name, double lat, double lon){
		this.providerVenueId = providerVenueId;
		this.provider = provider;
		this.name = name;
		this.lat = lat;
		this.lon = lon;
		
		lastImagesUpdate = Calendar.getInstance();
	}
	
	public static Venue findByProviderId(int provider, String id){
		TypedQuery<Venue> query = JPA.em().createQuery("SELECT v FROM Venue v WHERE v.provider=:provider AND v.providerVenueId=:venue_id", Venue.class);
		query.setParameter("provider", provider);
		query.setParameter("venue_id", id);
		query.setMaxResults(1);
		try{
			return query.getSingleResult();
		} catch(NoResultException ex){
			return null;
		}
	}

	public Image getBestPictureOfTag(String string) {
		String queryString = "SELECT img FROM ImageTag tag"
				+ " JOIN tag.image img"
				+ " WHERE"
				+ " img.venue.id = :venue_id"
				+ " AND"
				+ " tag.name LIKE :tag_name"
				+ " ORDER BY tag.confidence DESC";
		Query query = JPA.em().createQuery(queryString);
		query.setParameter("venue_id", this.id);
		query.setParameter("tag_name", string);
		query.setMaxResults(1);
		List<Image> images = query.getResultList();
		if (images.isEmpty()){
			return null;
		} else {
			return images.get(0);
		}
	}
	
	public String getURL(){
		return "http://www.yelp.com/biz/"+this.providerVenueId;
	}
	
	public String getMenuUrl(){
		if (this.provider==Providers.YELP){
			return "http://www.yelp.com/menu/"+this.providerVenueId;
		} else {
			return null;
		}
	}
	
	public String getPhotosUrl(){
		if (this.provider==Providers.YELP){
			return "http://www.yelp.com/biz_photos/"+this.providerVenueId;
		} else {
			return null;
		}
	}
	
	public boolean makeSoupFromMenu(){
		String url = getMenuUrl();
		System.out.println("going to :"+url);
		String selector = "div.menu-section > div.menu-item:not(.menu-item-placeholder-photo)";
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements menuItems = doc.select(selector);
			if(menuItems.size()==0){
				return false;
			} else {
				System.out.println("Going through "+menuItems.size()+" menu items");
				Iterator<Element> eleIt = menuItems.iterator();
				while(eleIt.hasNext()){
					Element item = eleIt.next();
					String imgSrc;
					String description;
					Elements itemImages = item.select("div.biz-photo-box img.photo-box-img");
					if(itemImages.isEmpty()){
						System.out.println("no image for this item");
						continue;
					} else {
						Element img = itemImages.get(0);
						imgSrc = img.attr("src");
					}
					Elements itemTitles = item.select("div.menu-item-details h3");
					Elements itemDescriptions = item.select("div.menu-item-details div.menu-item-details-description");
					if(itemTitles.isEmpty() && itemDescriptions.isEmpty()){
						System.out.println("no description for this item");
						continue;
					}
					description = itemTitles.text().trim().toLowerCase();
					System.out.println("\t "+imgSrc+" : "+description);
					Image img = new Image(this);
					img.description = description;
					img.yelpImageUrlParser(imgSrc, "60s");
					img.save();
					img.makeTags(itemDescriptions.text().trim().toLowerCase());
					
				}
				System.out.println("Complete menu photos registration");
				lastImagesUpdate = Calendar.getInstance();
				return true;
			}
		} catch (IOException e) {
			return false;
		} 
	}
	
	public boolean makeSoupFromPhotos(){
		String url = getPhotosUrl();
		System.out.println("going to :"+url);
		String selector = "div.photos > div.photo";
		try {
			org.jsoup.nodes.Document doc = Jsoup.connect(url).get();
			Elements photos = doc.select(selector);
			if(photos.size()==0){
				return false;
			} else {
				System.out.println("Going through "+photos.size()+" phtos");
				Iterator<Element> eleIt = photos.iterator();
				while(eleIt.hasNext()){
					Element item = eleIt.next();
					String imgSrc;
					String description;
					Elements itemImages = item.select("div.biz-photo-box img.photo-box-img");
					if(itemImages.isEmpty()){
						System.out.println("no image for this item");
						continue;
					} else {
						Element img = itemImages.get(0);
						imgSrc = img.attr("src");
						description = img.attr("alt");
					}
					description = description.trim().toLowerCase();
					description = description.replaceAll("[^a-zA-Z0-9\\s]", "");
					description = description.replaceAll("\\s{2,}", " ");
					System.out.println("\t "+imgSrc+" : "+description);
					Image img = new Image(this);
					img.description = description;
					img.yelpImageUrlParser(imgSrc, "ms");
					img.save();
					img.makeTags(description);
					
				}
				System.out.println("Complete menu photos registration");
				lastImagesUpdate = Calendar.getInstance();
				return true;
			}
		} catch (IOException e) {
			return false;
		} 
	}
	
}
